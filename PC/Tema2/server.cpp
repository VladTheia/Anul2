#include <iostream>
#include <vector>
#include <queue>
#include <stdio.h>
#include <stdlib.h>
#include <cstring>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include<bits/stdc++.h>

using namespace std;

#define DIE(assertion, call_description)	\
	do {									\
		if (assertion) {					\
			fprintf(stderr, "(%s, %d): ",	\
					__FILE__, __LINE__);	\
			perror(call_description);		\
			exit(EXIT_FAILURE);				\
		}									\
	} while(0)


#define BUFLEN	1551	// dimensiunea maxima a campului de date
#define MAX_CLIENTS	5	// numarul maxim de clienti in asteptare

void usage(char *file) {
	fprintf(stderr, "Usage: %s PORT_Server\n", file);
	exit(0);
}

class Topic {
    public:
    char name[51];
    int SF;
    int status;
};

class Client {
    public:
    int sockfd;
    int ID;
    int status;
    vector<Topic> subbed_topics;
    queue<char*> missed_posts;
};

int main(int argc, char *argv[]) {
    // Variabile utilizate de server
    int tcp_sockfd, udp_sockfd, newsockfd, portno, ID, data_type, n, i, ret;
    char buffer[BUFLEN], topic[51], tip_date[2], message[1501], command[12], SF[2];
    struct sockaddr_in serv_addr, tcp_cli_addr, udp_cli_addr;
    socklen_t clilen;
    vector<Client> clients;
    
    // utilizam programarea defensiva
	if (argc < 2) {
		usage(argv[0]);
	}

    // stocam port-ul server-ului
    portno = atoi(argv[1]);
    DIE(portno == 0 , "atoi");

    // golim, apoi initializam structura server-ului
    memset((char *) &serv_addr, 0, sizeof(serv_addr));
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(portno);
	serv_addr.sin_addr.s_addr = INADDR_ANY;

    // initializam socket-ul pentru lucrul cu clientul TCP
    tcp_sockfd = socket(AF_INET, SOCK_STREAM, 0);
    DIE(tcp_sockfd < 0, "socket");
    
    // Seturile pentru multiplexarea clientilor TCP
    fd_set read_fds;    // multimea de citire
    fd_set tmp_fds;     // multime folosita temporar
    int fdmax;          // valoare maxima fd din multimea read_fds

    // se golesc multimile read_fds si tmp_fds
    FD_ZERO(&read_fds);
	FD_ZERO(&tmp_fds);
    
    // legarea la server
	ret = bind(tcp_sockfd, (struct sockaddr *) &serv_addr, sizeof(struct sockaddr));
	DIE(ret < 0, "bind");

	ret = listen(tcp_sockfd, MAX_CLIENTS);
	DIE(ret < 0, "listen");

    // initializam socket-ul pentru lucrul cu clientul UDP
    udp_sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    DIE(udp_sockfd < 0, "socket");

    int enable = 1;
    if (setsockopt(udp_sockfd, SOL_SOCKET, SO_REUSEADDR, &enable, 	sizeof(int)) < 0) {
        perror("setsockopt(SO_REUSEADDR) failed");
    }

    // legam socket-ul la adresa server-ului
    if ( bind(udp_sockfd, (const struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0 ) { 
        perror("bind failed"); 
    }

	// se adauga noul file descriptor (socketul pe care se asculta conexiuni) in multimea read_fds
	FD_SET(tcp_sockfd, &read_fds);
    FD_SET(udp_sockfd, &read_fds);
    FD_SET(STDIN_FILENO, &read_fds);
    fdmax = max(tcp_sockfd, udp_sockfd) + 1;

    while(1) {
        tmp_fds = read_fds;

        // multiplexarea
        ret = select(fdmax + 1, &tmp_fds, NULL, NULL, NULL);
        DIE(ret < 0, "select");

        for (i = 0; i <= fdmax; i++) {
            if (FD_ISSET(i, &tmp_fds)) {
                if (i == STDIN_FILENO) {
                    // comanda exit
                    memset(buffer, 0, BUFLEN);
                    fgets(buffer, BUFLEN - 1, stdin);

                    if (!strncmp(buffer, "exit", 4)) {
                        for (int j = 0; j <= fdmax; j++) {
                            if (FD_ISSET(j, &tmp_fds)) {
                                for (Client &cli : clients) {
                                    if (cli.sockfd == j) {
                                        n = send(j, "F", strlen("F"), 0);
                                        DIE(n < 0, "send");
                                    }
                                }
                                close(j);
                            }
                        }
                        FD_ZERO(&read_fds);
                        FD_ZERO(&tmp_fds);
                        exit(0);
                    }
                } else if (i == tcp_sockfd) {
                    // a venit o cerere de conexiune pe socket-ul cu listen, pe care server-ul o accepta
                    clilen = sizeof(tcp_cli_addr);
					newsockfd = accept(tcp_sockfd, (struct sockaddr *) &tcp_cli_addr, &clilen);
					DIE(newsockfd < 0, "accept");

                    // se adauga noul socket intors de accept la multimea de citire
                    FD_SET(newsockfd, &read_fds);
                    if (newsockfd > fdmax) {
                        fdmax = newsockfd;
                    }

                    // primeste ID-ul
                    memset(buffer, 0, BUFLEN);
					n = recv(newsockfd, buffer, sizeof(buffer), 0);
					DIE(n < 0, "recv");
                    buffer[n] = '\0';
                    ID = atoi(buffer);

                    int ok = 1;
                    int reconnected = 0;

                    for (Client &cli : clients) {
                        if (cli.ID == ID) {
                            if (cli.status == 1) {
                                // ID-ul este deja folosit de un user online
                                ok = 0;
                                break;
                            } else {
                                // ID-ul a fost folosit si acum se reconecteaza
                                cli.sockfd = newsockfd;
                                reconnected = 1;
                                cli.status = 1;
                                break;
                            }
                        }
                    }

                    // daca ID-ul e deja folosit, trimitem mesaj clientului TCP pentru a se reloga cu alt ID
                    if(!ok) {
                        // F - flag FALSE pentru a arata ca este deja un user online
                        send(newsockfd, "F", strlen("F"), 0);
                        FD_CLR(newsockfd, &read_fds);
                        close(newsockfd);
                        break;
                    } else {
                        if (!reconnected) {
                            // T - flag TRUE pentru a arata ca se poate conecta
                            n = send(newsockfd, "T", strlen("T"), 0);   
                            DIE(n < 0, "send");
                        } else {
                            // R - flag RECONNECT pentru a arata ca se poate reconecta
                            n = send(newsockfd, "R", strlen("R"), 0);   
                            DIE(n < 0, "send");
                        }
                    }

                    cout << "New client " << ID << " connected from " << inet_ntoa(tcp_cli_addr.sin_addr) << ":" << ntohs(tcp_cli_addr.sin_port) << '\n';

                    // daca reconectam clientul, deja se afla in vectorul de clienti
                    if (reconnected) {    
                        for (Client &cli : clients) {
                                if (cli.sockfd == newsockfd) {
                                    if (cli.missed_posts.empty()) {
                                        // flag pentru coada initial goala
                                        n = send(newsockfd, "none", strlen("none"), 0);
                                        DIE(n < 0, "send");
                                    } else {
                                        while (!cli.missed_posts.empty()) {
                                            strncpy(topic, cli.missed_posts.front(), 50);
                                            topic[50] = '\0';
                                            data_type = cli.missed_posts.front()[50];
                                            strncpy(message, cli.missed_posts.front() + 51, 1500);
                                            message[1500] = '\0';

                                            // mesajul
                                            n = send(newsockfd, cli.missed_posts.front(), sizeof(buffer), 0);
                                            DIE(n < 0, "send");
                                            cli.missed_posts.pop();
                                            // adresa si portul clientului UDP care a trimis mesajul
                                            n = send(newsockfd, cli.missed_posts.front(), 20, 0);
                                            DIE(n < 0, "send");
                                            cli.missed_posts.pop();
                                        }
                                        // flag pentru golirea cozii de mesaje
                                        n = send(newsockfd, "done", strlen("done"), 0);
                                        DIE(n < 0, "send");
                                    }
                                }
                            }
                        break;
                    } else {
                        // adaugam noul client in vectorul de clienti
                        Client new_cli;
                        new_cli.ID = ID;
                        new_cli.sockfd = newsockfd;
                        new_cli.status = 1;
                        clients.push_back(new_cli);
                    }

                } else if (i == udp_sockfd) { 
                    // primit mesaje de la clientul UDP
                    clilen = sizeof(udp_cli_addr);
                    memset(&udp_cli_addr, 0, sizeof(udp_cli_addr));  
                    memset(buffer, 0, BUFLEN);
                    n = recvfrom(udp_sockfd, (char *)buffer, sizeof(buffer), 0, (struct sockaddr *) &udp_cli_addr, &clilen); 
                    
                    // trimiterea mesajelor catre clientii TCP abonati la topic-ul respectiv
                    sscanf(buffer, "%s %s %s", topic, tip_date, message);


                    for (Client &cli : clients) {
                        for (Topic &subbed_topic : cli.subbed_topics) {
                            if (!strcmp(subbed_topic.name, topic) && subbed_topic.status == 1) {
                                char addr_port[20] = "";
                                strcat(addr_port, inet_ntoa(udp_cli_addr.sin_addr));
                                strcat(addr_port, ":");
                                strcat(addr_port, to_string(ntohs(udp_cli_addr.sin_port)).c_str());
                                
                                if (cli.status == 0 && subbed_topic.SF == 1) {
                                    char* new_buffer = (char *)malloc(1551 * sizeof(char));
                                    memcpy(new_buffer, buffer, 1551);
                                    char* new_addr = (char *)malloc(20 * sizeof(char));
                                    memcpy(new_addr, addr_port, 20);

                                    cli.missed_posts.push(new_buffer);
                                    cli.missed_posts.push(new_addr);

                                } else if (cli.status == 1) {
                                    // trimiterea mesajelor
                                    n = send(cli.sockfd, buffer, sizeof(buffer), 0);
                					DIE(n < 0, "send");
                                    n = send(cli.sockfd, addr_port, sizeof(addr_port), 0);
                					DIE(n < 0, "send");
                                }
                            }
                        }
                    }
                } else { 
                    // comanda de la TCP
                    memset(buffer, 0, BUFLEN);
					n = recv(i, buffer, sizeof(buffer), 0);
					DIE(n < 0, "recv");

					if (n == 0) {
						// conexiunea s-a inchis
                        for (Client &cli : clients) {
                            if (cli.sockfd == i) {
                                cli.status = 0;
                                ID = cli.ID;
                                break;
                            }
                        }
						printf("Client %d disconnected\n", ID);
						close(i);
						FD_CLR(i, &read_fds);
					} else {
                        // se primesc comenzile
                        sscanf(buffer, "%s %s %s", command, topic, SF);
                        
                        for (Client &cli : clients) {
                            if (cli.sockfd == i) {
                                if (!strncmp(command, "unsubscribe", 11)) {
                                    // unsubscribe
                                    for (Topic &subbed_topic : cli.subbed_topics) {
                                        if (!strcmp(subbed_topic.name , topic)) {
                                            subbed_topic.status = 0;
                                            break;
                                        }
                                    }
                                } else {
                                    // subscribe
                                    int resubbed = 0;
                                    for (Topic &subbed_topic : cli.subbed_topics) {
                                        // daca am fost anterior abonat, doar ma reabonez
                                        if (!strcmp(subbed_topic.name, topic)) {
                                            subbed_topic.status = 1;
                                            subbed_topic.SF = atoi(SF);
                                            resubbed = 1;
                                            break;
                                        }
                                    }
                                    if (!resubbed) {
                                        Topic new_topic;
                                        strcpy(new_topic.name, topic);
                                        new_topic.SF = atoi(SF);
                                        new_topic.status = 1;
                                        cli.subbed_topics.push_back(new_topic);  
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    close(tcp_sockfd);
    close(udp_sockfd);
}        