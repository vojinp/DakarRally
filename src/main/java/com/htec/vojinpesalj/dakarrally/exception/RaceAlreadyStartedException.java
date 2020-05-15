package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class RaceAlreadyStartedException extends RuntimeException {

    public RaceAlreadyStartedException(Long id) {
        super(String.format(ErrorMessages.RACE_ALREADY_STARTED, id));
    }
}
