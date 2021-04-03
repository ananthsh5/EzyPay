package com.app.ezypay.service;

import com.app.ezypay.domain.dto.SubscriptionDto;
import com.app.ezypay.domain.dto.SubscriptionInfo;

/**
 * @author Ananth Shanmugam
 */
public interface SubscriptionService {
	SubscriptionInfo getCustomerSubscriptionInvoices(SubscriptionDto subscriptionDto);
}
