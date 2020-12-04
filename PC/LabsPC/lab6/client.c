#include <arpa/inet.h>
#include <fcntl.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>

#include "helpers.h"

void usage(char *file)
{
	fprintf(stderr, "Usage: %s ip_server port_server file\n", file);
	exit(0);
}

/*
 * Utilizare: ./client ip_server port_server nume_fisier_trimis
 */
int main(int argc, char **argv)
{
	

	int port = 8080;
	int sockfd;
	struct sockaddr_in server_addr, cli_addr;
	char buffer[BUFLEN];

	/* TODO deschidere socket */

	// Creating socket file descriptor 
	if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) { 
		perror("socket creation failed"); 
		exit(EXIT_FAILURE); 
	}

	
	memset(&cli_addr, 0, sizeof(cli_addr));

	/* TODO setare struct sockaddr_in pentru a specifica unde trimit
	 * datele */

	/*
	 * TODO
	 * cat_timp  mai_pot_citi
	 *		citeste din fisier
	 *		trimite pe socket
	 */
	
	socklen_t len = sizeof(server_addr);
	memset(&server_addr, 0, sizeof(server_addr));

	server_addr.sin_family = AF_INET; // IPv4 
	server_addr.sin_port = htons(port);
	int rc = inet_pton(AF_INET, "127.0.0.1", &server_addr.sin_addr);
	if(rc <= 0) {
		perror("inet_pton");
		exit(-1);
	}

	sendto(sockfd, "Buna seara", strlen("Buna seara"), 
				0, ( struct sockaddr *) &server_addr, len); 
	int n = recvfrom(sockfd, buffer, BUFLEN,
				0, ( struct sockaddr *) &server_addr, &len);
	buffer[n] = '\0';

	printf("%s\n", buffer);
	/* TODO închidere socket */
	close(sockfd);
	/* TODO închidere fișier */

	return 0;
}
