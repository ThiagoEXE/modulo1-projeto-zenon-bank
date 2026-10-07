package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.lang.Integer.parseInt;

public class TransactionIngestor {

    private final String nomeArquivo;
    private List<Transaction> transactions;

    public TransactionIngestor(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.transactions = new ArrayList<>();
    }

    public List<Transaction> lerArquivoCSV() {

        try {
            Path arquivo = Path.of(nomeArquivo);
            String conteudo = Files.readString(arquivo);

            String[] linhaArquivo = conteudo.split("\n");

            for(int i = 1; i < linhaArquivo.length; i++) {
                String linha = linhaArquivo[i];
                String[] colunas = linha.split(",");

                boolean validacao = validarEntradaDeDados(colunas, linhaArquivo);
                if (validacao) {
                    int step = parseInt(colunas[0]);
                    Transaction.Type type = Transaction.Type.valueOf(colunas[1]);
                    BigDecimal amount = new BigDecimal(colunas[2]);
                    String nameOrig = colunas[3];
                    BigDecimal oldbalanceOrg = new BigDecimal(colunas[4]);
                    BigDecimal newbalanceOrig = new BigDecimal(colunas[5]);
                    String nameDest = colunas[6];
                    BigDecimal oldbalanceDest = new BigDecimal(colunas[7]);
                    BigDecimal newbalanceDest = new BigDecimal(colunas[8]);
                    boolean isFraud = Boolean.valueOf(colunas[9]);
                    boolean isFlaggedFraud = Boolean.valueOf(colunas[10]);

                    ArrayList<Object> dados = new ArrayList<>();
                    dados.add(step);
                    dados.add(type);
                    dados.add(amount);
                    dados.add(nameOrig);
                    dados.add(oldbalanceOrg);
                    dados.add(newbalanceOrig);
                    dados.add(nameDest);
                    dados.add(oldbalanceDest);
                    dados.add(newbalanceDest);
                    dados.add(isFraud);
                    dados.add(isFlaggedFraud);
                    Transaction transaction = new Transaction(step, type, amount, nameOrig, oldbalanceOrg, newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest, isFraud, isFlaggedFraud);
                    this.transactions.add(transaction);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.transactions;
    }

    public boolean validarEntradaDeDados(String[] colunas, String[] linhaArquivo) {

        String linha = linhaArquivo[0];
        String[] cabecalho = linha.split(",");
        for (int j = 0; j < colunas.length; j++) {
            if(j == 0 && parseInt(colunas[j]) < 1){
                System.err.println("Erro: " + Arrays.toString(colunas) + " step deve ser maior ou igual a 1");
                return false;
            }
            if(colunas[j] == null || Objects.equals(colunas[j], "")){
                System.err.println("Erro: " + Arrays.toString(colunas) + " campo: " + cabecalho[j]+ " não pode ser null");
                return false;
            }
            if (new BigDecimal(colunas[2]).compareTo(BigDecimal.ZERO) < 0
                    || new BigDecimal(colunas[4]).compareTo(BigDecimal.ZERO) < 0
                    || new BigDecimal(colunas[5]).compareTo(BigDecimal.ZERO) < 0
                    || new BigDecimal(colunas[7]).compareTo(BigDecimal.ZERO) < 0
                    || new BigDecimal(colunas[8]).compareTo(BigDecimal.ZERO) < 0) {
                System.err.println("Erro: " + Arrays.toString(colunas) + " campo: " + cabecalho[j]+ " valor não pode ser negativo");
                return false;
            }
            if (j == 1) {
                try {
                    Transaction.Type.valueOf(colunas[j]);
                } catch (IllegalArgumentException ex) {
                    System.err.println("Erro: " + Arrays.toString(colunas) + " tipo: " + colunas[j] +" não cadastrado");
                    return false;
                }
            }
        }

        return true;
    }
}