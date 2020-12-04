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
#include <sys/types.h>

char *get_ip(char *name)
{
    int ret;
    struct addrinfo hints, *result, *p;

    // TODO: set hints
    memset(&hints, 0, sizeof(struct addrinfo));
    memset(&hints, 0, sizeof(struct addrinfo));
    hints.ai_family = AF_UNSPEC;
    // TODO: get addresses
    ret = getaddrinfo(name, NULL, &hints, &result);
    if (ret != 0)
    {
        printf("getaddrinfo: %s\n", gai_strerror(ret));
        exit(1);
    }
    // TODO: iterate through addresses and print them

    char ip[16];
    for (p = result; p != NULL; p = p->ai_next)
    {
        char ip[INET_ADDRSTRLEN];
        struct sockaddr_in *addr = (struct sockaddr_in *)p->ai_addr;
        inet_ntop(p->ai_family, &(addr->sin_addr), ip, sizeof(ip));
    }
    // TODO: free allocated data
    return ip;
}

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
    // message = compute_get_request("141.85.241.51", "/", NULL);
    // send_to_server(sockfd, message);
    // response = receive_from_server(sockfd);
    // printf("%s\n", response);
    // free(response);
    // close_connection(sockfd);

    // /*
    //     Ex 1: Get videos
    // */

    // // TODO EX 1

    // /*
    //     EX 2.1: Add video
    // */
    // sockfd = open_connection(IP_SERVER, PORT_SERVER, AF_INET, SOCK_STREAM, 0);
    // message = compute_get_request(IP_SERVER, "/videos", NULL);
    // send_to_server(sockfd, message);
    // response = receive_from_server(sockfd);
    // printf("%s\n", response);
    // free(response);
    // close_connection(sockfd);

    // TODO Ex 2.1

    /*
        Ex 2.2 Verificam noua colectie de videoclipuri
    */
    // sockfd = open_connection(IP_SERVER, PORT_SERVER, AF_INET, SOCK_STREAM, 0);
    // message = compute_post_request(IP_SERVER, "/videos", "id=1&name=Minioni");
    // send_to_server(sockfd, message);
    // response = receive_from_server(sockfd);
    // printf("%s\n", response);
    // free(response);
    // close_connection(sockfd);

    // TODO Ex 2.2

    /*
        Ex 3 Autentificare
    */
    sockfd = open_connection(IP_SERVER, PORT_SERVER, AF_INET, SOCK_STREAM, 0);
    message = compute_post_request(IP_SERVER, "/weather/login", "username=admin&password=p@ss");
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);
    printf("%s\n", response);
    free(response);
    close_connection(sockfd);

    sockfd = open_connection(IP_SERVER, PORT_SERVER, AF_INET, SOCK_STREAM, 0);
    message = compute_get_request(IP_SERVER, "/weather/key", NULL);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);
    printf("%s\n", response);
    free(response);
    close_connection(sockfd);

    // TODO Ex 3
    char *IP = get_ip("api.openweathermap.org");
    puts(IP);
    // sockfd = open_connection(IP_SERVER, PORT_SERVER, AF_INET, SOCK_STREAM, 0);
    // message = compute_get_request(IP_SERVER, "/weather/key", "url=/weather/key; method=GET");
    // puts(message);
    // send_to_server(sockfd, message);
    // response = receive_from_server(sockfd);
    // printf("%s\n", response);
    // free(response);
    // close_connection(sockfd);
    // free(message);
    return 0;
}