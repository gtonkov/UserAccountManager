package com.georgitonkov.useraccountmanager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.georgitonkov.useraccountmanager.model.Account;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
	Account findById(long id);
	Page<Account> findAll(Pageable pageable);
	@SuppressWarnings("unchecked")
	Account save(Account account);
	void delete(Account account);
}
