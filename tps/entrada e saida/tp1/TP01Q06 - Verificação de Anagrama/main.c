#include <stdlib.h>
#include <stdio.h>

/**
 *
 * Estrutura de dados:
 *
 * 	[ [size], [ (int) char, qChar ], [ (int) char, qChar ], ... ]
 *
 * Ideia: monta a "tabela de frequência" da primeira string usando adicionaCaracter.
 * Em seguida, para cada caractere da segunda string, tenta remover uma ocorrência
 * dessa tabela com removeCaracter. Se algum caractere da segunda string não existir
 * (ou já tiver sido totalmente consumido) na tabela, as strings não são anagramas.
 * Ao final, se todas as ocorrências foram consumidas (matriz zerada), são anagramas.
 */


/**
 * Adiciona uma ocorrência do caractere atualChar na tabela de frequência.
 * Se o caractere já existe na tabela, apenas incrementa sua contagem; caso
 * contrário, cria uma nova entrada com contagem 1.
 */
int** adicionaCaracter(int** matriz, char atualChar){
	int size = matriz[0][0];
	for(int i = 1; i <= size; i++){
		if(*(*(matriz + i) + 0) == (int) atualChar){
			*(*(matriz + i) + 1) += 1;
			return matriz;
		}
	}

	int **matriz_temp = realloc(matriz,(size+2) * sizeof(int*));
	*(matriz_temp + size+1) = calloc(2, sizeof(int)); // [ size ... [ 0, 0 ] ]
	*(*(matriz_temp + size+1) + 0) = (int) atualChar;
        *(*(matriz_temp + size+1) + 1) = 1;
	*(*(matriz_temp + 0) + 0) = size+1;
	matriz = matriz_temp;
	return matriz_temp;
}

/**
 * Remove uma ocorrência do caractere atualChar na tabela de frequência (decrementa
 * a contagem). Grava em *sucesso 1 se o caractere existia com contagem > 0, ou
 * 0 se o caractere não está na tabela ou sua contagem já era zero.
 */
int** removeCaracter(int** matriz, char atualChar, int* sucesso){
	int size = matriz[0][0];
	*sucesso = 0;
	for(int i = 1; i <= size; i++){
		if(*(*(matriz + i) + 0) == (int) atualChar && *(*(matriz + i) + 1) > 0){
			*(*(matriz + i) + 1) -= 1;
			*sucesso = 1;
			break;
		}
	}
	return matriz;
}

/**
 * Verifica se todas as contagens da tabela de frequência estão zeradas, ou seja,
 * se todos os caracteres adicionados foram completamente consumidos por removeCaracter.
 */
int matrizZerada(int** matriz){
	int size = matriz[0][0];
	int zerada = 1;
	for(int i = 1; i <= size; i++){
		if(*(*(matriz + i) + 1) != 0){
			zerada = 0;
		}
	}
	return zerada;
}

/**
 * Libera toda a memória alocada dinamicamente pela tabela de frequência.
 */
void liberaMatriz(int** matriz){
	int size = matriz[0][0];
	for(int i = 0; i <= size; i++){
		free(*(matriz + i));
	}
	free(matriz);
}

/**
 * Converte uma letra maiúscula (A-Z) para minúscula; os demais caracteres são
 * retornados sem alteração. Usada para comparar anagramas sem diferenciar caixa.
 */
char paraMinusculo(char c){
	if(c >= 'A' && c <= 'Z'){
		return c + ('a' - 'A');
	}
	return c;
}

/**
 * Verifica se a string lida corresponde ao marcador de fim de entrada "FIM".
 */
int isFim(char* entryString){
	return entryString[0] == 'F' && entryString[1] == 'I' && entryString[2] == 'M' && entryString[3] == '\0';
}

/**
 * Verifica se stringA e stringB são anagramas uma da outra: monta a tabela de
 * frequência de stringA e tenta consumi-la totalmente com os caracteres de stringB.
 */
int isAnagrama(char* stringA, char* stringB){
	int** matriz = calloc(1, sizeof(int*)); // [ NULL ]
	int sucesso;
	int anagrama = 1;

	*(matriz+0) = calloc(1,sizeof(int)); // [ [0] ]
	for(int i = 0; stringA[i] != '\0'; i++){
		matriz = adicionaCaracter(matriz, paraMinusculo(stringA[i]));
	}

	for(int i = 0; stringB[i] != '\0'; i++){
		matriz = removeCaracter(matriz, paraMinusculo(stringB[i]), &sucesso);
		if(sucesso == 0){
			anagrama = 0;
		}
	}

	if(anagrama == 1 && matrizZerada(matriz) == 0){
		anagrama = 0;
	}

	liberaMatriz(matriz);
	return anagrama;
}

int main(){
	char entryStringA[50];
	char entryStringB[50];

	scanf("%s", entryStringA);
	while(isFim(entryStringA) == 0){
		scanf("%s", entryStringB);
		if(isAnagrama(entryStringA, entryStringB)){
			printf("SIM\n");
		} else{
			printf("NAO\n");
		}
		scanf("%s", entryStringA);
	}
	return 0;
}
