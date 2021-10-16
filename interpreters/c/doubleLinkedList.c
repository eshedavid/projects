#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct node {
    struct node* previous;
    struct node* next;
    char* value;
};

struct list {
    struct node* firstNode;
    struct node* lastNode;
};

struct node* insertNode(char* value, struct list* list);
void removeNode(struct node* nodeToRemove, struct list* list);
struct node* findValue(char value[], struct list* list);
void printList(struct list* list);

int main () {

    struct list list;

    list.firstNode = NULL;

    struct node* firstNode = insertNode("hello", &list);
    struct node* secondNode = insertNode("world!", &list);
    struct node* thirdNode = insertNode("and", &list);
    struct node* fourthNode = insertNode("everyone", &list);

    struct node* foundNode = findValue("and", &list);

    printf("found node: %s\n", foundNode->value);

    printList(&list);

    removeNode(thirdNode, &list);

    printList(&list);

    removeNode(firstNode, &list);

    printList(&list);

    removeNode(fourthNode, &list);

    printList(&list);
}

struct node* insertNode(char* value, struct list* list) {
    int length = sizeof(value) / sizeof(value[0]);
    struct node* newNode = (struct node*) malloc(sizeof(struct node));

    newNode->value = (char*) malloc(length * sizeof(char));
    newNode->next = NULL;

    strcpy(newNode->value, value);

    if(list->firstNode == NULL) {
        newNode->previous = NULL;
        list->firstNode = newNode;
        list->lastNode = newNode;
        return newNode;
    }

    list->lastNode->next = newNode;
    newNode->previous = list->lastNode;
    list->lastNode = newNode;

    return newNode;
}

void removeNode(struct node* nodeToRemove, struct list* list) {

    struct node* previousNode = nodeToRemove->previous;
    struct node* nextNode = nodeToRemove->next;

    if(previousNode == NULL) {
        list->firstNode = nextNode;
        nextNode->previous = NULL;
    }
    else {
        previousNode->next = nextNode;
    }

    if(nextNode == NULL) {
        list->lastNode = previousNode;
        previousNode->next = NULL;
    }
    else {
        nextNode->previous = previousNode;
    }
}


void printList(struct list* list) {
    struct node* currentNode = list->firstNode;

    do {
        printf("%s\n", currentNode->value);
        currentNode = currentNode->next;
    } while(currentNode != NULL);
}

struct node* findValue(char value[], struct list* list) {
    struct node* currentNode = list->firstNode;
    struct node* foundNode = NULL;

    while(foundNode == NULL && currentNode != NULL) {
        if(strcmp(currentNode->value, value) == 0) {
            foundNode = currentNode;
        }
        else {
            currentNode = currentNode->next;
        }
    }

    return foundNode;
    
}
