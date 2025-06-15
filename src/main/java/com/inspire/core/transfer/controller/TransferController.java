package com.inspire.core.transfer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inspire.core.transfer.entity.Transfer;
import com.inspire.core.transfer.service.TransferService;

@RestController
@RequestMapping("/api/pay")
public class TransferController {

    @Autowired
    private TransferService service;

    @PostMapping("/salary")
    public ResponseEntity<Transfer> paySalary(@RequestBody Transfer transfer) {
        return ResponseEntity.ok(service.processSalary(transfer));
    }

    @GetMapping("/transfers")
    public ResponseEntity<List<Transfer>> getAll() {
        return ResponseEntity.ok(service.getAllTransfers());
    }
}
