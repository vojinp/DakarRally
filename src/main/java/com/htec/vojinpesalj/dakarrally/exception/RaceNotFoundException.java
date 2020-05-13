package com.htec.vojinpesalj.dakarrally.exception;

public class RaceNotFoundException extends EntityNotFoundException {

    public RaceNotFoundException(Long id) {
        super("Race", id);
    }
}
