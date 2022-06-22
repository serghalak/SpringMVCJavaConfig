package com.apress.prospring5.ch16.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Profile {

//    @Id
//    @Column(name = "user_id")
//    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String language;

    @OneToOne
    @JoinColumn(name = "user_id")
    //@PrimaryKeyJoinColumn
    private User user;

    public void setUser(User user) {
        user.setProfile(this);
        this.user = user;
        //this.id = user.getId();
    }
}
