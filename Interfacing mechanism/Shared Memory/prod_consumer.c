#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <semaphore.h>

#define TRUE 1
#define SIZE 10

void *produce(void *ptr);
void *consume(void *ptr);

sem_t empty, full, mutex;

int buf[SIZE];
int in, out;


int main() {
    sem_init(&empty, 0, SIZE);
    sem_init(&full, 0, 0);

    pthread_t th1;
    pthread_t th2;

    pthread_create(&th1, NULL, &produce, NULL);
    pthread_create(&th2, NULL, &consume, NULL);
    
    pthread_join(th1, NULL);
    pthread_join(th2, NULL);

    sem_destroy(&empty);
    sem_destroy(&full);

    return 0;
}


void *produce(void *ptr) {
    while (TRUE) {
        int item = rand() % 10;  // produce number up to 10
        sem_wait(&empty);
        buf[in] = item;
        in = (in + 1) % SIZE;
        sem_post(&full);
    }
}

void *consume(void *ptr) {
    while (TRUE) {
        sem_wait(&full);
        int item = buf[out];
        printf("The item consumed %d \n", item);
        out = (out + 1) % SIZE;
        sem_post(&empty);
    }
}
