#include <stdio.h> 

void displayString(char str[]);

int main () {
    // string initialization in c. for char array-like for
    // char array-like initialization, we need to specify
    // the null character, otherwise c will add it automatically.
    char cString[] = "c string";
    char cString2[] = "c string";
    char c[] = {'c', ' ', 's', 't', 'r', 'i', 'n', 'g', '\0'};
    char c2[9] = {'c', ' ', 's', 't', 'r', 'i', 'n', 'g', '\0'};
 
    // strings are not assignable after declaration
    // char c[100];
    // c = "c string"; <- Error! array type is not assignable.

    // scanf() reads a string
    char name[20];
    //printf("Enter name: \n");
    //scanf("%s", name);

    // entering a space cuts off the assignment to the value before the space.
    //printf("Your name is %s\n", name);
    
    // to read a line of text, you can use fgets() and puts()
    printf("Enter a line: \n");

    fgets(name, sizeof(name), stdin); // read text string.
    displayString(name);
    return 0; 
}

void displayString(char str[]) {
    printf("Name: ");
    puts(str);
}