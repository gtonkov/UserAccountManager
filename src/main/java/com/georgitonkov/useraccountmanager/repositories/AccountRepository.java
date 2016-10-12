package com.georgitonkov.useraccountmanager.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.georgitonkov.useraccountmanager.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {
	Account findById(long id);
	List<Account> findAll();
	@SuppressWarnings("unchecked")
	Account save(Account account);
	void delete(Account account);
}
