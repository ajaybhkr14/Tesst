package com.example.demo;
import static org.junit.Assert.assertEquals;

import java.net.http.HttpHeaders;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.Controller.SalesController;
import com.example.demo.Service.Sales;
import com.example.demo.Service.SalesService;


@RunWith(SpringRunner.class)
@WebMvcTest(value = SalesController.class)
public class SalesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SalesService salesService;

	String exampleSalesJson ="{\"total\":250.0,\"units\":2.0,\"item\":\"Desk\"}";

	
	@Test
	public void createStudentCourse() throws Exception {
		Sales sales = new Sales(100, "abc", 1);
				
		// studentService.addCourse to respond back with mockCourse
		Mockito.when(
				salesService.getItemsByItem(Mockito.anyString(),
						Mockito.anyList())).thenReturn(sales);

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/sales/by_name")
				.accept(MediaType.APPLICATION_JSON).content(exampleSalesJson)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		assertEquals("http://localhost/students/Student1/courses/1",
				response.getHeader(org.springframework.http.HttpHeaders.LOCATION));

	}

}
