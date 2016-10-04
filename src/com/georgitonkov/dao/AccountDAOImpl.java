package com.georgitonkov.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.georgitonkov.model.Account;

@Transactional
@Repository("accountDAO")
public class AccountDAOImpl implements AccountDAO {
	
	private static final String SELECT_ALL_ACCOUNTS_QUERY = "SELECT acc FROM Account acc ORDER BY acc.firstName ASC";
	private static final String SELECT_DELETE_ACCOUNTS_QUERY = "SELECT acc FROM Account acc WHERE acc.id LIKE :id";
	private static final String SELECT_ACCOUNT_QUERY = "SELECT acc FROM Account acc WHERE acc.id LIKE :id";

	@PersistenceContext
    EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> getAllAccounts() {
		return entityManager.createQuery(SELECT_ALL_ACCOUNTS_QUERY).getResultList();
	}

	@Override
	public void createAccount(Account account) {
		entityManager.persist(account);
	}

	@Override
	public void deleteAccount(long id) {
		Account account = (Account) entityManager.createQuery(
				SELECT_DELETE_ACCOUNTS_QUERY).setParameter("id", id).getSingleResult();
		entityManager.remove(account);
	}

	@Override
	public Account getAccount(long id) {
		try {
			Account account = (Account) entityManager.createQuery(
					SELECT_ACCOUNT_QUERY).setParameter("id", id).getSingleResult();
			return account;
		} catch (NoResultException ex) {
			return null;
		}
	}
	
	@Override
	public void editAccount(Account account) {
		entityManager.merge(account);
	}
	
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	
}
