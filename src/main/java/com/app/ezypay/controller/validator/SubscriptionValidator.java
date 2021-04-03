package com.app.ezypay.controller.validator;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.ezypay.domain.dto.SubscriptionDto;
import com.app.ezypay.domain.dto.SubscriptionType;

/**
 * @author Ananth Shanmugam Class to validate subscription info
 */
@Component
public class SubscriptionValidator implements Validator {

	private static final String AMOUNT_MORE_THAN_ZERO = "Amount should be more than 0";
	
	private static final String SUBSCRIPTION_TYPE_EXPECTED = "Subscription type expected";
	
	private static final String DAY_STRING_TYPE_EXPECTED_WEEKLY = "Day in String expected for weekly";

	private static final String DAY_INTEGER_TYPE_EXPECTED_MONTHLY = "Days in integer expected for monthly";

	private static final String UNDEFINED_PERIOD = "Period is undefined";

	private static final String PERIOD_IS_MORE_THAN_3_MONTHS= "Period is more than 3 months";

	enum ERROR_CODE{
		AMOUNT001,SUB001,DAY001,DAY002,PERIOD001,PERIOD002
	}
	@Override
	public boolean supports(Class<?> clazz) {
		return SubscriptionDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SubscriptionDto subscriptionDTO = (SubscriptionDto) target;
		if (subscriptionDTO.getAmount() == null || subscriptionDTO.getAmount().equals(new BigDecimal("0.00"))) {
			errors.rejectValue("amount", "AMOUNT001", AMOUNT_MORE_THAN_ZERO);
		}
		if (subscriptionDTO.getSubscriptionType() == null) {

			errors.rejectValue("days", "SUB001", SUBSCRIPTION_TYPE_EXPECTED);

		} else {
			if (subscriptionDTO.getSubscriptionType().equals(SubscriptionType.WEEKLY)) {
				try {
					DayOfWeek.valueOf(subscriptionDTO.getDay());
				} catch (IllegalArgumentException ex) {
					errors.rejectValue("days", "DAY001", DAY_STRING_TYPE_EXPECTED_WEEKLY);
				}

			} else if (subscriptionDTO.getSubscriptionType().equals(SubscriptionType.MONTHLY)) {
				try {
					Integer.parseInt(subscriptionDTO.getDay());
				} catch (NumberFormatException formatExcpt) {
					errors.rejectValue("day", "DAY002", DAY_INTEGER_TYPE_EXPECTED_MONTHLY);
				}

			}
		}
		if (subscriptionDTO.getStartDate() == null || subscriptionDTO.getEndDate() == null) {
			errors.rejectValue("amount", "PERIOD001", UNDEFINED_PERIOD);
		} else {
			LocalDate subscriptionStartDate = subscriptionDTO.getStartDate();
			LocalDate subscriptionEndDate = subscriptionDTO.getEndDate();

			if (subscriptionStartDate.isBefore(subscriptionEndDate)) {
				Period diff = Period.between(subscriptionStartDate, subscriptionEndDate);
				if (diff.getMonths() > 3) {
					errors.rejectValue("amount", "PERIOD002", PERIOD_IS_MORE_THAN_3_MONTHS);
				}
			} else {
				errors.rejectValue("amount", "PERIOD001", UNDEFINED_PERIOD);
			}
		}
	}
}
