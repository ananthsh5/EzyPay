package com.app.ezypay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * @author Ananth Shanmugam
 */

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@WebMvcTest(SubscriptionController.class)
@AutoConfigureMockMvc
public class MonthlySubscriptionTest extends AbstractTest {

	@Autowired
	private MockMvc mvc;

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

	private SubscriptionDto getMonthlySubscriptionBadAmtRequestDto() {

		SubscriptionDto monthlySubscriptionDto = new SubscriptionDto();
		monthlySubscriptionDto.setAmount(null);
		monthlySubscriptionDto.setDay("10");
		monthlySubscriptionDto.setSubscriptionType(SubscriptionType.MONTHLY);
		LocalDate startDate = LocalDate.of(2021, 03, 10);
		monthlySubscriptionDto.setStartDate(startDate);
		LocalDate endDate = LocalDate.of(2021, 05, 31);
		monthlySubscriptionDto.setEndDate(endDate);
		return monthlySubscriptionDto;
	}

	@Test
	public void doPostMonthlySubscription() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		byte[] requestJson = objectMapper.writeValueAsBytes(getMonthlySubscriptionDto());

		MvcResult result = mvc
				.perform(MockMvcRequestBuilders.post("/customer/subscribe").content(requestJson)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		SubscriptionInfo subscriptionInfo = super.mapFromJson(result.getResponse().getContentAsString(),
				SubscriptionInfo.class);
		System.out.println("result.getResponse().getContentAsString() " + result.getResponse().getContentAsString());

		System.out.println("Response content received subscriptionInfo amt is " + subscriptionInfo.getAmount());

		assertNotNull(subscriptionInfo);
		assertNotNull(subscriptionInfo.getInvoiceDates());
		assertTrue(subscriptionInfo.getInvoiceDates().size() > 0);

		assertEquals(new BigDecimal("20.00"), subscriptionInfo.getAmount());

	}

	@Test
	public void doPostMonthlySubscriptionBadAmtRequest() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		byte[] requestJson = objectMapper.writeValueAsBytes(getMonthlySubscriptionBadAmtRequestDto());

		mvc.perform(MockMvcRequestBuilders.post("/customer/subscribe").content(requestJson)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();
	}

}
