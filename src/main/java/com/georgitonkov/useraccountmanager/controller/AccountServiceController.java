package com.georgitonkov.useraccountmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.georgitonkov.useraccountmanager.dto.AccountDTO;
import com.georgitonkov.useraccountmanager.service.AccountService;
import com.georgitonkov.useraccountmanager.validation.AccountDTOValidator;

@RestController
@RequestMapping("/rest")
public class AccountServiceController {
	
	@Autowired
	private AccountService service;
	
	@Autowired
	private AccountDTOValidator validator;

	@RequestMapping(value="/acc", method=RequestMethod.GET)
    public Page<AccountDTO> getAllAccounts(Pageable pageable, Sort sort) {
		return service.getAllAccounts(pageable);
    }
	
	@RequestMapping(value="/acc/{id}", method=RequestMethod.GET)
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") long id) {
		return new ResponseEntity<AccountDTO>(service.getAccount(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/acc", method=RequestMethod.POST)
	public ResponseEntity<Void> createAccount(@RequestBody AccountDTO account, UriComponentsBuilder builder) {
		validator.validate(account);
		if (validator.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
		service.createAccount(account);		
		UriComponents components = builder.path("/acc/{id}").buildAndExpand(account.getId());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(components.toUri());
		return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/acc/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAccount(@PathVariable("id") long id) {
		service.deleteAccount(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value="/acc/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> editAccount(@RequestBody AccountDTO account, @PathVariable("id") long id) {
		validator.validate(account);
		if (validator.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
		service.editAccount(account, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
