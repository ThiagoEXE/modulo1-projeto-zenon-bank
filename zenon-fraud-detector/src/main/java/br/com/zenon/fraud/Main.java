package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        /*Transaction transaction1 = new Transaction(
                1,
                PAYMENT,
                new BigDecimal("9839.64"),
                "C1231006815",
                new BigDecimal("170136.0"),
                new BigDecimal("160296.36"),
                "M1979787155",
                new BigDecimal("0.0"),
                new BigDecimal("0.0"),
                false,
                false);
        Transaction transaction2 = new Transaction(
                743,
                CASH_OUT,
                new BigDecimal("850002.52"),
                "C1280323807",
                new BigDecimal("850002.52"),
                new BigDecimal("0.0"),
                "C873221189",
                new BigDecimal("6510099.11"),
                new BigDecimal("7360101.63"),
                true,
                false
        );

        System.out.println(transaction1);
        System.out.println(transaction2);
        System.out.println("==============================");*/

        /*TransactionIngestor transactionIngestor = new TransactionIngestor("data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> listaDeTransactions = transactionIngestor.lerArquivoCSV();
        for (int i = 0; i < 10; i++) {
            System.out.println(listaDeTransactions.get(i).toString());
        }*/
        TransactionIngestor transactionIngestor = new TransactionIngestor("data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> listaDeTransactions = transactionIngestor.lerArquivoCSV();
        System.out.println(listaDeTransactions.size());
        System.out.println(listaDeTransactions.get(0).toString());
        long qtdFraud = listaDeTransactions.stream()
                .filter(Transaction::isFraud)
                .count();
        System.out.println("1. Total de Fraudes:: " + qtdFraud);

        List<Transaction> tresMaioresValores = listaDeTransactions.stream()
                .filter(t-> t != null && t.amount() != null)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .limit(3)
                .toList();

        tresMaioresValores.forEach(t -> System.out.println("2. Top 3 Fraudes de Maior Valor:" + " - R$ " + t.amount().floatValue()));

    }

}
