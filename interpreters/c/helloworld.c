// BUILD USING CTRL + SHIFT + B > GCC COMPILER

#include <stdio.h>
#include <stdlib.h>

// sample functions
void formatting();
void userInput();
void numericalOperations();
void pointers();
void pointersAndFunctions();
void memoryAlloc();
// pointersAndFunctions aux functions.
void swap(int* num1, int* num2);
void addOne(int* ptr);

int main()
{

    // // formatting sample.
    // formatting();
    // // user input samples.
    // userInput();
    // // numerical operations.
    // numericalOperations();
    // // pointers
    // pointers();
    // // pointers and functions
    // pointersAndFunctions();
    // memory allocation.
    memoryAlloc();

    return 0;
}

void formatting () {
    short a;
    long b;
    long long c;
    long double d;
    
    // Using "%d" int values can be interpolated into a string.
    // Using "%f" float values can be interpolated into a string.
    // Using "%lf" double values can be interpolated into a string.
    // Using "%c" character values can be interpolated into a string.
    printf("size of short = %d bytes\n", sizeof(a));
    printf("size of long = %d bytes\n", sizeof(b));
    printf("size of long long = %d bytes\n", sizeof(c));
    printf("size of long double= %d bytes\n", sizeof(d));
    return;
}

void userIput() {

    // user input sample
    int entered;

    // printf is used to print to the console, while scanf prompt the user for input
    // use the "&" to get the address of the variable, where the user input will be stored.
    scanf("%d", &entered);
    printf("%d\n", entered);

    char character;

    scanf("%c", &character);
    printf("%c (%d)",character, character);

    int num1;
    int num2;

    printf("add two numbers");
    scanf("%d%d", &num1, &num2);

    printf("results is %d \n", num1 + num2);

    return;
}

void numericalOperations() {

    float af = 2.5f;
    float bf = 5.5f;
    float cf = af * bf;

    printf("multiplied result: %f", cf);
    return;
}

void pointers() {

    // variable where we'll store the user's input
    int var = 5;
    printf("var: %d\n", var);

    // Notice the user of & before var.
    // here we're printing the memory location of the variable,
    // not the value contained in at that location.
    printf("address of var: %p\n", &var);

    // with *, we denote we are storing a pointer to a memory location,
    // the value for this pointer represents the memory location.
    int* varPointer;
    varPointer = &var;

    printf("pointer value: %p\n", varPointer);
    
    // to get the value at the pointer's value location,
    // we preface the variable name with a *
    var++;

    printf("var: %d\n", var);
    printf("ptr: %d\n", *varPointer);

    // arrays
    int x[4];
    int i;

    // we are iterating each position in the array,
    // and printing the location in memory using the
    // & operator.
    for(i = 0; i < 4; ++i) {
        printf("&x[%d] = %p\n", i, &x[i]);
    }

    printf("Address of array x: %p\n", x);

    // Basically, &x[i] is equivalent to x+i and x[i] is equivalent to *(x+i).
    i = 0;
    int y[5], sum = 0;

    printf("enter 5 numbers:\n");
    
    for(i = 0; i < 5; i++) {
        // y + i = &y[i]
        scanf("%d", y + i);
        // *(y + i) = y[i];
        sum += *(y + i);
    }

    printf("Sum = %d\n", sum);
}

// This could be not declared at the top since it is not used
// before the implementation of pointersAndFunctions is
// resolved, but we'll declare it to avoid possible errors if
// things are moved around.
void swap(int* num1, int* num2) {
    int temp;
    temp = *num1;
    *num1 = *num2;
    *num2 = temp;
}

void addOne(int* ptr) {
    (*ptr)++;
}

void pointersAndFunctions() {
    int num1 = 5, num2 = 10;
     
    printf("%d, %d\n", num1, num2);
    // we pass the address locations instead of the values,
    // so that we can swap the values stored in each location,
    // instead of passing the value.
    swap(&num1, &num2);
    printf("%d, %d\n", num1, num2);

    int* ptr, i = 10;

    ptr = &i;

    printf("%d\n", i);
    addOne(ptr);
    printf("%d\n", i);

}

void memoryAlloc() {

    // here, we allocate the memory for 100 floats.
    // malloc follows the next syntax:
    //      ptr = (castType*) malloc(size);
    float* ptr = (float*) malloc(100 * sizeof(float));

    // calloc, or continuous allocation, allocates all memory,
    // and initializes all the locations to have value 0. in
    // contrast, malloc does not initialze the locations to 0.
    // The above statement allocates contiguous space in memory 
    // for 25 elements of type float.
    float* ptr2 = (float*) calloc(25, sizeof(float));

    // so, malloc allocates a block of memory of the specified size,
    // while calloc allocates contiguous space in memory for the specified
    // number of elements of the specified type.

    free(ptr);
    free(ptr2);

    // malloc sample
    int n, i, *ptr3, sum = 0;

    printf("Enter number of elements: ");
    scanf("%d", &n);

    ptr3 = (int*) malloc(n * sizeof(int));

    // if memory cannot be allocated
    if(ptr3 == NULL) {
        printf("Error! memory not allocated.");
        exit(0);
    }

    printf("Enter elements:");

    for(i = 0; i < n; ++i) {
        scanf("%d", ptr3 + i);
        sum += *(ptr3 + i);
    }

    printf("Sum = %d\n", sum);

    // dealloc memory
    free(ptr3);

    // calloc sample
    int n2, i2, *ptr4, sum2 = 0;
    
    printf("Enter number  of elements: ");
    scanf("%d\n", &n2);

    ptr4 = (int*) calloc(n2, sizeof(int));

    if(ptr4 == NULL) {
        printf("Error! memory not allocated.");
        exit(0);
    }

    printf("Enter elements: ");
    for(i2 = 0; i2 < n2; ++i2) {
        scanf("%d", ptr4 + i2);

        sum2 += *(ptr4 + i2);
    }

    printf("Sum2 = %d\n", sum2);
    free(ptr4);

    // realloc sample
    int *ptr5, i3, n3, n4;
    printf("Enter size: ");
    scanf("%d", &n3);

    ptr5 = (int*) malloc(n3 * sizeof(int));

    printf("Addresses of previously allocated memory:\n");
    for(i3 = 0; i3 < n3; ++i3) {
        printf("%pc\n", ptr5 + i3);
    }

    printf("\n Enter the new size: ");
    scanf("%d", &n4);

    ptr5 = realloc(ptr5, n4 * sizeof(int));

    printf("Addresses of newly allocated memory:\n");
    for(i3 = 0; i3 < n4; ++i3) {
        printf("%pc\n", ptr5 + i3);
    }

    free(ptr5);

    return;

}

