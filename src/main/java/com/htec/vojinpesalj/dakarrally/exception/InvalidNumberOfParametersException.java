package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class InvalidNumberOfParametersException extends RuntimeException {

    public InvalidNumberOfParametersException() {
        super(ErrorMessages.INVALID_NUMBER_OF_PARAMETERS);
    }
}
