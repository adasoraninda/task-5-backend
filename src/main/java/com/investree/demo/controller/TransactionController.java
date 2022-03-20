package com.investree.demo.controller;

import com.investree.demo.model.Transaction;
import com.investree.demo.utils.ResponseWrapper;
import com.investree.demo.view.TransactionService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/transaksi")
@SuppressWarnings("rawtypes")
public class TransactionController {

  private final TransactionService service;
  private final ResponseWrapper wrapper;

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Map> save(
      @RequestBody Transaction transaction
  ) {
    return wrapper.getResult(service.save(transaction));
  }

  @PutMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Map> updateStatus(
      @RequestBody Transaction transaction
  ) {
    return wrapper.getResult(service.updateStatus(transaction));
  }

  @GetMapping("/list")
  public ResponseEntity<Page<Transaction>> list(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size,
      @RequestParam(required = false, defaultValue = "") String status
  ) {
    return ResponseEntity.ok(service.list(status, page, size));
  }

}
