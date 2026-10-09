package br.com.zenon.fraud;

import java.util.Optional;

public interface TransactionRepository {

 public Optional<Transaction> buscaTransacao(Optional<Transaction> listaDeTransactions, String nome);
}