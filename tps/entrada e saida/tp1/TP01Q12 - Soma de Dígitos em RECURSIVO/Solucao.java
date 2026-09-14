import java.util.Scanner;

public class Solucao{

    // Soma o último dígito (resto da divisão por 10) com a soma dos dígitos do
    // restante do número (divisão inteira por 10), até number virar 0!
    public static int somaDigitos(int numero){
        int resp;
        if(numero == 0){
            resp = 0;
        } else{
            resp = (numero % 10) + somaDigitos(numero / 10);
        }
        return resp;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        int numero;

        while(scan.hasNextInt()){
            numero = scan.nextInt();
            System.out.println(somaDigitos(numero));
        }
    }
}
