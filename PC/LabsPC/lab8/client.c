#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include "helpers.h"

void usage(char *file)
{
	fprintf(stderr, "Usage: %s server_address server_port\n", file);
	exit(0);
}

int main(int argc, char *argv[])
{
	int sockfd, n, ret;	//			
	struct sockaddr_in serv_addr;		//
	char buffer[BUFLEN];	//

	if (argc < 3) {	//
		usage(argv[0]);	//
	}//

	sockfd = socket(AF_INET, SOCK_STREAM, 0);	//
	DIE(sockfd < 0, "socket");//				

	serv_addr.sin_family = AF_INET; //					
	serv_addr.sin_port = htons(atoi(argv[2]));	//	
	ret = inet_aton(argv[1], &serv_addr.sin_addr);	//
	DIE(ret == 0, "inet_aton");	//

	ret = connect(sockfd, (struct sockaddr*) &serv_addr, sizeof(serv_addr));//
	DIE(ret < 0, "connect");//													

	fd_set read_set;	//
	FD_ZERO(&read_set);	//				
	FD_SET(STDIN_FILENO, &read_set);//
	FD_SET(sockfd, &read_set);//

	int nr;

	while (1) {
		fd_set tmp_read_set = read_set;
		int ret = select(sockfd + 1, &tmp_read_set, NULL, NULL, NULL);
		DIE(ret < 0, "select");

		for (int i = 0; i <= sockfd; i++) {
			if (FD_ISSET(i, &tmp_read_set)) {
				if (i == STDIN_FILENO) {
					memset(buffer, 0, BUFLEN);
					fgets(buffer, BUFLEN - 1, stdin);

					if (strncmp(buffer, "exit", 4) == 0) {
						break;
					}

					// se trimite mesaj la server
					n = send(sockfd, buffer, strlen(buffer), 0);
					DIE(n < 0, "send");
				} else {
					nr = recv(sockfd, buffer, sizeof(buffer), 0);
					buffer[nr] = '\0';
					if (n == 0) {
						close(sockfd);
						break;
					}
					printf("%s\n", buffer);
				}
			}
		}
	}

	close(sockfd);

	return 0;
}
