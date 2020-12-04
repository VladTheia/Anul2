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
#include "parson.h"

#define PORT 8081

int main(int argc, char *argv[])
{
    int i;
    char hostUrl[20] = "185.118.200.35";
    char task5Url[20];
    char *message;
    char *response;
    char *form_data = malloc(1500);
    char *cookie = malloc(1500);
    char *cookie2 = malloc(1500);
    char *query_params3 = malloc(1500);
    char *weather_query = malloc(1500);
    char *token3 = malloc(1500);
    char *token4 = malloc(1500);
    char *token5 = malloc(1500);
    int sockfd, weathersock;
    
    //=============================================================== Task 1 ======================================================================
    sockfd = open_connection(hostUrl, PORT, AF_INET, SOCK_STREAM, 0);

    // GET method for the 1st task
    message = compute_request("GET", hostUrl, "/task1/start", NULL, NULL, NULL, NULL, NULL);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);

    // Getting the cookies
    char *cookie_start = strstr(response, "Cookie");
    char *cookie_end = strstr(cookie_start, ";");
    char *cookie_start2 = strstr(cookie_end, "Cookie");
    char *cookie_end2 = strstr(cookie_start2, ";");

    strncpy(cookie, cookie_start, cookie_end - cookie_start);
    strcat(cookie, "; ");
    strncpy(cookie2, cookie_start2 + 8, cookie_end2 - cookie_start2);
    strcat(cookie, cookie2);

    // make the response string contain only the json format
    response = strchr(response, '{');

    // parsing the returned json
    JSON_Object *json_task2 = json_object(json_parse_string(response));

    const char *url_task2 = json_object_get_string(json_task2, "url");
    const char *method_task2 = json_object_get_string(json_task2, "method");
    const char *type_task2 = json_object_get_string(json_task2, "type");
    JSON_Object *data_task2 = json_object_get_object(json_task2, "data");
    const char *field1T2 = json_object_get_name(data_task2, 0);
    const char *value1T2 = json_value_get_string(json_object_get_value_at(data_task2, 0));
    const char *field2T2 = json_object_get_name(data_task2, 1);
    const char *value2T2 = json_value_get_string(json_object_get_value_at(data_task2, 1));

    close(sockfd);

    //=============================================================== Task 2 ======================================================================
    sockfd = open_connection(hostUrl, PORT, AF_INET, SOCK_STREAM, 0);
    
    // POST method for 2nd task
    sprintf(form_data, "%s=%s&%s=%s", (char *)field1T2, (char *)value1T2, (char *)field2T2, (char *)value2T2);
    message = compute_request((char *)method_task2, hostUrl, (char *)url_task2, NULL, cookie, NULL, form_data, (char *) type_task2);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);

    // Getting the cookies
    cookie_start = strstr(response, "Cookie");
    cookie_end = strstr(cookie_start, ";");
    cookie_start2 = strstr(cookie_end, "Cookie");
    cookie_end2 = strstr(cookie_start2, ";");

    strncpy(cookie, cookie_start, cookie_end - cookie_start);
    strcat(cookie, "; ");
    strncpy(cookie2, cookie_start2 + 8, cookie_end2 - cookie_start2);
    strcat(cookie, cookie2);

    // make the response string contain only the json format
    response = strchr(response, '{');

    // parsing the returned json
    JSON_Object *json_task3 = json_object(json_parse_string(response));
    const char *url_task3 = json_object_get_string(json_task3, "url");
    const char *method_task3 = json_object_get_string(json_task3, "method");
    JSON_Object *data_task3 = json_object_get_object(json_task3, "data");
    const char *field1T3 = json_object_get_name(data_task3, 0);
    const char *value1T3 = json_value_get_string(json_object_get_value_at(data_task3, 0));
    JSON_Object *query_task3 = json_object_get_object(data_task3, "queryParams");
    const char *id_fieldT3 = json_object_get_name(query_task3, 0);
    const char *id_valueT3 = json_value_get_string(json_object_get_value_at(query_task3, 0));

    strcpy(query_params3, "raspuns1=omul&raspuns2=numele&");
    strcat(query_params3, id_fieldT3);
    strcat(query_params3, "=");
    strcat(query_params3, id_valueT3);

    strcpy(token3, "Authorization: Bearer ");
    strcat(token3, value1T3);
    strcpy(token4, token3);
    strcpy(token5, token3);

    close(sockfd);

    //=============================================================== Task 3 ======================================================================
    sockfd = open_connection(hostUrl, PORT, AF_INET, SOCK_STREAM, 0);

    // GET method for the 3rd task
    message = compute_request((char *)method_task3, hostUrl, (char *)url_task3, query_params3, cookie, token3, NULL, NULL);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);

    // Getting the cookies
    cookie_start = strstr(response, "Cookie");
    cookie_end = strstr(cookie_start, ";");
    cookie_start2 = strstr(cookie_end, "Cookie");
    cookie_end2 = strstr(cookie_start2, ";");

    strncpy(cookie, cookie_start, cookie_end - cookie_start);
    strcat(cookie, "; ");
    strncpy(cookie2, cookie_start2 + 8, cookie_end2 - cookie_start2);
    strcat(cookie, cookie2);

    // make the response string contain only the json format
    response = strchr(response, '{');   

    // parsing the returned json
    JSON_Object *json_task4 = json_object(json_parse_string(response));
    const char *url_task4 = json_object_get_string(json_task4, "url");
    const char *method_task4 = json_object_get_string(json_task4, "method");
     

    close(sockfd);

    //=============================================================== Task 4 ======================================================================
    sockfd = open_connection(hostUrl, PORT, AF_INET, SOCK_STREAM, 0);

    // GET method for the 4th task
    message = compute_request((char *)method_task4, hostUrl, (char *)url_task4, NULL, cookie, token4, NULL, NULL);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);

    // Getting the cookies
    cookie_start = strstr(response, "Cookie");
    cookie_end = strstr(cookie_start, ";");
    cookie_start2 = strstr(cookie_end, "Cookie");
    cookie_end2 = strstr(cookie_start2, ";");

    strncpy(cookie, cookie_start, cookie_end - cookie_start);
    strcat(cookie, "; ");
    strncpy(cookie2, cookie_start2 + 8, cookie_end2 - cookie_start2);
    strcat(cookie, cookie2);
    
    // make the response string contain only the json format
    response = strchr(response, '{');    

    // parsing the returned json
    JSON_Object *json_task5 = json_object(json_parse_string(response));
    const char *url_task5 = json_object_get_string(json_task5, "url");
    const char *method_task5 = json_object_get_string(json_task5, "method");
    const char *type_task5 = json_object_get_string(json_task5, "type");
    JSON_Object *data_task5 = json_object_get_object(json_task5, "data");
    const char *url_task5_part2 = json_object_get_string(data_task5, "url");
    const char *method_task5_part2 = json_object_get_string(data_task5, "method");
    JSON_Object *query_task5_part2 = json_object_get_object(data_task5, "queryParams");
    const char *field1T5 = json_object_get_name(query_task5_part2, 0);
    const char *value1T5 = json_value_get_string(json_object_get_value_at(query_task5_part2, 0));
    const char *field2T5 = json_object_get_name(query_task5_part2, 1);
    const char *value2T5 = json_value_get_string(json_object_get_value_at(query_task5_part2, 1));
    sprintf(weather_query, "%s=%s&%s=%s", field1T5, value1T5, field2T5, value2T5);

    // parsing the url
    char* method_url = strchr((char *)url_task5_part2, '/');
    strncpy(task5Url, url_task5_part2, method_url - (char *)url_task5_part2);
    char* weather_ip = get_ip(task5Url);

    close(sockfd);

    //=============================================================== Task 5 ======================================================================
    sockfd = open_connection(hostUrl, PORT, AF_INET, SOCK_STREAM, 0);

    weathersock = open_connection(weather_ip, 80, AF_INET, SOCK_STREAM, 0);
    
    // GET from weather api
    message = compute_request((char *)method_task5_part2 , weather_ip, (char *)method_url, weather_query, NULL, NULL, NULL, NULL);
    send_to_server(weathersock, message);
    response = receive_from_server(weathersock);

    // got the json needed for POST
    response = strchr(response, '{');

    // POST method for 5th task
    message = compute_request((char *)method_task5, hostUrl, (char *)url_task5, NULL, cookie, token5, response, (char *) type_task5);
    send_to_server(sockfd, message);
    response = receive_from_server(sockfd);    

    puts(response);

    close(weathersock);
    close(sockfd);

    return 0;
}