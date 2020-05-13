package com.htec.vojinpesalj.dakarrally.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RaceResponse {
    private Long id;
    private Integer year;
    private RaceStatusDto status;
}
