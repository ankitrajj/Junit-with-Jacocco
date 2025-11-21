package in.ankit.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ankit.bindings.User;
import in.ankit.constants.AppConstants;
import in.ankit.rest.RegistrationRestController;
import in.ankit.service.RegistrationService;

@WebMvcTest(value = RegistrationRestController.class)
public class RegistrationRestControllerTest {

	@MockBean
	private RegistrationService regService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void emailCheckTest1() throws Exception {
		when(regService.uniqueEmail("ashok@gmail.com")).thenReturn(true);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/emailcheck/ankitrajj95@gmail.com");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		String resBody = response.getContentAsString();
		assertEquals(AppConstants.UNIQUE, resBody);
	}

	@Test
	public void emailCheckTest2() throws Exception {
		when(regService.uniqueEmail("ankitrajj95@gmail.com")).thenReturn(false);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/emailcheck/ankitrajj95@gmail.com");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		String resBody = response.getContentAsString();
		assertEquals(AppConstants.DUPLICATE, resBody);
	}

	@Test
	public void countriesTest() throws Exception {

		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "India");
		map.put(2, "USA");
		when(regService.getCountries()).thenReturn(map);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/countries");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	@Test
	public void statesTest() throws Exception {

		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "AP");
		map.put(2, "TS");
		when(regService.getStates(1)).thenReturn(map);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/states/1");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	@Test
	public void citiesTest() throws Exception {

		HashMap<Integer, String> map = new HashMap<>();
		map.put(1, "Delhi");
		map.put(2, "Buxar");
		when(regService.getCities(1)).thenReturn(map);

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/cities/1");

		MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(200, status);
	}

	@Test
	public void saveUserTest1() throws Exception {
		User user = new User();
		user.setUserFname("ankit");
		user.setUserLname("raj");
		user.setUserEmail("ankitrajj95@gmail.com");
		// set remaining fields also

		when(regService.registerUser(user)).thenReturn(true);

		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/saveuser")
							.contentType("application/json")
							.content(userJson);

		MvcResult result = mockMvc.perform(request).andReturn();
		String resBody = result.getResponse().getContentAsString();
		assertEquals(AppConstants.SUCCESS, resBody);
	}

	@Test
	public void saveUserTest2() throws Exception {
		User user = new User();
		user.setUserFname("ankit");
		user.setUserLname("raj");
		user.setUserEmail("ankitrajj95@gmail.com");
		// set remaining fields also

		when(regService.registerUser(user)).thenReturn(false);

		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(user);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/saveuser")
				.contentType("application/json")
				.content(userJson);

		MvcResult result = mockMvc.perform(request).andReturn();
		String resBody = result.getResponse().getContentAsString();
		assertEquals(AppConstants.FAIL, resBody);
	}
}
