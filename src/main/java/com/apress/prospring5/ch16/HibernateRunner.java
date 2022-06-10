package com.apress.prospring5.ch16;


import com.apress.prospring5.ch16.model.PersonalInfo;
import com.apress.prospring5.ch16.model.Role;
import com.apress.prospring5.ch16.model.User;
import com.apress.prospring5.ch16.model.converter.Birthday;
import com.apress.prospring5.ch16.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {

    private static final Logger log =  LoggerFactory.getLogger(HibernateRunner.class);
    //private static final Logger log =  Logger.getLogger(HibernateRunner.class);

    public static void main(String[] args) throws SQLException {
        final Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hb_student_tracker",
                "hb_student_tracker", "hb_student_tracker");
        System.out.println("connection: " + connection);

        User user = User.builder()
                .username("ivan10@gmail.com")
//                .firstname("Ivan")
//                .lastname("Ivanov")
//                //.birthDate(LocalDate.of(2000, 1, 19))
//                .birthday(new Birthday(LocalDate.of(2000,1,19)))
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Ivanov")
                        .birthday(new Birthday(LocalDate.of(2000,1,19)))
                        .build())
                .info("{\"name\": \"Ivan\",\"id\": \"26\" }")
                //.age(20)
                .role(Role.ADMIN)
                .build();
        log.info("User entity is created: {}", user);
        log.info("logger>>>>>>>>>>>>>>>>>>>>>>>>Logger");
        System.out.println("sout>>>>>>>>>>>>>>>>Logger");
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                //user.setFirstname("Sveta");
                user.getPersonalInfo().setFirstname("Peter");
                //session2.refresh(user);
                session2.merge(user);
                session2.getTransaction().commit();
            }
        }




    }
}
