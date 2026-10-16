package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class ReportMain {

    public static void main(String[] args) {
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

        String language = args.length > 0 ? args[0] : "pt";
        Locale locale;
        if (language.equals("en")) {
            locale = Locale.US;
        } else {
            locale = Locale.of("pt", "BR");
        }


        ResourceBundle reportUS = ResourceBundle.getBundle("report", locale);

        BigDecimal valueTotal = streamLazy3.skip(1)
                .map(line -> line.split(","))
                .map(columns -> new BigDecimal (columns[2].trim()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        IO.println(reportUS.getString("valor.total") + " " + NumberFormat.getCurrencyInstance(locale).format(valueTotal));
        streamLazy3.close();

    }
}
