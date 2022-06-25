package com.apress.prospring5.ch16.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"userChats"})
@ToString(exclude = {"userChats"})
@Builder
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

//    @Builder.Default
//    @ManyToMany(mappedBy = "chats")
//    private Set<User> users = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "chat")
    private Set<UserChat> userChats = new HashSet<>();

//    public void addUser(User user) {
//        users.add(user);
//        user.getChats().add(this);
//    }
}
