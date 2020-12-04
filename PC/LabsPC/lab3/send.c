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
  msg t;
  my_pkt p;
  memcpy(p.payload, "Helloa World of PC", strlen("Helloa World of PC"));
  int sum = 0;
  for (int c = 0; c < strlen(p.payload); c++) {
    for (int i = 0; i < 8; i++) {
      sum ^= (p.payload[c] & 1 << i)>>i;
    }
  }
  p.parity = sum;
  // printf("%d\n", p.parity);
  t.len = sizeof(int) + strlen(p.payload);
  memcpy(t.payload, &p, t.len);
  send_message(&t);
  memset(t.payload, 0, sizeof(t.payload));
  memset(p.payload, 0, sizeof(p.payload));

  return 0;
}
