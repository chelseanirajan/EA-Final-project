package edu.miu.badge.services;

import edu.miu.badge.exceptions.TransactionNotFoundException;
import edu.miu.badge.dto.RequestTransactionDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;

import java.util.List;

public interface TransactionService {
    ResponseTransactionDTO createTransaction(RequestTransactionDTO transaction) throws TransactionNotFoundException;
    ResponseTransactionDTO getTransaction(int id) throws TransactionNotFoundException;
    List<ResponseTransactionDTO> getAllTransactions();
}
