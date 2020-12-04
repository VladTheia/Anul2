#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include "link_emulator/lib.h"

#define HOST "127.0.0.1"
#define PORT 10001

int main(int argc,char** argv){
  init(HOST,PORT);
  msg r;
  my_pkt p;

  recv_message(&r);
  p = *((my_pkt *)r.payload);
  int sum = 0;
  for (int c = 0; c < strlen(p.payload); c++)
  {
    for (int i = 0; i < 8; i++)
    {
      sum ^= (p.payload[c] & 1 << i) >> i;
    }
  }
  if (p.parity == sum) {
    printf("Partitatea e OK\n");
  } else {
    printf("Mesaj corupt\n");
  }
  memset(r.payload, 0, sizeof(r.payload));
  

  return 0;
}
