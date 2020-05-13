package com.htec.vojinpesalj.dakarrally.repository.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "races")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Race {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull private Integer year;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RaceStatus status;
}
