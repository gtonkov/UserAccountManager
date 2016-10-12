package com.georgitonkov.useraccountmanager.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.georgitonkov.useraccountmanager.dto.AccountDTO;

@Component
public class AccountDTOValidator implements Validator {
	
	private static final String VALID_EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String VALID_NAME_REGEX = "[a-zA-Z]+";
	private static final String EARLIEST_VALID_DATE = "01-01-1880";
	private static final String EARLIEST_VALID_DATE_REGEX = "dd-MM-yyyy";
	
	@Override
	public boolean supports(Class<?> aClass) {
		return AccountDTO.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AccountDTO account = (AccountDTO) target;
		validateEmptyFields(account, errors);
		validateName(account.getFirstName(), errors, "firstName");
		validateName(account.getLastName(), errors, "lastName");
		validateBirthDate(account.getDateOfBirth(), errors);
		validateEmail(account.getEmail(), errors);
	}
	
	private void validateEmptyFields(AccountDTO account, Errors errors) {
		if (account.getFirstName() == null || account.getFirstName().equals("")) {
			errors.rejectValue("firstName", "no.firstName");
		}
		if (account.getLastName() == null || account.getLastName().equals("")) {
			errors.rejectValue("lastName", "no.lastName");
		}
		if (account.getDateOfBirth() == null || account.getDateOfBirth().equals("")) {
			errors.rejectValue("dateOfBirth", "no.dateOfBirth");
		}
		if (account.getEmail() == null || account.getEmail().equals("")) {
			errors.rejectValue("email", "no.email");
		}
	}
	
	private void validateName(String name, Errors errors, String type) {
		if((name.length() < 3) || !(name.matches(VALID_NAME_REGEX))) {
			errors.rejectValue(type, "invalid." + type);
		}
	}
	
	private void validateBirthDate(Date birthDate, Errors errors) {
		Date earliestValidDate;
		earliestValidDate = parseDate(EARLIEST_VALID_DATE);
		Date today = new Date();
		if(birthDate.before(earliestValidDate) || birthDate.after(today)) {
			errors.rejectValue("dateOfBirth", "invalid.dateOfBirth");
		}
	}
	
	private void validateEmail(String email, Errors errors) {
		if (!email.matches(VALID_EMAIL_REGEX)) {
			errors.rejectValue("email", "invalid.email");
		}
	}
	
	private static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat(EARLIEST_VALID_DATE_REGEX).parse(date);
	     } catch (ParseException e) {
	    	 // log exception
	         return null;
	     }
	  }
	
}
