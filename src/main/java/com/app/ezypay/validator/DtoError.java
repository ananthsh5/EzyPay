package com.app.ezypay.validator;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Ananth Shanmugam
 * Class to hold any dto object error due to validation or any other error 
 */
@Data
@AllArgsConstructor
public class DtoError {
    private String field;
    private String message;
}
