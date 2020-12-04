/*
 * Protocoale de comunicatii
 * Laborator 7 - TCP
 * Echo Server
 * server.c
 */

#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>

#include "helpers.h"

#define PORT 8080
#define MAXLINE 1024

int main(int argc, char *argv[])
{
    int listenfd = 0, connfd = 0;
    char buffer[MAXLINE];
    struct sockaddr_in serv_addr; 

    if(argc != 3)
    {
       printf("\n Usage: %s <ip> <port>\n", argv[0]);
       return 1;
   }

    // deschidere socket
    if ( (listenfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }    

    int enable = 1;
    if (setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &enable, sizeof(int)) <0) {
        perror("setsockopt(SO_REUSEADDR) failed!");
    }

    memset(&serv_addr, 0, sizeof(serv_addr));

    // completare informatii despre adresa serverului
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(PORT);
    
    // legare proprietati de socket
    if ( bind(listenfd, (const struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
        perror("bind failed!");
        exit(EXIT_FAILURE);
    }

    // ascultare de conexiuni
    connfd = listen(listenfd, 100);
    if (connfd < 0) {
        perror("listen failed!");
        exit(EXIT_FAILURE);
    }

    // acceptarea unei conexiuni, primirea datelor, trimiterea raspunsului
    int cfd1 = accept(listenfd, NULL, NULL);
    if (cfd1 < 0) {
        perror("Client1 accept failed!");
        exit(EXIT_FAILURE);
    }
    int cfd2 = accept(listenfd, NULL, NULL);
    if (cfd2 < 0) {
        perror("Client2 accept failed!");
        exit(EXIT_FAILURE);
    }

    int n;
    while (1) {
        n = recv(cfd1, buffer, sizeof(buffer), 0);
        if (n < 0) {
            perror("recv failed!");
            exit(EXIT_FAILURE);
        } else if (n == 0) {
            close(cfd1);             
            break;
        }
        send(cfd2, buffer, n, 0);

        n = recv(cfd2, buffer, sizeof(buffer), 0);
        if (n < 0) {
            perror("recv failed!");
            exit(EXIT_FAILURE);
        } else if (n == 0) {
            close(cfd2);             
            break;
        }
        send(cfd1, buffer, n, 0);
    }


    // inchidere socket(i)
    close(listenfd);
    close(connfd);
    close(cfd1);
    close(cfd2);

    return 0;
}
