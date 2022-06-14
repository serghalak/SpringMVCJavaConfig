package com.apress.prospring5.ch16;


import com.apress.prospring5.ch16.model.*;
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

        Company company = Company.builder()
                .name("Google")
                .build();
        User user = User.builder()
                .username("ivan@gmail.com")
//                .firstname("Ivan")
//                .lastname("Ivanov")
                //.birthDate(LocalDate.of(2000, 1, 19))
//                .birthday(new Birthday(LocalDate.of(2000,1,19)))
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Ivanov")
                        .birthday(new Birthday(LocalDate.of(2000,1,19)))
                        .build())
                .info("{\"name\": \"Ivan\",\"id\": \"26\" }")
                //.age(20)
                .role(Role.ADMIN)
                .company(company)
                .build();

        log.info("User entity is created: {}", user);
        log.info("logger>>>>>>>>>>>>>>>>>>>>>>>>Logger");
        System.out.println("sout>>>>>>>>>>>>>>>>Logger");
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                //session1.save(company);
                //session1.save(user);
                final User user1 = session1.get(User.class, 1L);
                System.out.println(user1);
                session1.getTransaction().commit();
            }

//            try (Session session2 = sessionFactory.openSession()) {
//                session2.beginTransaction();
//                //user1.setFirstname("Petr");
//                //final User1 user11 = session2.find(User1.class, "ivan@gmail.com");
//                user1.getPersonalInfo().setFirstname("Petr");
//                user1.getPersonalInfo().setLastname("Petrov");
//                user1.getPersonalInfo().setBirthday(new Birthday(LocalDate.of(2000,1,2)));
//                //session2.refresh(user);
//                session2.merge(user1);
//                session2.getTransaction().commit();
//            }

//            try (Session session = sessionFactory.openSession()) {
//
//
//
//                PersonalInfo1 key = PersonalInfo1.builder()
//                        //.birthday(new Birthday(LocalDate.of(2000, 1, 18)))
//                        .firstname("Ivan")
//                        .lastname("Ivanov")
//                        .build();
//
////                System.out.println(">>>>: " +
////                        new Birthday(LocalDate.of(2000, 1, 19)).getBirthDate());
//                User2 userByKey = session.get(User2.class, key);
//                System.out.println(">>>>user1 by embadded key: " + userByKey);
//
//
//            }
        }




    }
}
