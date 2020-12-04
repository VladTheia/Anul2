#include <stdio.h>      /* printf, sprintf */
#include <stdlib.h>     /* exit, atoi, malloc, free */
#include <unistd.h>     /* read, write, close */
#include <string.h>     /* memcpy, memset */
#include <sys/socket.h> /* socket, connect */
#include <netinet/in.h> /* struct sockaddr_in, struct sockaddr */
#include <netdb.h>      /* struct hostent, gethostbyname */
#include <arpa/inet.h>
#include "helpers.h"
#include "requests.h"

int main(int argc, char *argv[])
{
    int i;
    char *message;
    char *response;
    int sockfd;

    
    /*
    *   Ex 0: Get cs.curs.pub.ro
    *   
    *   Pas 1: Se deschide conexiunea (open_connection)
    *   Pas 2: Se creaza mesajul de request (compute_get_request)
    *   Pas 3: Se trimite la server mesajul (send_to_server)
    *   Pas 4: Se primeste raspuns de la server (receive_from_server)
    *   Pas 5: Se inchide conexiunea cu serverul (close_connection)
    */

    //TODO EX 0

    // sockfd = open_connection("141.85.241.51", 80, AF_INET, SOCK_STREAM, 0);

    // message = compute_get_request("acs.curs.pub.ro", "/2018", NULL);
    // send_to_server(sockfd, message);
    // response = receive_from_server(sockfd);
    // puts(response);
    // free(response);

    // close(sockfd);

    /*
        Ex 1: Get videos
    */
    
    // TODO EX 1
/*
    sockfd = open_connection("185.118.200.37", 8081, AF_INET, SOCK_STREAM, 0);

    message = compute_get_request("185.118.200.37", "/videos", NULL);
    send_to_server(sockfd, message);

    close(sockfd);
    

    /*
        EX 2.1: Add video
    */
    
    // TODO Ex 2.1
/*
    sockfd = open_connection("185.118.200.37", 8081, AF_INET, SOCK_STREAM, 0);

    message = compute_post_request("185.118.200.37", "/videos", "id=40&name=Avatar");
    send_to_server(sockfd, message);
    puts(message);
    close(sockfd);

     /*
        Ex 2.2 Verificam noua colectie de videoclipuri
    */
/*
    // TODO Ex 2.2
    sockfd = open_connection("185.118.200.37", 8081, AF_INET, SOCK_STREAM, 0);

    message = compute_get_request("185.118.200.37", "/videos", NULL);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);
    puts(response);
    free(response);
    
    close(sockfd);

    /*
        Ex 3 Autentificare
    */

    sockfd = open_connection("185.118.200.37", 8081, AF_INET, SOCK_STREAM, 0);

    message = compute_post_request("185.118.200.37", "/weather/login", "username=admin&password=p@ss");
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);
    puts(response);
    free(response);
    
    close(sockfd);
    sockfd = open_connection("185.118.200.37", 8081, AF_INET, SOCK_STREAM, 0);

    message = compute_get_request("api.openweathermap.org", "/data/2.5/weather?q=bucharest&APPID=", NULL);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);
    puts(response);
    free(response);
    
    close(sockfd);

    // TODO Ex 3

    return 0;
}