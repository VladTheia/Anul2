#ifndef _REQUESTS_
#define _REQUESTS_

char *compute_request(char* method, char *host, char *url, char *url_params, char *cookie, char *token, char *form_data, char *type);
char *compute_get_request(char *host, char *url, char *url_params, char* cookie, char* token);
char *compute_post_request(char *host, char *url, char *form_data, char* type, char* cookie, char* token);

#endif