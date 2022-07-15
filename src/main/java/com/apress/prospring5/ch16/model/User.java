package com.apress.prospring5.ch16.model;

import com.apress.prospring5.ch16.model.converter.Birthday;
import com.apress.prospring5.ch16.model.converter.BirthdayConverter;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString(exclude = {"company", "profile", "userChats"})
@EqualsAndHashCode(exclude = {"company", "profile", "userChats"})
@Table(name = "users")
@TypeDef(name = "dmdev", typeClass = JsonStringType.class)
public class User implements BaseEntity<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY
            /*, cascade = {CascadeType.DETACH}*/)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Profile profile;

//    @Builder.Default
//    @ManyToMany
//    @JoinTable(name = "user_chat",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"))
//    private Set<Chat> chats = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private Set<UserChat>userChats = new HashSet<>();

//    public void addChat(Chat chat) {
//        chats.add(chat);
//        chat.getUsers().add(this);
//    }

}
