import java.util.Scanner;
import java.util.Random;

public class Solucao{

    public static boolean isFim(String entrada){ // Função que vê se está no fim!
        if(entrada.length() >= 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M'){
            return true;
        } else{
            return false;
        }
    }

    // Sorteia as duas letras e troca uma pela outra na string. O gerador é recebido já
    // criado/semeado em main, para a sequência de sorteios continuar avançando a cada
    // chamada (se recriasse o Random aqui dentro, sortearia sempre as mesmas letras)!
    public static String alternador(String entrada, Random gerador){
        char charN1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char charN2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        String temp = "";
        char nowChar;

        for(int i = 0; i < entrada.length(); i++){
            nowChar = entrada.charAt(i);
            if(nowChar == charN1){
                temp = temp + charN2;
            }else{
                temp = temp + nowChar;
            }
        }
        return temp;
    }

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        Random gerador = new Random();
        gerador.setSeed(4); // Semente fixada uma única vez, fora do loop!

        String entryScan;
        String alternado;

        while(true){
            entryScan = scan.nextLine();
            if(isFim(entryScan)){
                break;
            }
            alternado = alternador(entryScan, gerador);
            System.out.println(alternado);
        }
    }
}
