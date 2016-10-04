package com.georgitonkov.dao;

import com.georgitonkov.model.Account;
import java.util.List;

public interface AccountDAO {
	
	List<Account> getAllAccounts();
	
	void createAccount(Account account);
	
	void deleteAccount(long id);
	
	Account getAccount(long id);
	
	void editAccount(Account account);
}
