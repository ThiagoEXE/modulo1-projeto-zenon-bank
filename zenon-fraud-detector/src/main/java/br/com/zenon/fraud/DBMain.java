package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DBMain {

    void main() throws SQLException {
        this.readCSVAndInsertData();
    }
    public void readCSVAndInsertData() throws SQLException {
        TransactionIngestor transactionIngestor = new TransactionIngestor("data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> listaDeTransacoes = transactionIngestor.lerArquivoCSV();
        TransactionSQLRepository transactionSQLRepository = new TransactionSQLRepository();

        for (Transaction transaction : listaDeTransacoes) {
            transactionSQLRepository.save(transaction);
        }
    }

}
