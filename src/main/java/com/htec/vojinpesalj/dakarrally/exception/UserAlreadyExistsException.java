package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String username) {
        super(String.format(ErrorMessages.USER_ALREADY_EXISTS, username));
    }
}
