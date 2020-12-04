#ifndef LIB
#define LIB

#define MAX_LEN 1500

typedef struct {
  // int type;
  int len;
  char payload[MAX_LEN];
} msg;

typedef struct {
    int type;
    char payload[MAX_LEN - 4];
} my_pkt;

void init(char* remote,int remote_port);
void set_local_port(int port);
void set_remote(char* ip, int port);
int send_message(const msg* m);
int recv_message(msg* r);
//msg* receive_message_timeout(int timeout);

#endif

