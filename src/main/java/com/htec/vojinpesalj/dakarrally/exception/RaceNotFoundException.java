package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class RaceNotFoundException extends RuntimeException {

    public RaceNotFoundException(Long id) {
        super(String.format(ErrorMessages.ENTITY_WITH_ID_NOT_FOUND, "Race", id));
    }
}
