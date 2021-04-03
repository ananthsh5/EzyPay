package com.app.ezypay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.ezypay.controller.SubscriptionController;
import com.app.ezypay.domain.dto.SubscriptionDto;
import com.app.ezypay.domain.dto.SubscriptionInfo;
import com.app.ezypay.domain.dto.SubscriptionType;
import com.app.ezypay.service.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ananth Shanmugam
 */

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(SubscriptionController.class)
@AutoConfigureMockMvc
public class SubscriptionControllerTest extends AbstractTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SubscriptionService subscriptionService;

	private SubscriptionDto getMonthlySubscriptionDto() {

		SubscriptionDto monthlySubscriptionDto = new SubscriptionDto();
		monthlySubscriptionDto.setAmount(new BigDecimal("20.00"));
		monthlySubscriptionDto.setDay("10");
		monthlySubscriptionDto.setSubscriptionType(SubscriptionType.MONTHLY);
		LocalDate startDate = LocalDate.of(2021, 03, 10);
		monthlySubscriptionDto.setStartDate(startDate);
		LocalDate endDate = LocalDate.of(2021, 05, 31);
		monthlySubscriptionDto.setEndDate(endDate);
		return monthlySubscriptionDto;
	}

	private List<String> getInvoiceDates() {

		List<String> invoiceDates = new ArrayList<String>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date1 = LocalDate.of(2021, 03, 10);
		LocalDate date2 = LocalDate.of(2021, 04, 10);
		LocalDate date3 = LocalDate.of(2021, 05, 10);
		invoiceDates.add(formatter.format(date1));
		invoiceDates.add(formatter.format(date2));
		invoiceDates.add(formatter.format(date3));
		return invoiceDates;
	}

	@Test
	public void doPostMonthlySubscriptionControllerTest() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		byte[] requestJson = objectMapper.writeValueAsBytes(getMonthlySubscriptionDto());

		SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
		subscriptionInfo.setAmount(new BigDecimal("20.00"));
		subscriptionInfo.setSubscriptionType(SubscriptionType.MONTHLY);
		subscriptionInfo.setInvoiceDates(getInvoiceDates());
		Mockito.when(subscriptionService.getCustomerSubscriptionInvoices(getMonthlySubscriptionDto()))
				.thenReturn(subscriptionInfo);

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/customer/subscribe").content(requestJson)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		SubscriptionInfo subscriptionInfoResponse = super.mapFromJson(result.getResponse().getContentAsString(),
				SubscriptionInfo.class);

		assertEquals(new BigDecimal("20.00"), subscriptionInfoResponse.getAmount());
		assertEquals(3, subscriptionInfoResponse.getInvoiceDates().size());

	}

}
