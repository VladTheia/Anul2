#include <stdlib.h>     /* exit, atoi, malloc, free */
#include <stdio.h>
#include <unistd.h>     /* read, write, close */
#include <string.h>     /* memcpy, memset */
#include <sys/socket.h> /* socket, connect */
#include <netinet/in.h> /* struct sockaddr_in, struct sockaddr */
#include <netdb.h>      /* struct hostent, gethostbyname */
#include <arpa/inet.h>
#include "helpers.h"
#include "requests.h"

char *compute_request(char* method, char *host, char *url, char *url_params, char *cookie, char *token, char *form_data, char *type) {
    if (!strncmp(method, "GET", 3)) {
        return compute_get_request(host, url, url_params, cookie, token);
    } else {
        return compute_post_request(host, url, form_data, type, cookie, token);
    }

}

char *compute_get_request(char *host, char *url, char *url_params, char *cookie, char *token) {
    char *message = calloc(BUFLEN, sizeof(char));
    char *line = calloc(LINELEN, sizeof(char));
    
    if (url_params != NULL) {
        sprintf(line, "GET %s?%s HTTP/1.1", url, url_params);
    }
    else {
        sprintf(line, "GET %s HTTP/1.1", url);
    }
    compute_message(message, line);

    sprintf(line, "Host: %s", host);
    compute_message(message, line);

    if (cookie != NULL)
        compute_message(message, cookie);

    if (token != NULL)
        compute_message(message, token);

    strcat(message, "\r\n");
    
    return message;
}

char *compute_post_request(char *host, char *url, char *form_data, char *type, char *cookie, char *token) {
    char *message = calloc(BUFLEN, sizeof(char));
    char *line = calloc(LINELEN, sizeof(char));

    sprintf(line, "POST %s HTTP/1.1", url);
    compute_message(message, line);

    sprintf(line, "Host: %s", host);
    compute_message(message, line);

    if (cookie != NULL)
        compute_message(message, cookie);

    if (token != NULL)
        compute_message(message, token);
    

    sprintf(line, "Content-Type: %s", type);
    compute_message(message, line);

    sprintf(line, "Content-Length: %lu", strlen(form_data));
    compute_message(message, line);

    
    strcat(message, "\r\n");

    sprintf(line, "%s", form_data);
    compute_message(message, line);
  
    return message;
}