package com.finance.repositories;

import java.util.Optional;

import com.finance.entities.FinanceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends CrudRepository<FinanceEntity, String> {
  @Query("SELECT f from FinanceEntity f where f.financeId= :financeId")
  Optional<FinanceEntity> getFinanceEntity(String financeId);
}
