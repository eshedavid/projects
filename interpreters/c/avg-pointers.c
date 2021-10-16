#include <stdio.h>
#include <stdlib.h>

int main() {
    
    float avg = 0.0f, sum = 0;
    int size = 0;

    printf("Number of elements:");
    scanf("%d", &size);

    float* numbers = (float*) malloc(size * sizeof(float));

    printf("Enter %d numbers\n", size);

    for (int i = 0; i < size; ++i)
    {
        scanf("%f", numbers + i);
    }

    for (int j = 0; j < size; ++j)
    {
        sum += *(numbers + j);
        printf("sum = %.2f\n", sum);
    }

    avg = sum / size;

    printf("The avg is %.2f", avg);

    free(numbers);
    
    
}