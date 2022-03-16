package com.investree.demo.repository;

import com.investree.demo.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

  @Query("SELECT * FROM transaksi t WHERE t.status LIKE %:status%")
  Page<Transaction> findAll(
      @Param(value = "status") String status, Pageable pageable
  );

}
