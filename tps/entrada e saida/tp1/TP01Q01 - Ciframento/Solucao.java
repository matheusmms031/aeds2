import java.util.Scanner;

public class Solucao{
    public static boolean compara(String stringA, String stringB){
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
        String linhaFormatada = "";
        String linha;

        while(true){
            linha = sc.nextLine();
            if(compara(linha,"FIM")){
                break;
            } else{
                for(int i = 0; i < linha.length(); i++){
                    linhaFormatada = linhaFormatada + (char) ( (int) linha.charAt(i) + 3 );
                }
                System.out.println(linhaFormatada);
                linhaFormatada = "";
            }
        }
    }
}
