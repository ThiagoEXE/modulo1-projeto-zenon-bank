package br.com.zenon.fraud;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TransactionSQLRepository implements TransactionRepository{
    private Optional<Transaction> fraud;

    @Override
    public Optional<Transaction> findByOriginName(String nome) {
        String sql = "SELECT * FROM fraud WHERE name_orig = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fraud", "root", "senha123");
             PreparedStatement ps = connection.prepareStatement(sql);


        ) {
            ps.setString(1, nome);

            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    // 1- Lê a fraude do banco de dados
                    int step = rs.getInt("step");
                    String type = rs.getString("type");
                    BigDecimal amount = rs.getBigDecimal("amount");
                    String nameOrig = rs.getString("name_orig");
                    BigDecimal oldbalanceOrig = rs.getBigDecimal("old_balance_orig");
                    BigDecimal newbalanceOrig = rs.getBigDecimal("new_balance_orig");
                    String nameDest = rs.getString("name_dest");
                    BigDecimal oldbalanceDest = rs.getBigDecimal("old_balance_dest");
                    BigDecimal newbalanceDest = rs.getBigDecimal("new_balance_dest");
                    boolean isFraud = rs.getBoolean("is_fraud");
                    boolean isFlaggedFraud = rs.getBoolean("is_flagged_fraud");

                    // 2- Cria O objeto
                    Transaction.Type typeTransaction = Transaction.Type.valueOf(type);
                    Transaction transaction = new Transaction(step, typeTransaction, amount, nameOrig, oldbalanceOrig, newbalanceOrig, nameDest, oldbalanceDest, newbalanceDest, isFraud, isFlaggedFraud);
                    this.fraud = Optional.ofNullable(transaction);
                    // 3- Retorna o stream com o registro
                    return this.fraud.stream().findFirst();
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public void save(Transaction transaction) {

        String sql = "INSERT INTO fraud (step, type, amount, name_orig, old_balance_orig, new_balance_orig," + " name_dest, old_balance_dest, new_balance_dest, is_fraud, is_flagged_fraud) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fraud", "root", "senha123");
             PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, transaction.step());
                ps.setString(2, transaction.type().toString());
                ps.setBigDecimal(3, transaction.amount());
                ps.setString(4, transaction.nameOrig());
                ps.setBigDecimal(5, transaction.oldbalanceOrg());
                ps.setBigDecimal(6, transaction.newbalanceOrig());
                ps.setString(7, transaction.nameDest());
                ps.setBigDecimal(8, transaction.oldbalanceDest());
                ps.setBigDecimal(9, transaction.newbalanceDest());
                ps.setBoolean(10, transaction.isFraud());
                ps.setBoolean(11, transaction.isFlaggedFraud());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
