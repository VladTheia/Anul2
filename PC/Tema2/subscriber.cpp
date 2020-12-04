#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <cstring>
#include <vector>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

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
	fprintf(stderr, "Usage: %s ID_Client IP_Server PORT_Server\n", file);
	exit(0);
}

// functie de parsare a mesajului in functie de tipul lui
void parse(char* buffer, char *addr_port) {
    char topic[51];
    strcpy(topic, buffer);
    int type = (buffer[50]);
    int sign;
    
    if(type == 0){
        int value = int((unsigned char)(buffer[52]) << 24 |
                        (unsigned char)(buffer[53]) << 16 |
                        (unsigned char)(buffer[54]) << 8 |
                        (unsigned char)(buffer[55]));
        
        sign = (int) buffer[51];
        
        if(sign)
            value = -value;

        cout << addr_port << " - " << topic << " - INT - " << value << "\n";
    }

    if (type == 1) {
        double value = int((unsigned char)(buffer[51]) << 8 |
                             (unsigned char)(buffer[52]));
        value = value / 100;
        
        cout << addr_port << " - " << topic << " - SHORT_REAL - ";        
        printf("%.2f\n", value);        
    }

    if (type == 2) {
        sign = (int) buffer[51];
        
        double value = int((unsigned char)(buffer[52]) << 24 |
                           (unsigned char)(buffer[53]) << 16 |
                           (unsigned char)(buffer[54]) << 8 |
                           (unsigned char)(buffer[55]));
        
        uint8_t mod = buffer[56];
        
        while(mod){
            value = value / 10;
            mod--;
        }
        
        if(sign)
            value = -value;
        
        cout << addr_port << " - " << topic << " - FLOAT - ";
        printf("%f\n", value);
    }

    if (type == 3) {
        cout << addr_port << " - " << topic << " - INT - " << buffer + 51 << "\n";
    }
}

int main(int argc, char *argv[]) {
    // variabile pentru client
    int sockfd, n, ret, data_type;
	struct sockaddr_in serv_addr;
	char buffer[BUFLEN], ack[2], topic[51], message[1501], addr_port[20], command[12], SF[2];

    // programare defensiva
	if (argc < 4) {
		usage(argv[0]);
	}

    if (strlen(argv[1]) > 10) {
        fprintf(stderr, "ID_Client has maximum 10 characters.\n");
        exit(0);
    }

    // initializam structura server-ului
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(atoi(argv[3]));
	ret = inet_aton(argv[2], &serv_addr.sin_addr);
	DIE(ret == 0, "inet_aton");

    // deschidem socket-ul clientului
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
	DIE(sockfd < 0, "socket");

    // conexiunea cu server-ul
    ret = connect(sockfd, (struct sockaddr*) &serv_addr, sizeof(serv_addr));
	DIE(ret < 0, "connect");

    // trimitere ID-ul
    n = send(sockfd, argv[1], strlen(argv[1]), 0);
	DIE(n < 0, "send");

    memset(buffer, 0, BUFLEN);
    n = recv(sockfd, ack, sizeof(ack), 0);
    DIE(n < 0, "recv");
    if (!strncmp(ack, "F", 1)) {
        fprintf(stderr, "This ID is already used.\n");
        exit(0);
    }

    // daca reconecteaza, primeste mesajele pierdute de la topic-urile cu SF = 1
    if (!strncmp(ack, "R", 1)) {

        memset(buffer, 0, BUFLEN);
        n = recv(sockfd, buffer, sizeof(buffer), 0);
        DIE(n < 0, "recv");

        if (!strncmp(buffer, "none", 4)) {
            printf("No missed messages.\n");
        } else {
            do {
                // se citesc mesajele din coada pana la intalnirea flag-ului "done"
                memset(addr_port, 0, sizeof(addr_port));
                n = recv(sockfd, addr_port, sizeof(addr_port), 0);
                DIE(n < 0, "recv");

                strncpy(topic, buffer, 50);
                topic[50] = '\0';
                data_type = buffer[50];
                strncpy(message, buffer + 51, 1500);
                message[1500] = '\0';

                parse(buffer, addr_port);

                memset(buffer, 0, BUFLEN);
                n = recv(sockfd, buffer, sizeof(buffer), 0);
                DIE(n < 0, "recv");
            } while (strncmp(buffer, "done", 4));
        }
    }
    

    // initializam multimea de lucru
	fd_set read_set;
    fd_set tmp_read_set;
	FD_ZERO(&read_set);
	FD_SET(STDIN_FILENO, &read_set);
	FD_SET(sockfd, &read_set);

    while (1) {
        // multiplexare
        tmp_read_set = read_set;
		int ret = select(sockfd + 1, &tmp_read_set, NULL, NULL, NULL);
		DIE(ret < 0, "select");

        for (int i = 0; i <= sockfd; i++) {
            if (FD_ISSET(i, &tmp_read_set)) {
                if (i == STDIN_FILENO) { 
                    // clientul efectueaza o comanda
                    memset(buffer, 0, BUFLEN);
                    fgets(buffer, BUFLEN - 1, stdin);

                    sscanf(buffer, "%s %s %s", command, topic, SF);

                    // programare defensiva 
                    if (!strncmp(buffer, "exit", 4)) {
                        close(sockfd);
                        exit(0);
                    } else if (!strncmp(buffer, "unsubscribe", 11)) {
                        n = send(sockfd, buffer, strlen(buffer), 0);
                        DIE(n < 0, "send");
                        printf("unsubscribed %s\n", topic);
                        break;
                    } else if (!strncmp(buffer, "subscribe", 9)) { 
                        n = send(sockfd, buffer, strlen(buffer), 0);
                        DIE(n < 0, "send");
                        printf("subscribed %s\n", topic);
                        break;
                    } else { 
                        cout << "Wrong command.\n";
                        break;
                    }

                }
            } else {
                // mesaj de la server
                memset(buffer, 0, BUFLEN);
				n = recv(sockfd, buffer, sizeof(buffer), 0);
				DIE(n < 0, "recv");

                // comanda de exit de la server
                if (n == 0) {
                    close(sockfd);
                    printf("Server closed.\n");
                    exit(0);
                } else {
                    // mesaj de la clientul UDP
                    memset(addr_port, 0, 20);
                    n = recv(sockfd, addr_port, sizeof(addr_port), 0);
                    DIE(n < 0, "recv");
                    
                    // parsarea mesajului
                    parse(buffer, addr_port);
                    break;
                }
            }
        }
    }

    close(sockfd);
}