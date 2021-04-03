package com.app.ezypay.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ananth Shanmugam
 */

@Data
@NoArgsConstructor
public class SubscriptionDto implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal amount;
	 
	 private SubscriptionType subscriptionType;

	 private String day;
	 
	 @JsonSerialize(using = LocalDateSerializer.class)
	 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	 @JsonFormat(pattern="yyyy-MM-dd")
     private LocalDate startDate;
	 
	 @JsonSerialize(using = LocalDateSerializer.class)
	 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	 @JsonFormat(pattern="yyyy-MM-dd")
     private LocalDate endDate;

}
