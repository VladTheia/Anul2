#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include "link_emulator/lib.h"

#define HOST "127.0.0.1"
#define PORT 10001

int main(int argc,char** argv){
  msg r,t;
  my_pkt p, ack;
  init(HOST,PORT);

  recv_message(&r);
  p = *((my_pkt*)r.payload);
  if (p.type == 1) {
    char *filename = p.payload;
    printf("Am primit numele fisierului: %s\n", filename);
  }
  memset(r.payload, 0, sizeof(r.payload));

  ack.type = 4;
  memcpy(ack.payload, "ACK", 3);
  t.len = sizeof(int) + strlen(ack.payload);
  memcpy(t.payload, &ack, t.len);
  send_message(&t);
  memset(t.payload, 0, sizeof(t.payload));
  memset(ack.payload, 0, sizeof(ack.payload));

  unsigned size;
  recv_message(&r);
  p = *((my_pkt *)r.payload);
  if (p.type == 2)
  {
    memcpy(&size, p.payload, sizeof(p.payload));
    printf("Am primit dimensiunea fisierului: %d\n", size);
  }
  memset(r.payload, 0, sizeof(r.payload));

  ack.type = 4;
  memcpy(ack.payload, "ACK", 3);
  t.len = sizeof(int) + strlen(ack.payload);
  memcpy(t.payload, &ack, t.len);
  send_message(&t);
  memset(t.payload, 0, sizeof(t.payload));
  memset(ack.payload, 0, sizeof(ack.payload));

  int total = 0;
  int fd = open("output", O_WRONLY | O_CREAT | O_TRUNC);
  while (total < size) {
    recv_message(&r);
    p = *((my_pkt *)r.payload);
    if (p.type == 3)
    {
      write(fd, p.payload, strlen(p.payload));
      total += r.len - sizeof(int);
    }
    memset(r.payload, 0, sizeof(r.payload));


    ack.type = 4;
    memcpy(ack.payload, "ACK", 3);
    t.len = sizeof(int) + sizeof(ack.payload);
    memcpy(t.payload, &ack, t.len);
    send_message(&t);
    memset(t.payload, 0, sizeof(t.payload));
    memset(ack.payload, 0, sizeof(ack.payload));
  }

  return 0;
}
