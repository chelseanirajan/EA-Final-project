package edu.miu.badge.controllers;

import edu.miu.badge.dto.RequestTransactionDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;
import edu.miu.badge.exceptions.TransactionDeclinedException;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<?> createTransaction(@RequestBody RequestTransactionDTO requestTransactionDTO) throws TransactionDeclinedException {
            return new ResponseEntity<>(transactionService.createTransaction(requestTransactionDTO), HttpStatus.OK);
    }
    @GetMapping("/transactions/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable int id) throws ResourceNotFoundException {
        return new ResponseEntity<>(transactionService.getTransaction(id), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public List<ResponseTransactionDTO> getAllTransactions(){
        return transactionService.getAllTransactions();
    }


}
