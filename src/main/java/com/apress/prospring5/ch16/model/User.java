package com.apress.prospring5.ch16.model;

import com.apress.prospring5.ch16.model.converter.Birthday;
import com.apress.prospring5.ch16.model.converter.BirthdayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String firstname;
    private String lastname;
//    @Column(name = "birth_date")
//    private LocalDate birthDate;

    @Convert(converter = BirthdayConverter.class)
    @Column(name = "birth_date")
    private Birthday birthday;

    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

}
