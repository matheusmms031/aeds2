#include <stdio.h>
#include <stdlib.h>
#include <locale.h> // NECESSÁRIO PARA SUPORTE A ACENTOS

/**
 * Mesma ideia da questão "Is" iterativa (vogais/consoantes/inteiro/real), porém cada
 * verificação percorre a string de forma recursiva: um método "wrapper" chama o método
 * recursivo passando a posição inicial (0) e o método recursivo avança um caractere por
 * vez (parâmetro i), sem usar laços de repetição.
 */

// Verifica se o caractere é uma vogal (maiúscula ou minúscula).
int isVogalChar(char c){
    return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
           c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
}

// Verifica se o caractere é uma letra do alfabeto (A-Z ou a-z), desconsiderando acentos.
int isLetra(char c){
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
}

// Verifica se o caractere é um dígito numérico (0-9).
int isDigito(char c){
    return c >= '0' && c <= '9';
}

// Percorre a string recursivamente verificando se todos os caracteres são vogais.
int isVogalRecursivo(char* s, int i){
    int resp;
    if(s[i] == '\0'){
        resp = (i > 0);
    } else if(!isVogalChar(s[i])){
        resp = 0;
    } else{
        resp = isVogalRecursivo(s, i + 1);
    }
    return resp;
}

// Função "wrapper": inicia a verificação recursiva de vogais a partir da posição 0.
int isVogal(char* s){
    return isVogalRecursivo(s, 0);
}

// Percorre a string recursivamente verificando se todos os caracteres são consoantes.
int isConsoanteRecursivo(char* s, int i){
    int resp;
    if(s[i] == '\0'){
        resp = (i > 0);
    } else if(!isLetra(s[i]) || isVogalChar(s[i])){
        resp = 0;
    } else{
        resp = isConsoanteRecursivo(s, i + 1);
    }
    return resp;
}

// Função "wrapper": inicia a verificação recursiva de consoantes a partir da posição 0.
int isConsoante(char* s){
    return isConsoanteRecursivo(s, 0);
}

// Percorre a string recursivamente verificando se todos os caracteres são dígitos.
int isInteiroRecursivo(char* s, int i){
    int resp;
    if(s[i] == '\0'){
        resp = (i > 0);
    } else if(!isDigito(s[i])){
        resp = 0;
    } else{
        resp = isInteiroRecursivo(s, i + 1);
    }
    return resp;
}

// Função "wrapper": inicia a verificação recursiva de número inteiro a partir da posição 0.
int isInteiro(char* s){
    return isInteiroRecursivo(s, 0);
}

// Percorre a string recursivamente verificando se todos os caracteres são dígitos ou um separador decimal ('.' ou ',').
int temApenasDigitosOuSeparadorRecursivo(char* s, int i){
    int resp;
    if(s[i] == '\0'){
        resp = 1;
    } else if(!isDigito(s[i]) && s[i] != '.' && s[i] != ','){
        resp = 0;
    } else{
        resp = temApenasDigitosOuSeparadorRecursivo(s, i + 1);
    }
    return resp;
}

// Função "wrapper" correspondente.
int temApenasDigitosOuSeparador(char* s){
    return temApenasDigitosOuSeparadorRecursivo(s, 0);
}

// Conta recursivamente quantos dígitos existem na string.
int contarDigitosRecursivo(char* s, int i){
    int resp;
    if(s[i] == '\0'){
        resp = 0;
    } else if(isDigito(s[i])){
        resp = 1 + contarDigitosRecursivo(s, i + 1);
    } else{
        resp = contarDigitosRecursivo(s, i + 1);
    }
    return resp;
}

// Função "wrapper" correspondente.
int contarDigitos(char* s){
    return contarDigitosRecursivo(s, 0);
}

// Conta recursivamente quantos separadores decimais ('.' ou ',') existem na string.
int contarSeparadoresRecursivo(char* s, int i){
    int resp;
    if(s[i] == '\0'){
        resp = 0;
    } else if(s[i] == '.' || s[i] == ','){
        resp = 1 + contarSeparadoresRecursivo(s, i + 1);
    } else{
        resp = contarSeparadoresRecursivo(s, i + 1);
    }
    return resp;
}

// Função "wrapper" correspondente.
int contarSeparadores(char* s){
    return contarSeparadoresRecursivo(s, 0);
}

// Um número real possui somente dígitos e, no máximo, um único separador decimal,
// com pelo menos um dígito presente. Implementada recursivamente através das funções acima.
int isReal(char* s){
    return temApenasDigitosOuSeparador(s) && contarDigitos(s) > 0 && contarSeparadores(s) <= 1;
}

// Verifica se a string lida corresponde ao marcador de fim de entrada "FIM".
int isFim(char* entryString){
    return entryString[0] == 'F' && entryString[1] == 'I' && entryString[2] == 'M' && entryString[3] == '\0';
}

// Lê uma linha inteira (podendo ser vazia) para dentro de entryString, removendo o '\n' final.
void leLinha(char* entryString){
    fgets(entryString, 1000, stdin);
    for(int i = 0; entryString[i] != '\0'; i++){
        if(entryString[i] == '\n'){
            entryString[i] = '\0';
        }
    }
}

int main(){
    // Configura o console para usar a codificação padrão do sistema (UTF-8 / Acentos)
    setlocale(LC_ALL, "");

    char entryString[1000];

    leLinha(entryString);
    while(!isFim(entryString)){
        printf("%s %s %s %s\n",
            isVogal(entryString) ? "SIM" : "NAO",
            isConsoante(entryString) ? "SIM" : "NAO",
            isInteiro(entryString) ? "SIM" : "NAO",
            isReal(entryString) ? "SIM" : "NAO");
        leLinha(entryString);
    }

    return 0;
}
