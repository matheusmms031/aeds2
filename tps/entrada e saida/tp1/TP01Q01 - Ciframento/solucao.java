import java.util.Scanner;

Public class Solucao(){

    Public void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String linha = sc.nextLine();
        String linhaFormatada;

        while(linha != "FIM"){
            for(int i = 0; i < linha.length(); i++){
                linhaFormatada += (char) (((int) linha[i]) + 3 );
            }
            System.out.println(linhaFormatada);
        }
    }
}
