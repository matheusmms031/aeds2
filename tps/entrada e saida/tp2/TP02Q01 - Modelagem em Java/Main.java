import java.io.File;
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
            return String.format("%d/%d/%d",this.ano,this.mes,this.dia);
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
            String returnValue;
            String combValue = "[";
            for(int i = 0; i < this.combustivel.length-1; i++){
                combValue = combValue + this.combustivel[i] + ",";
            }
            combValue = combValue + this.combustivel[this.combustivel.length-1] + "]";
            returnValue = String.format("[%d ## %s ## %s ## %d ## %s ## %s ## %d ## %f ## %s ## %s ## %f ## %f ## %f ## %b ## %s]",this.id,this.marca,this.modelo,this.ano,this.categoria,combValue, this.cilindros, this.cilindrada,this.transmissao,this.tracao,this.consumo_cidade, this.consumo_estrada,this.co2,this.turbo,this.data_registro.format());
            return returnValue;
        }
    }

    public static class LeitorCsv{
        public static Veiculo[] ler(String caminho){
            File csvFile = new File(caminho);

        }
    }


    public static void main(String[] args){
        Data teste = Data.parseData("2020-30-10");
        Veiculo testeVeiculo = Veiculo.parseVeiculo("64472,Nissan,VERSA,2012,Compact Cars,Gasoline,4,1.8,CVT,Front-Wheel Drive,11.90,14.45,184.1,false,2011-03-01");
        System.out.println(testeVeiculo.format());
    }
}
