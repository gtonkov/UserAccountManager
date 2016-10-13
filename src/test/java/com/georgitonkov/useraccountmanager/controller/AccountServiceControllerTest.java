package com.georgitonkov.useraccountmanager.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.georgitonkov.useraccountmanager.dto.AccountDTO;
import com.georgitonkov.useraccountmanager.service.AccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountServiceController.class)
@ComponentScan("com.georgitonkov.useraccountmanager.service")
@ComponentScan("com.georgitonkov.useraccountmanager")
@EnableSpringDataWebSupport
public class AccountServiceControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

	@Test
	public void testGetAccount() throws Exception {
		AccountDTO dto = createDTO(1L, "Georgi", "Tonkov", "24-02-1989", "georgi@mail.com");
		given(service.getAccount(1L)).willReturn(dto);
		mockMvc.perform(get("/rest/acc/1")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(content().json("{\"id\": 1,\"firstName\": \"Georgi\",\"lastName\": \"Tonkov\",\"dateOfBirth\": \"24-02-1989\",\"email\": \"georgi@mail.com\"}"));
	}
	
	
	@Test
	public void testGetAllAccounts() throws Exception {
		Pageable pageable = Mockito.mock(Pageable.class);
		Page<AccountDTO> page = service.getAllAccounts(pageable);
		given(service.getAllAccounts(pageable)).willReturn(page);
		mockMvc.perform(get("/rest/acc")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	
	}
	
	@Test
	public void testCreateAccountValid() throws Exception {
		AccountDTO dto = createDTO(1L, "Georgi", "Tonkov", "24-02-1989", "georgi@mail.com");
		
		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(post("/rest/acc").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated());	
	}
	
	@Test(expected=Exception.class)
	public void testCreateAccountInvalidName() throws Exception {
		AccountDTO dto = createDTO(2L, "G", "Tonkov", "24-02-1989", "georgi@mail.com");

		String requestJson = mapAccountToJson(dto);
		
		mockMvc.perform(post("/rest/acc").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	@Test(expected=Exception.class)
	public void testCreateAccountInvalidSurname() throws Exception {
		AccountDTO dto = createDTO(3L, "Georgi", "To", "24-02-1989", "georgi@mail.com");

		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(post("/rest/acc").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	@Test(expected=Exception.class)
	public void testCreateAccountInvalidBirthdate() throws Exception {
		AccountDTO dto = createDTO(4L, "Georgi", "Tonkov", "24-02-2020", "georgi@mail.com");

		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(post("/rest/acc").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	@Test(expected=Exception.class)
	public void testCreateAccountInvalidEmail() throws Exception {
		AccountDTO dto = createDTO(5L, "Georgi", "Tonkov", "24-02-2020", "@mail.");

		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(post("/rest/acc").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	@Test
	public void testDeleteAccount() throws Exception {
		mockMvc.perform(delete("/rest/acc/1")
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNoContent());	
	}
	
	@Test
	public void testEditAccountValid() throws Exception {
		AccountDTO dto = createDTO(1L, "Georgi", "Tonkov", "24-02-1989", "georgi@mail.com");
		
		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(put("/rest/acc/1").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());	
	}
	
	@Test(expected=Exception.class)
	public void testEditAccountInvalidName() throws Exception {
		AccountDTO dto = createDTO(2L, "G", "Tonkov", "24-02-1989", "georgi@mail.com");

		String requestJson = mapAccountToJson(dto);
		
		mockMvc.perform(put("/rest/acc/1").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	@Test(expected=Exception.class)
	public void testEditAccountInvalidSurname() throws Exception {
		AccountDTO dto = createDTO(3L, "Georgi", "To", "24-02-1989", "georgi@mail.com");

		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(put("/rest/acc/1").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	@Test(expected=Exception.class)
	public void testEditAccountInvalidBirthdate() throws Exception {
		AccountDTO dto = createDTO(4L, "Georgi", "Tonkov", "24-02-2020", "georgi@mail.com");

		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(put("/rest/acc/1").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	@Test(expected=Exception.class)
	public void testEditAccountInvalidEmail() throws Exception {
		AccountDTO dto = createDTO(5L, "Georgi", "Tonkov", "24-02-2020", "@mail.");

		String requestJson = mapAccountToJson(dto);

		mockMvc.perform(put("/rest/acc/1").content(requestJson)
				.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotAcceptable());
	}
	
	private AccountDTO createDTO(long id, String firstName, String lastName, String dateOfBirth, String email) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = null;
		try {
			inputDate = dateFormat.parse(dateOfBirth);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		AccountDTO dto = new AccountDTO();
		dto.setId(id);
		dto.setFirstName(firstName);
		dto.setLastName(lastName);
		dto.setDateOfBirth(inputDate);
		dto.setEmail(email);
		return dto;
	}
	
	private String mapAccountToJson(AccountDTO dto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    return ow.writeValueAsString(dto);
	}

}
