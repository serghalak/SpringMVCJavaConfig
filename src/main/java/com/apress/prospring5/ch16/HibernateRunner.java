package com.apress.prospring5.ch16;


import com.apress.prospring5.ch16.model.Role;
import com.apress.prospring5.ch16.model.User;
import com.apress.prospring5.ch16.model.converter.Birthday;
import com.apress.prospring5.ch16.model.converter.BirthdayConverter;
import com.apress.prospring5.ch16.util.HibernateUtil;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) throws SQLException {
        final Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/hb_student_tracker",
                "hb_student_tracker", "hb_student_tracker");
        System.out.println("connection: " + connection);

        User user = User.builder()
                .username("ivan09@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                //.birthDate(LocalDate.of(2000, 1, 19))
                .birthday(new Birthday(LocalDate.of(2000,1,19)))
                .info("{\"name\": \"Ivan\",\"id\": \"26\" }")
                //.age(20)
                .role(Role.ADMIN)
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {

            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }

            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                user.setFirstname("Sveta");
                //session2.refresh(user);
                session2.merge(user);
                session2.getTransaction().commit();
            }
        }




    }
}
