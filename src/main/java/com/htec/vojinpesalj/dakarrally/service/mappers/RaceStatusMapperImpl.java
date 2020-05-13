package com.htec.vojinpesalj.dakarrally.service.mappers;

import com.htec.vojinpesalj.dakarrally.repository.Constants.ErrorMessages;
import com.htec.vojinpesalj.dakarrally.repository.domain.RaceStatus;
import com.htec.vojinpesalj.dakarrally.service.dto.RaceStatusDto;
import org.springframework.stereotype.Service;

@Service
public class RaceStatusMapperImpl implements RaceStatusMapper {

    @Override
    public RaceStatus toEntity(RaceStatusDto raceStatus) {
        switch (raceStatus) {
            case PENDING:
                return RaceStatus.PENDING;
            case RUNNING:
                return RaceStatus.RUNNING;
            case FINISHED:
                return RaceStatus.FINISHED;
            default:
                throw new UnsupportedOperationException(ErrorMessages.RACE_STATUS_NOT_SUPPORTED);
        }
    }

    @Override
    public RaceStatusDto toDto(RaceStatus raceStatus) {
        switch (raceStatus) {
            case PENDING:
                return RaceStatusDto.PENDING;
            case RUNNING:
                return RaceStatusDto.RUNNING;
            case FINISHED:
                return RaceStatusDto.FINISHED;
            default:
                throw new UnsupportedOperationException(ErrorMessages.RACE_STATUS_NOT_SUPPORTED);
        }
    }
}
