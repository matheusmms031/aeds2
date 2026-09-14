#include <stdlib.h>
#include <stdio.h>



int somaRecursiva(int number){
    if(number == 0){
        return 0;
    }
    return (number%10) + somaRecursiva(number/10); // number % 10 pega o ultimo digito, number/10 retira o ultimo digito de number! 
}


int main(){
    int number;

    while(scanf("%d", &number) != EOF){
        printf("%d\n", somaRecursiva(number));
    }

    return 0;
}
