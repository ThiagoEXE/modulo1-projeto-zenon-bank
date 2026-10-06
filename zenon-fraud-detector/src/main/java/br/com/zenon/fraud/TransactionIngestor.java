package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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

            for(int i = 1; i <= 1000; i++) {
                String linha = linhaArquivo[i];
                String[] colunas = linha.split(",");

                int step = Integer.parseInt(colunas[0]); 
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

                Transaction transaction = new Transaction(step, type, amount, nameOrig, oldbalanceOrg, newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest, isFraud, isFlaggedFraud);
                this.transactions.add(transaction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.transactions;
    }
}