package com.app.ezypay.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.ezypay.domain.dto.SubscriptionDto;
import com.app.ezypay.domain.dto.SubscriptionInfo;
import com.app.ezypay.domain.dto.SubscriptionType;
import com.app.ezypay.service.SubscriptionService;

/**
 * @author Ananth Shanmugam
 */
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

	public SubscriptionInfo getCustomerSubscriptionInvoices(SubscriptionDto subscriptionDto) {

		SubscriptionInfo subscpInfo = new SubscriptionInfo();

		LocalDate first = subscriptionDto.getStartDate();
		LocalDate last = subscriptionDto.getEndDate();

		List<String> invoiceDates = new ArrayList<String>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		
		if (subscriptionDto.getSubscriptionType().equals(SubscriptionType.WEEKLY)) {
			DayOfWeek day = DayOfWeek.valueOf(subscriptionDto.getDay());
			first = first.with(TemporalAdjusters.next(day));
			for (LocalDate date = first; !date.isAfter(last); date = date.with(TemporalAdjusters.next(day))) {
				invoiceDates.add(formatter.format(date));
			}
		}else if (subscriptionDto.getSubscriptionType().equals(SubscriptionType.MONTHLY)) {
			for (LocalDate date = first; !date.isAfter(last); date = date.plusMonths(1)) {
				invoiceDates.add(formatter.format(date));
			}
			
		}else if (subscriptionDto.getSubscriptionType().equals(SubscriptionType.DAILY)) {
			for (LocalDate date = first; !date.isAfter(last); date = date.plusDays(1)) {
				invoiceDates.add(formatter.format(date));
			}
		} 
		subscpInfo.setAmount(subscriptionDto.getAmount());
		subscpInfo.setSubscriptionType(subscriptionDto.getSubscriptionType());
		subscpInfo.setInvoiceDates(invoiceDates);
		
		return subscpInfo;
	}
}
