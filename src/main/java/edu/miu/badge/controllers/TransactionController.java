package edu.miu.badge.controllers;

import edu.miu.badge.dto.RequestTransactionDTO;
import edu.miu.badge.dto.ResponseTransactionDTO;
import edu.miu.badge.exceptions.ResourceNotFoundException;
import edu.miu.badge.exceptions.TransactionNotFoundException;
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
    public ResponseEntity<?> createTransaction(@RequestBody RequestTransactionDTO requestTransactionDTO)  {
        try{
            return new ResponseEntity<>(transactionService.createTransaction(requestTransactionDTO), HttpStatus.OK);
        }catch (TransactionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/transactions/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable int id) throws TransactionNotFoundException {
        try{
            ResponseTransactionDTO transaction = transactionService.getTransaction(id);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        }catch (TransactionNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/transactions")
    public List<ResponseTransactionDTO> getAllTransactions(){
        return transactionService.getAllTransactions();
    }


}
