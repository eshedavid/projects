#include <stdio.h>
#include <string.h>

struct Person {
    char name[50];
    int citNo;
    float salary;
};

// typedef simplifies the declaration of variables of this struct type.
typedef struct Distance {
    int feet;
    float inch;
} distances;

// nested struct
typedef struct complex {
    int imag;
    float real;
} complex;

typedef struct number {
    complex complex;
    int integers;
} number;

complex copyStruct(complex original, complex* newOne);

int main () {

    struct InlineDeclaration {
        int test;
    } inlineDeclaration;

    struct Person person1, person2, p[20];
    distances d1, d2;

    // assign value to name of person1.
    strcpy(person1.name, "George Orwell");

    // assign value to other person1 variables.
    person1.citNo = 1234;
    person1.salary = 1234;

    // print
    printf("Name: %s\n", person1.name);
    printf("Citizenship no.: %d\n", person1.citNo);
    printf("Salary: %.2f\n", person1.salary);

    complex original, newOne;
    original.imag = 1;
    original.real = 0.5;

    copyStruct(original, &newOne);

    printf("%d, %.2f", newOne.imag, newOne.real);

    return 0;
}

complex copyStruct(complex original, complex *newOne) {
    newOne->imag = original.imag;
    newOne->real = original.real;
}