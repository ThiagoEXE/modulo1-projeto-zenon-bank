package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DBMain {

    public static void main(String[] args) throws SQLException {

        TransactionIngestor transactionIngestor = new TransactionIngestor("../data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> listaDeTransacoes = transactionIngestor.lerArquivoCSV();
        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();

        long start = System.currentTimeMillis();
        for (Transaction transaction : listaDeTransacoes) {
            transactionSQLRepository.save(transaction);
        }

        IO.println("Tempo de execução: " + (start - System.currentTimeMillis() + " ms"));

        Optional<Transaction> transaction1 = transactionSQLRepository.findByOriginName("C1231006815");
            transaction1.ifPresentOrElse(System.out::println, () -> System.out.println("Transação não encontrada"));

        Optional<Transaction> transaction2 = transactionSQLRepository.findByOriginName("C12345");
        transaction2.ifPresentOrElse(System.out::println, () -> System.out.println("Transação não encontrada"));
    }

}
