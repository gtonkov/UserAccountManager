package com.georgitonkov.useraccountmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.georgitonkov.useraccountmanager.dto.AccountDTO;

public interface AccountService {
	public Page<AccountDTO> getAllAccounts(Pageable pageable);
	public void createAccount(AccountDTO account);
	public void deleteAccount(long id);
	public AccountDTO getAccount(long id);
	public void editAccount(AccountDTO account, long id);
}
