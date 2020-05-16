package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entity, Long id) {
        super(String.format(ErrorMessages.ENTITY_WITH_ID_NOT_FOUND, entity, id));
    }
}
