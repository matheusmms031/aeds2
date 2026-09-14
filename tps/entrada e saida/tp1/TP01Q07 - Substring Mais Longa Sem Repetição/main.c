#include <stdlib.h>
#include <stdio.h>


/**
 *  Problema muito conhecido e complexo de fato, quando paramos para pensar nas possiveis possibilidades que o probelma aborda.
 *  Há duas formas de fazer esse problema, a boa e a ruim, a ruim é aquela que listamos todos as substrings
 *  e listamos a maior que não possui caracteres identicos, um trabalho que é relativamente péssimo!
 *  Já a boa é aquela que lê caracter por caracter, identifica qual a maior substring sem repetição usando apenas um laço for para ler a string inteira e outro for para comparar se o proximo caracter lido está dentro da maior substring.
 *
 *  Por exemplo:
 *
 *  [ 0, 1, 2, 3, 3, 4, 5 ]
 *
 *  Aqui temos: [ 0, 1, 2 , 3 ] como maior subvetor sem repetição, depois o 3 se repete, e há outro subvetor:
 *  [ 3, 4, 5 ], porém este não é a maior e é descartado!
 *
 *  O raciocinio que a maioria segue por intuição é a forma liniar que, quando se lê um repetido, zerar os vetores de maior sub, salvar o tamanho do maior e se o tamanho da nova substring for maior, salvar este tamanho como maior e assim vai... Porém tem um baita problema nisso, e próximo exemplo deixa claro:
 *
 *  [ 0, 1, 2, 3, 4, 3, 6, 7, 8, 9, 3 ]
 *
 *  Muitos leriam os maiores subvetores sem repetição assim:
 *
 *  [ 0, 1, 2, 3, 4 ] ENCONTROU UM 3! MAIOR SUVETOR: 5
 *  [ 3, 6, 7, 8, 9 ] ENCONTROU UM 3! MAIOR SUBVETOR AINDA É 5!
 *  [ 3 ] ACABOU!
 *
 *  Sendo que na verdade, existe um subvetor ESCONDIDO ai: [ 4, 3, 6, 7, 8, 9] QUE TEM 6 elementos...
 *
 *  A solução para esse problema é, ao invés de "zerar tudo" quando acha uma repetição, guardar a
 *  ÚLTIMA posição em que cada caractere apareceu. Quando um caractere repetido é encontrado DENTRO
 *  da janela atual (posição >= inicio), a janela não é zerada, apenas o "inicio" pula para logo
 *  depois da última ocorrência daquele caractere. Assim o [4, 3, 6, 7, 8, 9] escondido é encontrado.
 *
 */


// Função para marcar todas as posições do vetor de últimas ocorrências como "nunca vistas" (-1)
void setMenos1All(int size, int* vetor){
    for(int i = 0; i < size; i++){
        *(vetor + i) = -1;
    }
}

// Percorre a string uma única vez, mantendo em ultimaPos a última posição em que cada
// caractere ASCII apareceu, e desliza o início da janela quando encontra uma repetição
// dentro da janela atual.
int maiorSubstringSemRepeticao(char* entryString, int* ultimaPos){
    int inicio = 0;
    int maior = 0;
    int tamanhoAtual;
    unsigned char atual;

    for(int i = 0; entryString[i] != '\0'; i++){
        atual = (unsigned char) entryString[i];
        if(ultimaPos[atual] >= inicio){
            inicio = ultimaPos[atual] + 1;
        }
        ultimaPos[atual] = i;
        tamanhoAtual = i - inicio + 1;
        if(tamanhoAtual > maior){
            maior = tamanhoAtual;
        }
    }
    return maior;
}

int main(){
    char entryString[50];
    int* ultimaPos = calloc(256, sizeof(int)); // últimaPos[c] = última posição em que o caractere c apareceu

    scanf("%s", entryString);
    while(entryString[0] != 'F' || entryString[1] != 'I' || entryString[2] != 'M' || entryString[3] != '\0'){
        setMenos1All(256, ultimaPos);
        printf("%d\n", maiorSubstringSemRepeticao(entryString, ultimaPos));
        scanf("%s", entryString);
    }

    free(ultimaPos);
    return 0;
}
