package com.app.ezypay.validator;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ananth Shanmugam
 * Class to hold a list of dto object error due to validation or any other error 
 */
@Data
public class DtoErrors {
    private String errorType;
    private List<DtoError> errorFields = new ArrayList<>();

    public void addErrorField(String path, String message) {
        this.errorFields.add(new DtoError(path, message));
    }
}
