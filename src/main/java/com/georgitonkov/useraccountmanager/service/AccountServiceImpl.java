package com.georgitonkov.useraccountmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.georgitonkov.useraccountmanager.dto.AccountDTO;
import com.georgitonkov.useraccountmanager.model.Account;
import com.georgitonkov.useraccountmanager.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountRepository repository;

	@Override
	public Page<AccountDTO> getAllAccounts(Pageable pageable) {
		Page<AccountDTO> dtos = repository.findAll(pageable).map(this::convertToDTO);
		return dtos;
	}

	@Override
	public void createAccount(AccountDTO dto) {
		Account account = new Account();
		BeanUtils.copyProperties(dto, account);
		repository.save(account);
	}

	@Override
	public void deleteAccount(long id) {
		Account account = repository.findById(id);
		repository.delete(account);
	}

	@Override
	public AccountDTO getAccount(long id) {
		return convertToDTO(repository.findById(id)); 
	}

	@Override
	public void editAccount(AccountDTO dto, long id) {
		Account currentAccount = new Account();
		BeanUtils.copyProperties(dto, currentAccount);
		currentAccount.setId(id);
		repository.save(currentAccount);
	}

	private List<AccountDTO> convertToDTOs(List<Account> accounts) {
		List<AccountDTO> dtos = new ArrayList<AccountDTO>();
		accounts.forEach(account -> dtos.add(convertToDTO(account)));
		return dtos;
	}
	
	private AccountDTO convertToDTO(Account account) {		
		AccountDTO dto = new AccountDTO();
		BeanUtils.copyProperties(account, dto);
		return dto;
	}

}
