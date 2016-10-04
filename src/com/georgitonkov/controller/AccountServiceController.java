package com.georgitonkov.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.georgitonkov.dao.AccountDAO;
import com.georgitonkov.model.Account;

@RestController
public class AccountServiceController {
	
	@Autowired
	AccountDAO accountDAO;
	
	@RequestMapping(value="/acc/", method=RequestMethod.GET)
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = accountDAO.getAllAccounts();
		if (!accounts.isEmpty()) {
			return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
		}
		return new ResponseEntity<List<Account>>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/acc/{id}", method=RequestMethod.GET)
	public ResponseEntity<Account> getAccount(@PathVariable("id") long id) {
		Account account = accountDAO.getAccount(id);
		if (!(account == null)) {
			return new ResponseEntity<Account>(account, HttpStatus.OK);
		}
		return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/acc/", method=RequestMethod.POST)
	public ResponseEntity<Void> createAccount(@RequestBody Account account, UriComponentsBuilder builder) {
		accountDAO.createAccount(account);
		
		UriComponents components = builder.path("/acc/{id}").buildAndExpand(account.getId());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(components.toUri());
		
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/acc/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAccount(@PathVariable("id") long id) {
		if(accountDAO.getAccount(id) == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		accountDAO.deleteAccount(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/acc/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> editAccount(@RequestBody Account account, @PathVariable("id") long id) {
		Account currentAccount = accountDAO.getAccount(id);
		
		if (currentAccount == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		currentAccount.setFirstName(account.getFirstName());
		currentAccount.setLastName(account.getLastName());
		currentAccount.setDateOfBirth(account.getDateOfBirth());
		currentAccount.setEmail(account.getEmail());
		
		accountDAO.editAccount(currentAccount);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
