package br.com.zenon.fraud;

import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

            for (int i = 1; i < linhaArquivo.length; i++) {
                String linha = linhaArquivo[i];
                String[] colunas = linha.split(",");

                try {

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

                    boolean validacao = validarEntradaDeDados(colunas, linhaArquivo);

                    if (validacao) {

                        Optional<Transaction> transaction = Optional.of(new Transaction(step, type, amount, nameOrig, oldbalanceOrg, newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest, isFraud, isFlaggedFraud));
                        this.transactions.add(transaction.get());
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println("Erro: " + Arrays.toString(colunas) + " | " + e.getClass().getName() + ": " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.transactions;
    }

    public boolean isNumeroNegativo(String numero) {
        if (numero == null || numero.isEmpty()) {
            return false;
        }
        try {
            BigDecimal valor = new BigDecimal(numero);
            return valor.compareTo(BigDecimal.ZERO) < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean validarEntradaDeDados(String[] colunas, String[] linhaArquivo) {

        String linha = linhaArquivo[0];
        String[] cabecalho = linha.split(",");
        for (int j = 0; j < colunas.length; j++) {
            if (j == 0 && parseInt(colunas[j]) < 1) {
                throw new IllegalArgumentException("step deve um número positivo: " + colunas[j]);
            }
            if (colunas[j] == null || Objects.equals(colunas[j], "")) {
                throw new IllegalArgumentException("campo: " + cabecalho[j] + " não pode vazio");
            }
            if (cabecalho[j].equals("amount") && isNumeroNegativo(colunas[2])
                    || cabecalho[j].equals("oldbalanceOrg") && isNumeroNegativo(colunas[4])
                    || cabecalho[j].equals("newbalanceOrg") && isNumeroNegativo(colunas[5])
                    || cabecalho[j].equals("oldbalanceDest") && isNumeroNegativo(colunas[7])
                    || cabecalho[j].equals("newbalanceDest") && isNumeroNegativo(colunas[8])) {
                throw new IllegalArgumentException("campo: " + cabecalho[j] + " deve ser um número positivo: " + colunas[j]);
            }
            if (j == 1) {
                try {
                    Transaction.Type.valueOf(colunas[j]);
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("tipo: " + colunas[j] + " não cadastrado");
                }
            }
        }

        return true;
    }
}
