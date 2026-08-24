import java.util.Scanner;
import java.util.Random;



public class Solucao{



    public static boolean isFim(String entrada){
        if(entrada.length() >= 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M'){
            return true;
        } else{
            return false;
        }
    }

    public static String alternador(String entrada){
        Random random = new Random();
        String temp = "";
        int charN1 = random.nextInt(67, 122);
        char nowChar;
        int charN2 = random.nextInt(67, 122);
        
        for(int i = 0; i < entrada.length(); i++){
            nowChar = entrada.charAt(i);
            if(nowChar == (char) charN1){
                temp = temp + (char) charN2;
            }else{
                temp = temp + nowChar;
            }
        }
        return temp;
    }

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        String entryScan;
        String alternado;

        while(true){
            entryScan = scan.nextLine();
            if(isFim(entryScan)){
                break;
            }
            alternado = alternador(entryScan);
            System.out.println(alternado);
        }
    }
}
