#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include "link_emulator/lib.h"

#define HOST "127.0.0.1"
#define PORT 10000

int main(int argc,char** argv){
  init(HOST,PORT);
  msg m, r;
  my_pkt p, ack;
  
  p.type = 1;
  memcpy(p.payload, argv[1], strlen(argv[1]));
  m.len = sizeof(int) + strlen(p.payload);
  memcpy(m.payload, &p, m.len);
  send_message(&m);
  memset(m.payload, 0, sizeof(m.payload));
  memset(p.payload, 0, sizeof(p.payload));

  recv_message(&r);
  ack = *((my_pkt *)r.payload);
  if (ack.type == 4)
    printf("Am primit ACK\n");
  memset(r.payload, 0, sizeof(r.payload));

  struct stat st;
  stat(argv[1], &st);
  
  p.type = 2;
  memcpy(p.payload, &st.st_size, sizeof(int));
  m.len = sizeof(int) + strlen(p.payload);
  memcpy(m.payload, &p, m.len);
  send_message(&m);
  memset(m.payload, 0, sizeof(m.payload));
  memset(p.payload, 0, sizeof(p.payload));

  recv_message(&r);
  ack = *((my_pkt *)r.payload);
  if (ack.type == 4)
    printf("Am primit ACK\n");
  memset(r.payload, 0, sizeof(r.payload));

  p.type = 3;
  int fd = open(argv[1], O_RDONLY);
  int count;
  while (count = read(fd, p.payload, sizeof(p.payload))) {
    m.len = sizeof(int) + count;
    memcpy(m.payload, &p, m.len);
    send_message(&m);
    memset(m.payload, 0, sizeof(m.payload));
    memset(p.payload, 0, sizeof(p.payload));

    recv_message(&r);
    ack = *((my_pkt *)r.payload);
    if (ack.type == 4)
      printf("Am primit ACK\n");
    memset(r.payload, 0, sizeof(r.payload));
  }

  return 0;
}
