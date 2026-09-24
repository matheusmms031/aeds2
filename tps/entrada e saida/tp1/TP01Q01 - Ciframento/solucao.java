import java.util.Scanner;

public class Solucao{
    public static boolean compara(String stringA, String stringB){ // Função que vê se está no fim!
        if(stringA.length() != stringB.length()) return false;
            for(int i = 0; i < stringA.length(); i++){
                if(stringA.charAt(i) != stringB.charAt(i)){
                    return false;
                }
            }
            return true;
        }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String linhaFormatada = ""; // String de saida
        String linha;

        while(true){
            linha = sc.nextLine();
            if(compara(linha,"FIM")){ // Se linha for igual a FIM para execução na hora!
                break;
            } else{
                for(int i = 0; i < linha.length(); i++){ // Percorre caracter a caracter da String 
                    linhaFormatada = linhaFormatada + (char) ( (int) linha.charAt(i) + 3 ); // Tranforma caracter em inteiro na tabela Ascii, soma 3 e depois tranforma em caracter!
                }
                System.out.println(linhaFormatada); // printa a resposta
                linhaFormatada = ""; // Esvazia a String de saida para a próxima String entrar
            }
        }
    }
}
