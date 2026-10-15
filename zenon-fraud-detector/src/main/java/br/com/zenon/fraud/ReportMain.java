package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class ReportMain {

    static void main() {
        System.out.println("==============================");
        TransactionReport transactionReport = new TransactionReport();
        Stream<String> streamLazy1 = transactionReport.lazyFileCSV("data/PS_20174392719_1491204439457_log.csv");

        long numberOFLines = streamLazy1.skip(1).count();
        IO.println("Total de linhas: "+ numberOFLines);

        streamLazy1.close();

        Stream<String> streamLazy2 = transactionReport.lazyFileCSV("data/PS_20174392719_1491204439457_log.csv");
        int totalFraud = streamLazy2.skip(1)
                .map(line -> line.split(","))
                .mapToInt(columns -> Integer.parseInt(columns[9].trim()))
                .filter(value -> value == 1)
                .sum();
        IO.println("Total de fraudes: " + totalFraud);
        streamLazy2.close();

        Stream<String> streamLazy3 = transactionReport.lazyFileCSV("data/PS_20174392719_1491204439457_log.csv");

        BigDecimal valueTotal = streamLazy3.skip(1)
                .map(line -> line.split(","))
                .map(columns -> new BigDecimal (columns[5].trim()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        IO.println("Valor total transacionado: " + valueTotal);
        streamLazy3.close();
    }
}
