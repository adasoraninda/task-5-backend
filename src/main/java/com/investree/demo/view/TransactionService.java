package com.investree.demo.view;

import com.investree.demo.model.Transaction;
import java.util.Map;
import org.springframework.data.domain.Page;

@SuppressWarnings("rawtypes")
public interface TransactionService {

  Map save(Transaction transaction);

  Map updateStatus(Transaction transaction);

  Page<Transaction> list(String status, Integer page, Integer size);
}
