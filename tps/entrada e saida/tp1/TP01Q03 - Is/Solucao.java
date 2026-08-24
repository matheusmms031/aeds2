import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * Ideia de planejamento: Separar de forma lógica em qual caso cada uma das String pertence e minimizar as conversões do motor do Java
 *
 *
 * p -> Apenas vogais
 * q -> Apenas consoantes
 * s -> Apenas números reais
 * r -> Apenas números inteiros
 *
 *
 * Função SplitString => Dividir cada string por espaços, retornando array de Strings, à fim de testar cada um dos casos;
 * Função TestTo => Navega cada string e testa cada um dos casos
 * Funções isVogal, isConsoante, isReal, isInteiro;
 *
 */


public class Solucao{
    static int[] vogais = {65,69,73,79,85,97,101,105,111,117,32};

    public static int[] checkType(String str) {
        int[] listR = {0,0,0,0};
        try {
            // Check if it's a valid integer
            Integer.parseInt(str);
            listR[2] = 1;
        } catch (NumberFormatException e1){
            try {
                // Check if it's a valid float
                Float.parseFloat(str);
                listR[3] = 1;
            } catch (NumberFormatException e2) {
                // Not a valid number at all
                for(int i = 0; i < str.length(); i++){
                    for(int j = 0; j < 11; j++){
                        if(((int) str.charAt(i)) != vogais[j]){
                            listR[1] = 1;
                        }
                    }

                }
                if(listR[1] != 1){
                    listR[0] = 1;
                }
            }
        }
        return listR;
    }
    

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
         
        int[] teste = new int[4];
        teste = checkType("938");
        System.out.println(teste[0] + teste[1] + " " +teste[2] + " " + " " + teste[3]);
    }
}
