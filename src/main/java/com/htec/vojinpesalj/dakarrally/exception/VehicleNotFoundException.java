package com.htec.vojinpesalj.dakarrally.exception;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;

public class VehicleNotFoundException extends RuntimeException {

  public VehicleNotFoundException(Long id) {
    super(String.format(ErrorMessages.ENTITY_WITH_ID_NOT_FOUND, "Vehicle", id));
  }
}
