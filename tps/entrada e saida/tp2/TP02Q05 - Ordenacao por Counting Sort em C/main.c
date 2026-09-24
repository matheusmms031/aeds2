#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// "dd/mm/aaaa" = 10 caracteres + o '\0' do fim da string
#define TAM_DATA 11
// Folga para "[Gasoline,Electricity]" e para a linha inteira do veiculo
#define TAM_COMBUSTIVEL 64
#define TAM_VEICULO 512
// A maior linha do veiculos.csv tem 151 caracteres, 256 sobra
#define TAM_LINHA 256
#define QTD_CAMPOS 15

// Copia 'origem' para uma string nova do tamanho exato.
// Sem strlen liberado, o tamanho e contado na mao ate achar o '\0';
// o +1 no malloc e a vaga desse '\0'. Serve de strdup caseiro.
char* novaString(char* origem){
    int tamanho = 0;
    while(origem[tamanho] != '\0'){
        tamanho++;
    }
    char* destino = malloc((tamanho + 1) * sizeof(char));
    sprintf(destino, "%s", origem);
    return destino;
}

struct Data{
    int ano;
    int mes;
    int dia;
};

// Recebe "aaaa-mm-dd" e devolve a struct preenchida
struct Data parseData(char* entryString){
    // O strtok escreve '\0' no lugar dos '-', ou seja, ele DESTROI a string que
    // recebe. Se passarmos o literal direto do main da segfault (literal e
    // memoria somente leitura), entao trabalhamos sempre em cima de uma copia, burocracia do C né
    char stringCopia[TAM_DATA];
    sprintf(stringCopia, "%s", entryString);    // sprintf copiando a string (strcpy nao e permitido)!

    int* vectorDataInt = calloc(3,sizeof(int));

    // A PRIMEIRA chamada leva a string, as seguintes levam NULL para o strtok
    // continuar de onde parou (ele guarda a posicao internamente)
    char* stringDividida = strtok(stringCopia, "-");
    int i = 0;
    while(stringDividida != NULL && i < 3){
        vectorDataInt[i] = atoi(stringDividida);
        stringDividida = strtok(NULL, "-");
        i++;                            // i < 3 no while evita escrever fora do vetor
    }

    struct Data returnData;
    returnData.ano = vectorDataInt[0];  // sem isso a struct volta com lixo da pilha
    returnData.mes = vectorDataInt[1];
    returnData.dia = vectorDataInt[2];
    free(vectorDataInt);                // Super necessário para limpar a memória, se fosse int[3] ok, mas usamos calloc! E porque? Porque sim! KKKKK
    return returnData;
}

// Devolve a data formatada como string nova.
// A memoria vem do malloc (e NAO de um vetor local, que morreria ao sair daqui),
// entao quem chama fica responsavel por dar free no ponteiro recebido.
char* formatData(struct Data entryData){
    char* buffer = malloc(TAM_DATA * sizeof(char));
    sprintf(buffer, "%d/%d/%d",entryData.dia,entryData.mes,entryData.ano);
    return buffer;
}

struct Veiculo{
    int id;
    char* marca;
    char* modelo;
    int ano;
    char* categoria;
    char** combustivel;             // vetor de strings: o CSV traz "Gasoline;E85"
    int qtdCombustivel;             // quantos combustiveis o veiculo tem
    int cilindros;
    double cilindrada;
    char* transmissao;
    char* tracao;
    double consumoCidade;
    double consumoEstrada;
    double co2;
    bool turbo;
    struct Data dataRegistro;
};

