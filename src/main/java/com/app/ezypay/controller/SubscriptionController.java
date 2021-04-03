package com.app.ezypay.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.ezypay.controller.validator.SubscriptionValidator;
import com.app.ezypay.domain.dto.SubscriptionDto;
import com.app.ezypay.domain.dto.SubscriptionInfo;
import com.app.ezypay.service.SubscriptionService;

/**
 * @author Ananth Shanmugam
 */
@Controller
public class SubscriptionController {

	private final static Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private SubscriptionValidator subscriptionValidator;


	@PostMapping("/customer/subscribe")
	@ResponseBody /* Return the response for customer subscription */
	public ResponseEntity<SubscriptionInfo> subsriberPublisher2(@Valid @RequestBody SubscriptionDto subscriptionDto,
			BindingResult bindingresult) {
		
		subscriptionValidator.validate(subscriptionDto, bindingresult);

		if (bindingresult.hasErrors()) {
			LOGGER.error("subscription json data is incorrect");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

		} else {
			return ResponseEntity.ok(subscriptionService.getCustomerSubscriptionInvoices(subscriptionDto));
		}
	}

}
