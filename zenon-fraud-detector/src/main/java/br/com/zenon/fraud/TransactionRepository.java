package br.com.zenon.fraud;

import java.util.Optional;

public interface TransactionRepository {

 public Optional<Transaction> findByOriginName(String nome);
}