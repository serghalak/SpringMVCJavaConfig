package com.apress.prospring5.ch16.model;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company")
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JoinColumn(name = "company_id")
    @OrderBy( "username DESC, personalInfo.lastname ASC")
    private List<User> users = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "company_locale", joinColumns = @JoinColumn(name = "company_id"))
    @AttributeOverrides({//використовуємо в випадку якщо назви в колекція і в БД не співпадають
            //в нашому випадкуспівпадають
            @AttributeOverride(name = "lang", column = @Column(name = "lang")),
            @AttributeOverride(name = "description", column = @Column(name = "description"))
    })
    private List<LocalInfo>lacales = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setCompany(this);
    }
}
