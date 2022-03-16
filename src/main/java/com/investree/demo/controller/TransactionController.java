package com.investree.demo.controller;

import com.investree.demo.model.Transaction;
import com.investree.demo.repository.TransactionRepository;
import com.investree.demo.view.TransactionService;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/transaksi")
@SuppressWarnings({"rawtypes", "unchecked"})
public class TransactionController {

  private final TransactionService service;
  private final TransactionRepository repository;

  @PostMapping
  public ResponseEntity<Map> save(Transaction transaction) {
    return getResult(service.save(transaction));
  }

  @PutMapping
  public ResponseEntity<Map> updateStatus(Transaction transaction) {
    return getResult(service.updateStatus(transaction));
  }

  @GetMapping("/list")
  public ResponseEntity<Page<Transaction>> list(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestParam String status
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(repository.findAll(status, pageable));
  }

  private ResponseEntity<Map> getResult(Map data) {
    if (data.get("message") != null) {
      return ResponseEntity.internalServerError()
          .body(mapResponse(500, "gagal", data));
    }

    return ResponseEntity.ok(
        mapResponse(
            200,
            "sukses",
            data)
    );
  }

  private Map mapResponse(int code, String status, Map data) {
    Map mapResponse = new HashMap();
    mapResponse.put("data", data);
    mapResponse.put("status", status);
    mapResponse.put("code", code);
    return mapResponse;
  }

}
