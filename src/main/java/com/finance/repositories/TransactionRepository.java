package com.finance.repositories;

import java.util.Set;

import com.finance.entities.TransactionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

  @Query("SELECT t FROM TransactionEntity t where t.financeIdRef= :financeId and t.customerIdRef= :customerRef")
  Set<TransactionEntity> getAllTransaction(String financeId, long customerRef);
}
