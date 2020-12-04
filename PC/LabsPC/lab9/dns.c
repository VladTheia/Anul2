// Protocoale de comunicatii
// Laborator 9 - DNS
// dns.c

#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>

int usage(char* name)
{
	printf("Usage:\n\t%s -n <NAME>\n\t%s -a <IP>\n", name, name);
	return 1;
}

// Receives a name and prints IP addresses
void get_ip(char* name)
{
	int ret;
	struct addrinfo hints, *result, *p;
	char host[100];

  int errcode;
  char addrstr[100];
  void *ptr;


  memset (&hints, 0, sizeof (hints));
  hints.ai_family = PF_UNSPEC;
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_flags |= AI_CANONNAME;

  errcode = getaddrinfo (name, NULL, &hints, &result);
  if (errcode != 0)
    {
      fprintf(stderr, "getaddrinfo: %s\n", gai_strerror(errcode));
      return -1;
    }

  
	
  	for(p = result; p != NULL; p = p->ai_next)
	{
		switch (p->ai_family)
        		{
        			case AF_INET:
          				ptr = &((struct sockaddr_in *) p->ai_addr)->sin_addr;
          				break;
        			case AF_INET6:
          				ptr = &((struct sockaddr_in6 *) p->ai_addr)->sin6_addr;
          				break;
        		}
      		inet_ntop (p->ai_family, ptr, addrstr, 100);
		printf("%s\n", addrstr);

		
	}
	


}

// Receives an address and prints the associated name and services
void get_name(char* ip)
{
	int ret;
	struct sockaddr_in addr;
	char host[1024];
	char service[20];
	
	// TODO: fill in address data
	addr.sin_family = AF_INET;
	addr.sin_port = 80;
	inet_aton(ip, &addr.sin_addr.s_addr);

	// TODO: get name and service
	getnameinfo(&addr, sizeof(addr), host, sizeof (host), NULL, 0, 0);
	printf("%s\n", host);

	// TODO: print name and service
}

int main(int argc, char **argv)
{
	if (argc < 3) {
		return usage(argv[0]);
	}

	if (strncmp(argv[1], "-n", 2) == 0) {
		get_ip(argv[2]);
	} else if (strncmp(argv[1], "-a", 2) == 0) {
		get_name(argv[2]);
	} else {
		return usage(argv[0]);
	}

	return 0;
}
