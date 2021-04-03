package com.app.ezypay.domain.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

/**
 * @author Ananth Shanmugam
 */
@Data
public class SubscriptionInfo {
	 private BigDecimal amount;
	 private SubscriptionType subscriptionType;
	 private List<String> invoiceDates;
}
