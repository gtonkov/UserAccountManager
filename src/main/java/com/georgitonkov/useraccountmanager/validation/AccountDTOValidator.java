package com.georgitonkov.useraccountmanager.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.stereotype.Component;

import com.georgitonkov.useraccountmanager.dto.AccountDTO;

@Component
public class AccountDTOValidator {
	
	private static final String VALID_EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String VALID_NAME_REGEX = "[a-zA-Z]+";
	private static final String EARLIEST_VALID_DATE = "01-01-1880";
	private static final String EARLIEST_VALID_DATE_REGEX = "dd-MM-yyyy";
	
	private List<String> violations;

	public void validate(AccountDTO account) {
		violations = new ArrayList<String>();
		validateEmptyFields(account);
		validateName(account.getFirstName(), "firstName");
		validateName(account.getLastName(), "lastName");
		validateBirthDate(account.getDateOfBirth());
		validateEmail(account.getEmail());
	}
	
	private void validateEmptyFields(AccountDTO account) {
		if (account.getFirstName() == null || account.getFirstName().equals("")) {			
			add("no.firstName");
		}
		if (account.getLastName() == null || account.getLastName().equals("")) {
			add("no.lastName");
		}
		if (account.getDateOfBirth() == null || account.getDateOfBirth().equals("")) {
			add("no.dateOfBirth");
		}
		if (account.getEmail() == null || account.getEmail().equals("")) {
			add("no.email");
		}
	}
	
	private void validateName(String name, String type) {
		if((name.length() < 3) || !(name.matches(VALID_NAME_REGEX))) {
			add("invalid." + type);
		}
	}
	
	private void validateBirthDate(Date birthDate) {
		Date earliestValidDate;
		earliestValidDate = parseDate(EARLIEST_VALID_DATE);
		Date today = new Date();
		if(birthDate.before(earliestValidDate) || birthDate.after(today)) {
			add("invalid.dateOfBirth");
		}
	}
	
	private void validateEmail(String email) {
		if (!email.matches(VALID_EMAIL_REGEX)) {
			add("invalid.email");
		}
	}
	
	private static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat(EARLIEST_VALID_DATE_REGEX).parse(date);
	     } catch (ParseException e) {
	    	 e.printStackTrace();
	     }
	     return null;
	  }
	
	public boolean hasErrors() {
		return !violations.isEmpty();
	}
	
	public void add(String error) {
		violations.add(error);
		throw new ValidationException(error);
	}
	
}
