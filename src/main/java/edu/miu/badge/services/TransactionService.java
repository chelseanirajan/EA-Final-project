package edu.miu.badge.services;

import edu.miu.badge.exceptions.TransactionDeclinedException;
import edu.miu.badge.dto.RequestTransactionDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;
import edu.miu.badge.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TransactionService {
    ResponseTransactionDTO createTransaction(RequestTransactionDTO transaction) throws TransactionDeclinedException;
    ResponseTransactionDTO getTransaction(int id) throws ResourceNotFoundException;
    List<ResponseTransactionDTO> getAllTransactions();
}
