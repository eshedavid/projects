#include <stdio.h>
#include <stdlib.h>

typedef struct person {
    int age;
    float weight;
    char name[30];
} person;

int main() {
    person *personPointer, person1;
    personPointer = &person1;

    printf("Enter age: ");
    scanf("%d%*c", &personPointer->age);

    printf("Enter weight: ");
    scanf("%f%*c", &personPointer->weight);

    printf("Displaying:\n");
    printf("Age: %d\n", personPointer->age);
    printf("Weight: %.2f", personPointer->weight);

    // dynamic memory allocation for structs.
    person *ptr;
    int i, n;

    printf("Enter the number of person: ");
    scanf("%d%*c", &n);

    // allocating memory for n numbers of struct person
    ptr = (person*) malloc(n * sizeof(person));

    for(i; i < n; ++i) {
        printf("Enter the name and age respectively:");
        scanf("%s %d%*c", (ptr + i)->name, &(ptr + i)->age);
    }

    printf("Displaying all: \n");
    
    for(i = 0; i < n; ++i) {
        printf("Name: %s, Age: %d\n", (ptr + i)->name, (ptr + i)->age);
    }
    return 0;
}