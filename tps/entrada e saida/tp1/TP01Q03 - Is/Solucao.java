import java.util.Scanner;

/**
 *
 * Ideia de planejamento: Separar de forma lógica em qual caso cada uma das String pertence e minimizar as conversões do motor do Java
 *
 *
 * X1 -> Apenas vogais
 * X2 -> Apenas consoantes
 * X3 -> Apenas números inteiros
 * X4 -> Apenas números reais
 *
 *
 * Função binarySearch => Busca binária no vetor ordenado de códigos ASCII das vogais, usado por isVogalChar;
 * Função isVogalChar/isLetra/isDigito => Testam um único caractere;
 * Funções isVogal, isConsoante, isInteiro, isReal => Testam a string inteira, caractere a caractere.
 *
 */

public class Solucao{
    static int[] vogais = {65,69,73,79,85,97,101,105,111,117};

    public static boolean binarySearch(int[] vetor, int busca){ // Busca binária no vetor ordenado
        int inicio = 0;
        int fim = vetor.length - 1;
        int meio;
        boolean achou = false;

        while(inicio <= fim && !achou){
            meio = (inicio + fim) / 2;
            if(vetor[meio] == busca){
                achou = true;
            } else if(vetor[meio] < busca){
                inicio = meio + 1;
            } else{
                fim = meio - 1;
            }
        }
        return achou;
    }

    public static boolean isVogalChar(char c){ // Vogal maiúscula ou minúscula, via busca binária no vetor vogais
        return binarySearch(vogais, (int) c);
    }

    public static boolean isLetra(char c){ // Letra do alfabeto (A-Z ou a-z), desconsiderando acentos
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    public static boolean isDigito(char c){ // Dígito numérico (0-9)
        return c >= '0' && c <= '9';
    }

    public static boolean isVogal(String entryString){ // String composta somente por vogais
        boolean resp = entryString.length() > 0;

        for(int i = 0; i < entryString.length() && resp; i++){ // Percorre caracter a caracter da String
            if(!isVogalChar(entryString.charAt(i))){
                resp = false;
            }
        }
        return resp;
    }

    public static boolean isConsoante(String entryString){ // String composta somente por consoantes
        boolean resp = entryString.length() > 0;
        char atual;

        for(int i = 0; i < entryString.length() && resp; i++){
            atual = entryString.charAt(i);
            if(!isLetra(atual) || isVogalChar(atual)){
                resp = false;
            }
        }
        return resp;
    }

    public static boolean isInteiro(String entryString){ // String composta somente por dígitos
        boolean resp = entryString.length() > 0;

        for(int i = 0; i < entryString.length() && resp; i++){
            if(!isDigito(entryString.charAt(i))){
                resp = false;
            }
        }
        return resp;
    }

    // String composta por dígitos com, no máximo, um único separador decimal ('.' ou ',') em qualquer posição
    public static boolean isReal(String entryString){
        int qtdDigitos = 0;
        int qtdSeparadores = 0;
        boolean resp = true;
        char atual;

        for(int i = 0; i < entryString.length() && resp; i++){
            atual = entryString.charAt(i);
            if(isDigito(atual)){
                qtdDigitos = qtdDigitos + 1;
            } else if(atual == '.' || atual == ','){
                qtdSeparadores = qtdSeparadores + 1;
            } else{
                resp = false;
            }
        }
        return resp && qtdDigitos > 0 && qtdSeparadores <= 1;
    }

    public static boolean isFim(String entrada){ // Função que vê se está no fim!
        return entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M';
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String linha;

        linha = scan.nextLine();
        while(!isFim(linha)){
            System.out.println( // Monta a linha de saida no formato X1 X2 X3 X4
                (isVogal(linha) ? "SIM" : "NAO") + " " +
                (isConsoante(linha) ? "SIM" : "NAO") + " " +
                (isInteiro(linha) ? "SIM" : "NAO") + " " +
                (isReal(linha) ? "SIM" : "NAO")
            );
            linha = scan.nextLine();
        }
    }
}
