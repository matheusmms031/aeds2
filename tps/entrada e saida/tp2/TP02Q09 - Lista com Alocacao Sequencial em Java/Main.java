import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Locale;
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

        public CellVeiculo(){
            this.val = null;
            this.prox = null;
        }

        public CellVeiculo(Veiculo val){
            this.val = val;
            this.prox = null;
        }
    }

    public static class ListVeiculo{
        public CellVeiculo init;
        public CellVeiculo end;
        public int size;

        public ListVeiculo(){
            this.init = null;
            this.end = null;
            this.size = 0;
        }

        public String formatAll(){
            DecimalFormat df = new DecimalFormat("#.##");
            CellVeiculo tempE = this.init;
            String returnValue = "";
            for(int i = 0; i < this.size; i++, tempE = tempE.prox){
                String combValue = "[";
                for(int j = 0; j < tempE.val.combustivel.length-1; j++){
                    combValue = combValue + tempE.val.combustivel[j] + ",";
                }
                combValue = combValue + tempE.val.combustivel[tempE.val.combustivel.length-1] + "]";
                returnValue += String.format("[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %s ## %s ## %s ## %s ## %s ## %s ## %b ## %s]\n",tempE.val.id,tempE.val.marca,tempE.val.modelo,tempE.val.ano,tempE.val.categoria,combValue,tempE.val.cilindros, df.format(tempE.val.cilindrada),tempE.val.transmissao,tempE.val.tracao,df.format(tempE.val.consumo_cidade), df.format(tempE.val.consumo_estrada),df.format(tempE.val.co2),tempE.val.turbo,tempE.val.data_registro.format());
            }
            return returnValue;
        }

        public void inserirInicio(Veiculo entryVeiculo){
            CellVeiculo entryVeiculoCelula = new CellVeiculo(entryVeiculo); // CellVeiculo entryVeiculo | entryVeiculo.prox() -> null
            if(this.init != null || this.end != null || this.size > 0){
                CellVeiculo CellTemp = this.init; 
                this.init = entryVeiculoCelula;
                this.init.prox = CellTemp;
                this.size++;
            } else{
                this.init = entryVeiculoCelula;
                this.init.prox = this.end;
                this.end = entryVeiculoCelula;
                this.size++;
            }
        }

        public void inserirFim(Veiculo entryVeiculo){
            CellVeiculo entryVeiculoCelula = new CellVeiculo(entryVeiculo);
            if(this.init != null || this.end != null || this.size > 0){
                this.end.prox = entryVeiculoCelula;
                this.end = entryVeiculoCelula;
                this.end.prox = null;
                this.size++;
            } else{
                this.init = entryVeiculoCelula;
                this.end = entryVeiculoCelula;
                this.init.prox = this.end;
                this.size++;
            }
        }

        public Veiculo removerFim(){
            CellVeiculo pointerCellVeiculo = this.init;
            if (this.init != null || this.end != null || this.size > 0) {
                while(pointerCellVeiculo.prox.prox != null){ // vê se o proximo possui proximo, irá caminhar até que pointerCellVeiculo seja igual ao penultimo
                    pointerCellVeiculo = pointerCellVeiculo.prox;
                }
                CellVeiculo ultimo = pointerCellVeiculo.prox;
                pointerCellVeiculo.prox = null; // Retira o referencial da ultima celula, ou seja, a exclui, a unica referencia que tem ela agora é a ultimo!
                this.end = pointerCellVeiculo; // Ultimo elemento agora é o penultimo de antes!
                this.size--;
                return ultimo.val; // Deve retornar um veiculo e não a celula!
            }

            return null; // Retorna null quando não há o que remover!
        }

        public Veiculo removerInicio(){
            if (this.init != null || this.end != null || this.size > 0) {
                CellVeiculo tempVeiculo = this.init;
                this.init = this.init.prox; // this.init agora é o proximo dele! Perde referencial e afins
                this.size = this.size - 1;

                return tempVeiculo.val;
            }
            return null; // Retorna null quando não há o que remover!
        }

        public Veiculo remover(int pos){
            if (this.init != null || this.end != null || this.size > 0) {
                CellVeiculo pointerVeiculo = this.init;
                CellVeiculo temp;
                int i;
                for(i = 0; i < pos-1; i++){
                    pointerVeiculo = pointerVeiculo.prox;
                }
                temp = pointerVeiculo.prox;
                pointerVeiculo.prox = pointerVeiculo.prox.prox; // Salta o proximo elemento de pos-1 para o proximo do proximo, ignorando o proximo, que é o que queremos remover.
                return temp.val;
            }
            return null;
        }

        public void inserir(int pos, Veiculo entryVeiculo){
            if (this.init != null || this.end != null || this.size > 0) {
                CellVeiculo pointerVeiculo = this.init;
                CellVeiculo temp = new CellVeiculo(entryVeiculo);
                int i;
                for(i = 0; i < pos-1; i++){
                    pointerVeiculo = pointerVeiculo.prox;
                }
                temp.prox = pointerVeiculo.prox;
                pointerVeiculo.prox = temp;
            }
        }
    }


    public static void main(String[] args){
        Locale.setDefault(Locale.US);
        Veiculo[] veiculos = LeitorCsv.ler("tps\\entrada e saida\\tp2\\TP02Q09 - Lista com Alocacao Sequencial em Java\\veiculos.csv");
        ListVeiculo listaVeiculos = new ListVeiculo();
        Scanner scan = new Scanner(System.in);
        int id = 0;
        while(id != -1){
            id = scan.nextInt();
            if(id != -1){ // O -1 é apenas a sentinela, não deve entrar no vetor
                listaVeiculos.inserirFim(Veiculo.searchLinearVeiculoId(veiculos,id));
            }
        }
        int q = scan.nextInt();
        for(int i = 0; i < q; i++){
            String f = scan.next();
            int pI = scan.nextInt(); // O inteiro padrão que sempre terá | pInclusive
            // Provavelmente dá para trocar por switch, mas não vou fazer pq não sei ainda como faz isso em JAVA e nem o comportamento da linguagem
            if (f.equals("RF")) {
                listaVeiculos.removerFim();
            } else if (f.equals("RI")) { // Só é verificado caso RF seja falso!
                listaVeiculos.removerInicio();
            } else if (f.equals("R*")) {
                listaVeiculos.remover(pI);
            } else if (f.equals("II")) {
                listaVeiculos.inserirInicio(Veiculo.searchLinearVeiculoId(veiculos,pI));
            } else if(f.equals("IF")){
                listaVeiculos.inserirFim(Veiculo.searchLinearVeiculoId(veiculos,pI));
            }
            else if (f.equals("I*")){
                int pE = scan.nextInt(); // O inteiro padrão que poderá haver | pExclusive
                listaVeiculos.inserir(pI, Veiculo.searchLinearVeiculoId(veiculos, pE));
            }
        }
        System.out.println(listaVeiculos.formatAll());
    }
}
