package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(String name) {
        super(String.format(ErrorMessages.ROLE_NOT_FOUND, name));
    }
}
