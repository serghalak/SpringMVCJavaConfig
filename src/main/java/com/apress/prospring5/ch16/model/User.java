package com.apress.prospring5.ch16.model;

import com.apress.prospring5.ch16.model.converter.Birthday;
import com.apress.prospring5.ch16.model.converter.BirthdayConverter;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@TypeDef(name = "dmdev", typeClass = JsonStringType.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstname", column = @Column(name = "firstname")),
            @AttributeOverride(name = "lastname", column = @Column(name = "lastname")),
            @AttributeOverride(name = "birthday", column = @Column(name = "birth_date"))
    })
    private PersonalInfo personalInfo;

    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;


    //@Type(type = "com.vladmihalcea.hibernate.type.json.JsonStringType")
    @Type(type = "dmdev")
    private String info;

}
