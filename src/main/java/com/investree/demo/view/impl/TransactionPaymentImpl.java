package com.investree.demo.view.impl;

import com.investree.demo.model.Transaction;
import com.investree.demo.repository.TransactionRepository;
import com.investree.demo.view.TransactionService;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class TransactionPaymentImpl implements TransactionService {

  private final TransactionRepository repository;

  @Override
  public Map save(Transaction transaction) {
    try {
      return mappingData(repository.save(transaction));
    } catch (Exception e) {
      e.printStackTrace();
      return mapError(e);
    }
  }

  @Override
  @SneakyThrows
  public Map updateStatus(Transaction transaction) {
    try {
      Transaction trxFromDb = repository.findById(transaction.getId())
          .orElseThrow(() -> new Exception("Transaksi tidak ditemukan"));

      trxFromDb.setId(transaction.getId());
      trxFromDb.setStatus("lunas");

      return mappingData(repository.save(trxFromDb));
    } catch (Exception e) {
      e.printStackTrace();
      return mapError(e);
    }

  }

  @Override
  public Page<Transaction> list(String status, Integer page, Integer size) {
    try {
      Pageable pageable = PageRequest.of(page, size);

      if (status.isEmpty()) {
        return repository.findAll(pageable);
      }

      return repository.findAll(status, pageable);
    } catch (Exception e) {
      e.printStackTrace();
      return Page.empty();
    }
  }

  private Map mappingData(Transaction transaction) {
    Map mapData = new HashMap();
    mapData.put("id", transaction.getId());
    mapData.put("tenor", transaction.getTenor());
    mapData.put("totalPinjaman", transaction.getTotalLoan());
    mapData.put("bungaPersen", transaction.getPercentInterest());
    mapData.put("status", transaction.getStatus());
    mapData.put("peminjam", transaction.getBorrowerUser());
    mapData.put("meminjam", transaction.getUserBorrow());

    return mapData;
  }

  private Map mapError(Exception e) {
    Map mapError = new HashMap();
    mapError.put("message", e.getMessage());
    return mapError;
  }

}
