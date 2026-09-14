#include <stdio.h>
#include <stdlib.h>
#include <locale.h> // NECESSÁRIO PARA SUPORTE A ACENTOS

// Verifica se a string lida corresponde ao marcador de fim de entrada "FIM".
int isFim(char* entryString){
    return entryString[0] == 'F' && entryString[1] == 'I' && entryString[2] == 'M' && entryString[3] == '\0';
}

// Imprime, recursivamente, o caractere cifrado (chave 3) na posição i e segue para o próximo,
// até encontrar o fim da string, quando imprime a quebra de linha.
void cifraRecursivo(char* entryString, int i){
    if(entryString[i] == '\0'){
        printf("\n");
    } else{
        printf("%c", (char) (entryString[i] + 3));
        cifraRecursivo(entryString, i + 1);
    }
}

// Função "wrapper" que inicia a cifragem recursiva a partir da posição 0.
void cifra(char* entryString){
    cifraRecursivo(entryString, 0);
}

// Lê uma linha inteira (podendo ser vazia) para dentro de entryString, removendo o '\n' final.
void leLinha(char* entryString){
    fgets(entryString, 500, stdin);
    for(int i = 0; entryString[i] != '\0'; i++){
        if(entryString[i] == '\n'){
            entryString[i] = '\0';
        }
    }
}

int main(){
    // Configura o console para usar a codificação padrão do sistema (UTF-8 / Acentos)
    setlocale(LC_ALL, "");

    char entryString[500];

    leLinha(entryString);

    // Condição de parada: lê até que a entrada seja exatamente "FIM"
    while(!isFim(entryString)){
        cifra(entryString);
        leLinha(entryString);
    }

    return 0;
}
