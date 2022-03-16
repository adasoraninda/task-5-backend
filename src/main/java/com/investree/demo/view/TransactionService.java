package com.investree.demo.view;

import com.investree.demo.model.Transaction;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface TransactionService {

  Map save(Transaction transaction);

  Map updateStatus(Transaction transaction);

}