// Devolve a linha do veiculo como string nova, no formato do TP:
// [id ## marca ## ... ## [comb1,comb2] ## ... ## turbo ## dd/mm/aaaa]
// Assim como o formatData, quem chama precisa dar free no retorno.
char* formatVeiculo(struct Veiculo entryVeiculo){
    // Os combustiveis saem entre colchetes e separados por virgula, entao o
    // ultimo e tratado fora do laco para nao sobrar uma virgula no fim.
    // O sprintf devolve quantos caracteres escreveu, entao 'pos' anda pelo buffer
    // e cada chamada continua de onde a anterior parou (substitui o strcat).
    char combValue[TAM_COMBUSTIVEL];
    int pos = sprintf(combValue, "[");
    for(int i = 0; i < entryVeiculo.qtdCombustivel - 1; i++){
        pos += sprintf(combValue + pos, "%s,", entryVeiculo.combustivel[i]);
    }
    sprintf(combValue + pos, "%s]", entryVeiculo.combustivel[entryVeiculo.qtdCombustivel - 1]);

    // Reaproveita o formatData para o campo de data
    char* dataValue = formatData(entryVeiculo.dataRegistro);

    // %f ja imprime 6 casas decimais, igual ao String.format do Java.
    // O bool nao tem formatador proprio em C, por isso o ternario com as strings.
    char* buffer = malloc(TAM_VEICULO * sizeof(char));
    sprintf(buffer, "[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %f ## %s ## %s ## %f ## %f ## %f ## %s ## %s]",
            entryVeiculo.id,
            entryVeiculo.marca,
            entryVeiculo.modelo,
            entryVeiculo.ano,
            entryVeiculo.categoria,
            combValue,
            entryVeiculo.cilindros,
            entryVeiculo.cilindrada,
            entryVeiculo.transmissao,
            entryVeiculo.tracao,
            entryVeiculo.consumoCidade,
            entryVeiculo.consumoEstrada,
            entryVeiculo.co2,
            entryVeiculo.turbo ? "true" : "false",
            dataValue);

    free(dataValue);    // o sprintf ja copiou o texto para dentro do buffer
    return buffer;
}


// Recebe uma linha do CSV e devolve o veiculo montado.
// CUIDADO COM O STRTOK: ele guarda a posicao numa variavel interna unica, ou
// seja, nao existem dois strtok rodando ao mesmo tempo. Por isso a linha e
// quebrada nos 15 campos PRIMEIRO, e so depois vem o strtok do ';' dos
// combustiveis e o do '-' da data (dentro do parseData). Se invertesse a
// ordem, o strtok de dentro zeraria a contagem do de fora.
struct Veiculo parseVeiculo(char* entryString){
    // O '\n' entra como delimitador porque o fgets guarda a quebra de linha
    // no fim, e ela cairia junto com o ultimo campo (a data).
    char* campos[QTD_CAMPOS];
    char* campo = strtok(entryString, ",\n");
    int i = 0;
    while(campo != NULL && i < QTD_CAMPOS){
        campos[i] = campo;
        campo = strtok(NULL, ",\n");
        i++;
    }

    // Conta os combustiveis ANTES de quebrar: o strtok troca cada ';' por '\0'
    // e depois nao daria mais para contar. 1 separador = 2 combustiveis.
    int qtdCombustivel = 1;
    for(int j = 0; campos[5][j] != '\0'; j++){
        if(campos[5][j] == ';'){
            qtdCombustivel++;
        }
    }
    char** combustivel = malloc(qtdCombustivel * sizeof(char*));
    char* comb = strtok(campos[5], ";");
    int k = 0;
    while(comb != NULL && k < qtdCombustivel){
        combustivel[k] = novaString(comb);
        comb = strtok(NULL, ";");
        k++;
    }

    // Todo campo de texto vira copia propria (novaString): os ponteiros de
    // 'campos' apontam para dentro da linha lida, que o fgets sobrescreve na
    // proxima volta do laco. Sem copiar, todos os veiculos acabariam apontando
    // para o mesmo lugar, com o texto do ultimo veiculo lido.
    struct Veiculo returnVeiculo;
    returnVeiculo.id = atoi(campos[0]);
    returnVeiculo.marca = novaString(campos[1]);
    returnVeiculo.modelo = novaString(campos[2]);
    returnVeiculo.ano = atoi(campos[3]);
    returnVeiculo.categoria = novaString(campos[4]);
    returnVeiculo.combustivel = combustivel;
    returnVeiculo.qtdCombustivel = qtdCombustivel;
    returnVeiculo.cilindros = atoi(campos[6]);
    returnVeiculo.cilindrada = atof(campos[7]);
    returnVeiculo.transmissao = novaString(campos[8]);
    returnVeiculo.tracao = novaString(campos[9]);
    returnVeiculo.consumoCidade = atof(campos[10]);
    returnVeiculo.consumoEstrada = atof(campos[11]);
    returnVeiculo.co2 = atof(campos[12]);
    returnVeiculo.turbo = (strcmp(campos[13], "true") == 0);   // C nao converte string para bool sozinho
    returnVeiculo.dataRegistro = parseData(campos[14]);

