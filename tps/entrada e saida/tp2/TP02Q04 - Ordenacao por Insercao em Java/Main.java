import java.io.File;
import java.util.Locale;
import java.text.DecimalFormat;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main{

    public static int toInt(String entryString){
        return Integer.parseInt(entryString);
    }
    public static double toDouble(String entryString){
        return Double.parseDouble(entryString);
    }

    public static class Data{
        private int ano;
        private int mes;
        private int dia;

        public Data(int ano, int mes, int dia){
            this.ano = ano;
            this.mes = mes;
            this.dia = dia;
        }

        public static Data parseData(String dataString){
            String[] dataStringVector = dataString.split("-");
            return new Data(Integer.parseInt(dataStringVector[0]), Integer.parseInt(dataStringVector[1]), Integer.parseInt(dataStringVector[2]));
        }

        public String format(){
            return String.format("%d/%d/%d",this.dia,this.mes,this.ano);
        }

    }

    public static class Veiculo{
        private int id;
        private String marca;
        private String modelo;
        private int ano;
        private String categoria;
        private String[] combustivel;
        private int cilindros;
        private double cilindrada;
        private String transmissao;
        private String tracao;
        private double consumo_cidade;
        private double consumo_estrada;
        private double co2;
        private boolean turbo;
        private Data data_registro;

        public Veiculo(int id, String marca, String modelo, int ano, String categoria, String[] combustivel, int cilindros, double cilindrada, String transmissao, String tracao, double consumo_cidade, double consumo_estrada, double co2, boolean turbo, Data data_registro){
            this.id = id;
            this.marca = marca;
            this.modelo = modelo;
            this.ano = ano;
            this.categoria = categoria;
            this.combustivel = combustivel;
            this.cilindros = cilindros;
            this.cilindrada = cilindrada;
            this.transmissao = transmissao;
            this.tracao = tracao;
            this.consumo_cidade = consumo_cidade;
            this.consumo_estrada = consumo_estrada;
            this.co2 = co2;
            this.turbo = turbo;
            this.data_registro = data_registro;
        }

        public int getId(){
            return this.id;
        }

        public String getMarca(){
            return this.marca;
        }

        public int getAno(){
            return this.ano;
        }

        // Busca linear por Veiculos por ID que retorna Veiculo
        public static Veiculo searchLinearVeiculoId(Veiculo[] vetorVeiculos, int id){
            int valVeiculoReturn = 0;
            for(int i = 0; i < vetorVeiculos.length; i++){
                if(vetorVeiculos[i].getId() == id){
                    valVeiculoReturn = i;
                }
            }
            return vetorVeiculos[valVeiculoReturn];
        }
        
        public static Veiculo parseVeiculo(String entryString){
            String[] entryStringList = entryString.split(",");
            String[] combList = entryStringList[5].split(";");
            return new Veiculo(
                    toInt(entryStringList[0]),
                    entryStringList[1],
                    entryStringList[2],
                    toInt(entryStringList[3]),
                    entryStringList[4],
                    combList,
                    toInt(entryStringList[6]),
                    toDouble(entryStringList[7]),
                    entryStringList[8],
                    entryStringList[9],
                    toDouble(entryStringList[10]),
                    toDouble(entryStringList[11]),
                    toDouble(entryStringList[12]),
                    Boolean.parseBoolean(entryStringList[13]),
                    Data.parseData(entryStringList[14]));
        }
        
        public String format(){
            DecimalFormat df = new DecimalFormat("#.##");
            String returnValue;
            String combValue = "[";
            for(int i = 0; i < this.combustivel.length-1; i++){
                combValue = combValue + this.combustivel[i] + ",";
            }
            combValue = combValue + this.combustivel[this.combustivel.length-1] + "]";
            returnValue = String.format("[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %s ## %s ## %s ## %s ## %s ## %s ## %b ## %s]",this.id,this.marca,this.modelo,this.ano,this.categoria,combValue,this.cilindros, df.format(this.cilindrada),this.transmissao,this.tracao,df.format(this.consumo_cidade), df.format(this.consumo_estrada),df.format(this.co2),this.turbo,this.data_registro.format());
            return returnValue;
        }

        public static Veiculo[] addVeiculo(Veiculo[] oldVeiculo, Veiculo veiculoInput){
            int newtamanho = oldVeiculo.length + 1;
            Veiculo[] returnVeiculo = new Veiculo[newtamanho];
            for(int i = 0; i < newtamanho - 1; i++){
                returnVeiculo[i] = oldVeiculo[i];
            }
            returnVeiculo[newtamanho - 1] = veiculoInput;

            return returnVeiculo;
        }

        public static Veiculo[] swap(Veiculo[] entryVector, int posX, int posY){
            Veiculo tempVeiculo = entryVector[posX];
            entryVector[posX] = entryVector[posY];
            entryVector[posY] = tempVeiculo;

            return entryVector;
        }
    }

    public static class LeitorCsv{
        public static Veiculo[] ler(String caminho){
            File csvFile = new File(caminho);
            Veiculo[] veiculos = new Veiculo[1];
            try { 
                Scanner sc = new Scanner(csvFile);
                String entryString = sc.nextLine(); // Usado para passar da 1° linha de colunas
                if(sc.hasNextLine()){
                    veiculos[0] = Veiculo.parseVeiculo(sc.nextLine()); // Adiciona o primeiro elemento no vetor
                }
                while(sc.hasNextLine()){
                    entryString = sc.nextLine();
                    Veiculo newVeiculo = Veiculo.parseVeiculo(entryString);
                    veiculos = Veiculo.addVeiculo(veiculos,newVeiculo);
                }
            } catch(FileNotFoundException error){
                System.err.println("Não foi possivel ler o arquivo");
            }
            return veiculos;
        }
    }

    public static class CellVeiculo{
        private Veiculo val;
        private CellVeiculo prox;
        private CellVeiculo ant;

        public CellVeiculo(){
            this.val = null;
            this.prox = null;
            this.ant = null;
        }

        public CellVeiculo(Veiculo val){
            this.val = val;
            this.prox = null;
            this.ant = null;
        }
    }

    public static class ListVeiculo{
        public CellVeiculo init;
        public CellVeiculo end;
        public int size;

        public ListVeiculo(CellVeiculo init, CellVeiculo end){
            this.init = init;
            this.end = end;
            this.size = 2;
            this.init.prox = this.end;
            this.end.prox = null;
            this.end.ant = this.init;
            this.init.ant = null;
        }

        public void addInitVeiculo(CellVeiculo entryVeiculo){
            CellVeiculo CellTemp = this.init;
            this.init.ant = entryVeiculo;
            this.init = entryVeiculo;
            this.init.prox = CellTemp;
            this.init.ant = null;
            this.size++;
        }

        public void addEndVeiculo(CellVeiculo entryVeiculo){
            CellVeiculo CellTemp = this.end;
            this.end.prox = entryVeiculo;
            this.end = entryVeiculo;
            this.end.ant = CellTemp;
            this.end.prox = null;
            this.size++;
        }
    }


    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        Veiculo[] veiculos = LeitorCsv.ler("../veiculos.csv");
        Veiculo[] vetorListado = new Veiculo[1];
        Scanner scan = new Scanner(System.in);
        int id = scan.nextInt();
        boolean hasVeiculo = false;
        if(id != -1){
            hasVeiculo = true;
            vetorListado[0] = Veiculo.searchLinearVeiculoId(veiculos,id);
        }
        while(id != -1){
            id = scan.nextInt();
            if(id != -1){ // O -1 é apenas a sentinela, não deve entrar no vetor
                vetorListado = Veiculo.addVeiculo(vetorListado, Veiculo.searchLinearVeiculoId(veiculos,id));
            }
        }
        if(hasVeiculo){ 
            /**
             *
             * Ordena usando ordenação por inserção
             *
             */

            for(int i = 1; i < vetorListado.length; i++){
                Veiculo temp = vetorListado[i]; // Guarda o elemento a ser inserido
                int j = i - 1;
                // Desloca para a direita todos os que vêm depois de temp
                while(j >= 0 && vetorListado[j].getMarca().compareTo(temp.getMarca()) > 0){
                    vetorListado[j + 1] = vetorListado[j];
                    j--;
                }
                vetorListado[j + 1] = temp; // Insere no buraco encontrado
            }

            for(int i = 0; i < vetorListado.length; i++){
                System.out.println(vetorListado[i].format());
            }
        }
    }
}
