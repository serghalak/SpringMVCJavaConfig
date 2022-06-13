package com.apress.prospring5.ch16.model;

import com.apress.prospring5.ch16.model.converter.Birthday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo1 implements Serializable {

    private static final long serialVersionUID = 4505317170595548893L;

    private String firstname;
    private String lastname;
//    @Column(name = "birth_date")
//    private LocalDate birthDate;

    //@Convert(converter = BirthdayConverter.class)
//    @Column(name = "birth_date")
//    private Birthday birthday;
}