    return returnVeiculo;
}

// Cresce o vetor de uma posicao, igual ao addVeiculo do TP02Q01 em Java.
// O realloc ja copia o conteudo antigo e libera o bloco velho sozinho, por isso
// nao existe laco de copia nem free(oldVeiculos) aqui -- dar free seria double free.
// realloc(NULL, n) se comporta como malloc(n), entao a primeira chamada funciona igual.
struct Veiculo* addVeiculo(struct Veiculo* oldVeiculos, int qtdAntiga, struct Veiculo veiculoInput){
    struct Veiculo* returnVeiculos = realloc(oldVeiculos, (qtdAntiga + 1) * sizeof(struct Veiculo));
    returnVeiculos[qtdAntiga] = veiculoInput;
    return returnVeiculos;
}

// Le o CSV inteiro. Devolve o vetor e escreve a quantidade em *qtdVeiculos,
// ja que uma funcao em C so consegue devolver um valor.
struct Veiculo* lerCsv(char* caminho, int* qtdVeiculos){
    struct Veiculo* veiculos = NULL;
    *qtdVeiculos = 0;

    FILE* csvFile = fopen(caminho, "r");
    if(csvFile == NULL){
        printf("Nao foi possivel ler o arquivo\n");
        return NULL;
    }

    char linha[TAM_LINHA];
    if(fgets(linha, TAM_LINHA, csvFile) == NULL){   // descarta a linha de cabecalho
        fclose(csvFile);
        return NULL;
    }
    while(fgets(linha, TAM_LINHA, csvFile) != NULL){
        if(linha[0] != '\n'){                       // pula linha em branco no fim do arquivo
            veiculos = addVeiculo(veiculos, *qtdVeiculos, parseVeiculo(linha));
            (*qtdVeiculos)++;
        }
    }

    fclose(csvFile);
    return veiculos;
}

// Todo malloc do parseVeiculo precisa de um free correspondente
void liberaVeiculos(struct Veiculo* veiculos, int qtdVeiculos){
    for(int i = 0; i < qtdVeiculos; i++){
        for(int j = 0; j < veiculos[i].qtdCombustivel; j++){
            free(veiculos[i].combustivel[j]);
        }
        free(veiculos[i].combustivel);
        free(veiculos[i].marca);
        free(veiculos[i].modelo);
        free(veiculos[i].categoria);
        free(veiculos[i].transmissao);
        free(veiculos[i].tracao);
    }
    free(veiculos);
}


int main(){
    int qtdVeiculos;
    int qtdVeiculosEscolhidos = 0;
    struct Veiculo* veiculos = lerCsv("../veiculos.csv", &qtdVeiculos);
    struct Veiculo* veiculosEscolhidos = calloc(0, sizeof(struct Veiculo));
    int buffer;
    while(true){
        scanf("%d", &buffer);
        if(buffer == -1){
            break;
        }
        for(int i = 0; i < qtdVeiculos; i++){
            if(veiculos[i].id == buffer){
                veiculosEscolhidos = addVeiculo(veiculosEscolhidos,qtdVeiculosEscolhidos, *(veiculos + i));
                qtdVeiculosEscolhidos++;
            }
        }
    }

    // Ordenacao por selecao
    for(int i = 0; i < qtdVeiculosEscolhidos; i++){ // I Percorre tudo nos veiculosEscolhidos | I é o espaço que vai ser reocupado pelos menores J achados!
        int menor = i; // Vamos inicializar o menor sendo o proprio objeto em I
        for(int j = i+1; j < qtdVeiculosEscolhidos; j++){ // J só pega a percurssão de I+1, ou seja, para cada I, J percorre de I+1 até o fim do vetor
            int ordem = strcmp(veiculosEscolhidos[j].marca, veiculosEscolhidos[menor].marca);
            if(ordem < 0){
                menor = j;
            }
        }
        // Agora o swap!
        struct Veiculo tempVeiculo = veiculosEscolhidos[i];
        veiculosEscolhidos[i] = veiculosEscolhidos[menor];
        veiculosEscolhidos[menor] = tempVeiculo;
    }

    for(int i = 0; i < qtdVeiculosEscolhidos; i++){
        printf("%s",formatVeiculo(veiculosEscolhidos[i]));
    }

    liberaVeiculos(veiculos, qtdVeiculos);
    return 0;
}
