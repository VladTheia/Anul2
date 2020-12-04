/*
 * Protocoale de comunicatii
 * Laborator 7 - TCP
 * Echo Server
 * client.c
 */

#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <arpa/inet.h>
#include <netinet/in.h>

#include "helpers.h"

#define PORT 8080

int main(int argc, char *argv[])
{
    int sockfd = 0;
    char buf[BUFLEN];
    struct sockaddr_in serv_addr; 

   if(argc != 3)
    {
       printf("\n Usage: %s <ip> <port>\n", argv[0]);
       return 1;
   }

    // deschidere socket
    sockfd = socket(AF_INET, SOCK_STREAM, 0);

    // completare informatii despre adresa serverului
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(PORT);
    int rc = inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr);
    if (rc <= 0) {
        perror("inet_pton error");
        exit(EXIT_FAILURE);
    } 

    // conectare la server
    if (connect(sockfd, (const struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        perror("Connection failed!");
        exit(EXIT_FAILURE);
    }

    // citire de la tastatura, trimitere de cereri catre server, asteptare raspuns
    
    while (1) {
        scanf("%s", buf);
        send(sockfd, buf, strlen(buf), 0);

        int nr = recv(sockfd, buf, sizeof(buf), 0);
        buf[nr] = '\0';
        if (nr == 0) {
            close(sockfd);             
            break;
        }
        printf("%s\n", buf);
    }

    // inchidere socket
    close(sockfd);

    return 0;
}
