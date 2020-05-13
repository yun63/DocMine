
/***************************************************************************
 *
 * Copyright © 2020 LT. All Rights Reserved.
 *
 ***************************************************************************/

/**
 *
 * @file: tcp_client.c
 *
 * @breaf: 
 *
 * @author: leiyunfei(towardstheway@gmail.com)
 *
 * @create: Sat 16 May 2020 12:59:16 AM CST
 *
 **/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/types.h>
#include <sys/socket.h>

#define SERVER_PORT 12345
#define MESSAGE_SIZE 1024000


ssize_t send_data(FILE *fp, int sockfd) {
    char *query;
    size_t remaining;
    size_t nwritten;
    query = (char *)malloc(MESSAGE_SIZE + 1);
    for (int i = 0; i < MESSAGE_SIZE; i++) {
        query[i] = 'a';
    }
    query[MESSAGE_SIZE] = '\0';

    const char *cp = query;
    remaining = strlen(query);
    while (remaining > 0) {
        nwritten = send(sockfd, cp, 1024, 0);
        fprintf(stdout, "send into buffer %ld \n", nwritten);
        if (nwritten <= 0) {
            perror("send error");
            return nwritten;
        }
        remaining -= nwritten;
        cp += nwritten;
    }
    return strlen(query) - remaining;
    
}

int main(int argc, char *argv[])
{
    if (argc != 2) {
        perror("usage: tcpclient <IPaddress>");
        exit(EXIT_FAILURE);
    }

    int sockfd;
    struct sockaddr_in servaddr;
    memset(&servaddr, 0, sizeof(servaddr));

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(SERVER_PORT);

    inet_pton(AF_INET, argv[1], &servaddr.sin_addr);

    if (connect(sockfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0) {
        perror("connect error\n");
        exit(EXIT_FAILURE);
    }
    send_data(stdin, sockfd);
    
    return 0;
}
