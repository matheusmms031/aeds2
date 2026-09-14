import java.util.Scanner;

public class Solucao{

    public static boolean isFim(String entrada){ // Função que vê se está no fim!
        return entrada.length() == 3 && entrada.charAt(0) == 'F' && entrada.charAt(1) == 'I' && entrada.charAt(2) == 'M';
    }

    // Monta, a partir da posição i, a string invertida do sufixo entrada[i..fim]: cada
    // chamada coloca o caractere da posição i DEPOIS da inversão do restante da string!
    public static String inverteRecursivo(String entrada, int i){
        String resp;
        if(i == entrada.length()){
            resp = "";
        } else{
            resp = inverteRecursivo(entrada, i + 1) + entrada.charAt(i);
        }
        return resp;
    }

    public static String inverte(String entrada){ // Wrapper: inicia a recursão na posição 0
        return inverteRecursivo(entrada, 0);
    }

    // Monta, a partir da posição i, o restante (não invertido) da string entrada[i..fim].
    // Usada só para descartar os espaços em branco do início de cada linha!
    public static String restanteRecursivo(String entrada, int i){
        String resp;
        if(i == entrada.length()){
            resp = "";
        } else{
            resp = entrada.charAt(i) + restanteRecursivo(entrada, i + 1);
        }
        return resp;
    }

    public static String removeEspacosIniciais(String entrada){ // Remove os espaços do começo da linha
        int i = 0;
        while(i < entrada.length() && entrada.charAt(i) == ' '){
            i = i + 1;
        }
        return restanteRecursivo(entrada, i);
    }

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String linha;

        linha = removeEspacosIniciais(scan.nextLine());
        while(!isFim(linha)){
            System.out.println(inverte(linha));
            linha = removeEspacosIniciais(scan.nextLine());
        }
    }
}
