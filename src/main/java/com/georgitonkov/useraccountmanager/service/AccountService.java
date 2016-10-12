package com.georgitonkov.useraccountmanager.service;

import java.util.List;

import com.georgitonkov.useraccountmanager.dto.AccountDTO;

public interface AccountService {
	public List<AccountDTO> getAllAccounts();
	public void createAccount(AccountDTO account);
	public void deleteAccount(long id);
	public AccountDTO getAccount(long id);
	public void editAccount(AccountDTO account, long id);
}
