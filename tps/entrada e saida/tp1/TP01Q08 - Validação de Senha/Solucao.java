import java.util.Scanner;

public class Solucao{

    public static boolean isFim(String entrada){ // Função que vê se está no fim!
        return entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M';
    }

    // Senha válida: pelo menos 8 caracteres, com ao menos uma maiúscula, uma minúscula,
    // um dígito e um caractere especial (qualquer um que não seja letra nem dígito)
    public static boolean isSenhaValida(String senha){
        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temDigito = false;
        boolean temEspecial = false;
        char atual;

        for(int i = 0; i < senha.length(); i++){ // Percorre caracter a caracter da String
            atual = senha.charAt(i);
            if(atual >= 'A' && atual <= 'Z'){
                temMaiuscula = true;
            } else if(atual >= 'a' && atual <= 'z'){
                temMinuscula = true;
            } else if(atual >= '0' && atual <= '9'){
                temDigito = true;
            } else{
                temEspecial = true;
            }
        }
        return senha.length() >= 8 && temMaiuscula && temMinuscula && temDigito && temEspecial;
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String linha;

        linha = scan.nextLine();
        while(!isFim(linha)){
            if(isSenhaValida(linha)){
                System.out.println("SIM");
            } else{
                System.out.println("NAO");
            }
            linha = scan.nextLine();
        }
    }
}
