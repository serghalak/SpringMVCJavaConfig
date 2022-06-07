package com.apress.prospring5.ch16.model.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Birthday {

    private LocalDate birthDate;

    public long getAge() {
        return ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }

}
