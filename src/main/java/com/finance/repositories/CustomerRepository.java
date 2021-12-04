package com.finance.repositories;

import java.util.Optional;
import java.util.Set;

import com.finance.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

  @Query("SELECT c from Customer c where c.financeId= :financeId and c.customerName= :name")
  Optional<Customer> getCustomer(String financeId, String name);

  @Query("SELECT c from Customer c where c.financeId= :financeId")
  Set<Customer> getCustomers(final String financeId);
}
