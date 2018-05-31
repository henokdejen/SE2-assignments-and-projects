#include <stdio.h>
#include <unistd.h>
#include <string.h>


int main() {
    int pipe1[2];
    int pipe2[2];

    pipe(pipe1);
    pipe(pipe2);

    printf("Pipe 1 fd[0]: %d", pipe1[0]);

    if (fork() == 0) {
        char buffer[100];
        char *msg = "Hello the whole world!";        

        write(pipe1[1], msg, strlen(msg) + 1);
        read(pipe2[0], buffer, 100);
        printf("Child: my parnet sent me %s \n", buffer);
    }

    else {
        char buffer[100];
        char *msg = "Hello the whole world!";

        read(pipe1[0], buffer, 100);
        write(pipe2[1], msg, strlen(msg) + 1);        
        printf("Etetu, my child sent me this: %s\n", buffer);
    }
    return 0;
}