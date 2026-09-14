#include <stdio.h>
#include <stdlib.h>
#include <locale.h> // NECESSÁRIO PARA SUPORTE A ACENTOS
#include <string.h>

// Função para limpar a memória do vetor
void setNull(char* elementChar, int size){
    for(int i = 0; i < size; i++){
        *(elementChar + i) = '\0';
    }
}

int main(){
    // Configura o console para usar a codificação padrão do sistema (UTF-8 / Acentos)
    setlocale(LC_ALL, "");

    char entryString[500];
    int tamanhoAteFim;
    int posChar; 
    
    // %[^\n] lê a linha inteira incluindo espaços e acentos, evitando quebras no scanf
    scanf(" %[^\n]", entryString);
    
    // Condição de parada: lê até que a entrada seja exatamente "FIM"
    while(strcmp(entryString, "FIM") != 0){
        posChar = 0;
        tamanhoAteFim = 0; // Reinicia o tamanho para cada nova string

        while(entryString[posChar] != '\0'){ 
            tamanhoAteFim += 1; 
            posChar += 1;   
        }

        // Inverte a string desconsiderando o '\0' (começa em tamanhoAteFim - 1)
        for(int i = tamanhoAteFim - 1; i >= 0; i--){ 
            printf("%c", entryString[i]);
        }
        printf("\n");

        setNull(entryString, 500);
        scanf(" %[^\n]", entryString);
    }

    return 0;
}

