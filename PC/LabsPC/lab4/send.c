#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>

#include "lib.h"

#define HOST "127.0.0.1"
#define PORT 10000

int main(int argc, char *argv[])
{
	msg m;
	msg ack;
	
	printf("[SENDER] Starting.\n");	
	init(HOST, PORT);

	printf("[SENDER]: BDP=%d\n", atoi(argv[1])); 
	int w = (atol(argv[1]) * 1000) / (sizeof(msg) * 8);
	if (COUNT < w) {
		w = COUNT;
	}
	for (int i = 0; i < w; i++) {
		m.len = MSGSIZE;
		memcpy(m.payload, "mesaj", m.len);
		send_message(&m);
  		memset(&m, 0, sizeof(msg));
	}

	for (int i = 0; i < COUNT - w; i++) {
		recv_message(&ack);
  		memset(&ack, 0, sizeof(msg));
		m.len = MSGSIZE;
		memcpy(m.payload, "mesaj", m.len);
		send_message(&m);
  		memset(&m, 0, sizeof(msg));
	}

	for (int i = 0; i < w; i++) {
		recv_message(&ack);
  		memset(&ack, 0, sizeof(msg));
	}

	printf("[SENDER] Job done, all sent.\n");
		
	return 0;
}
