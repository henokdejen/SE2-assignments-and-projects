#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define RECEIVED '1'
#define SENT '0'
#define WAITING '2'
#define MAX_MSG_SIZE 100


key_t key = 12345;
char *shared_mem;
int control_me, control_lela;

void receiveMsg() {
    char *s = shared_mem + 2;
    printf("  ");
    for (;*s != '\0'; s++) {
        putchar(*s);
    }
    printf("\n");
}

void sendMsg() {
    int c;
    char *s = shared_mem + 2;

    printf(">> ");
    while ((c = getchar()) != '\n' && index < MAX_MSG_SIZE){
        *s++ = c;
    }
    *s = '\0';

    *(shared_mem + control_lela) = RECEIVED;
    *(shared_mem + control_me) = WAITING;
}

int main(int argc, char *args[]) {
    int seg_id = shmget(key, 102, IPC_CREAT | 0666);
    
    if (seg_id == -1) {
        printf("Creating a new segment has succesfully failed! \n");
        exit(1);
    }

    shared_mem = shmat(seg_id, NULL, 0);
    
    if (shared_mem == (char *)-1) {
        printf("Creating attachement has succesfully failed! \n");
        exit(1); 
    }
    
    if (strcmp(args[1], "-c") == 0) {   // create connection
        control_me = 0;
        control_lela = 1;
        sendMsg();  
    }
    else if (strcmp(args[1], "-j") == 0){   // join connection
        *(shared_mem + 1) = WAITING;
        control_me = 1;
        control_lela = 0;
    }
    else {
        exit(1);
    }

    while(1) {
        if (*(shared_mem + control_me) == RECEIVED){
            receiveMsg();
            sendMsg();            
        }
    } 
    return 0;
}