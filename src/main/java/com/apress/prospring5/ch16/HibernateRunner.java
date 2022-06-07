package com.apress.prospring5.ch16;


import com.apress.prospring5.ch16.model.Role;
import com.apress.prospring5.ch16.model.User;
import com.apress.prospring5.ch16.model.converter.Birthday;
import com.apress.prospring5.ch16.model.converter.BirthdayConverter;
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

        final Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAttributeConverter(new BirthdayConverter(), true);
        configuration.registerTypeOverride(new JsonStringType());
        configuration.configure("hibernate.cfg.xml");
        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = User.builder()
                    .username("ivan07@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    //.birthDate(LocalDate.of(2000, 1, 19))
                    .birthday(new Birthday(LocalDate.of(2000,1,19)))
                    .info("{\"name\": \"Ivan\",\"id\": \"26\" }")
                    //.age(20)
                    .role(Role.ADMIN)
                    .build();
            session.save(user);
            session.getTransaction().commit();
        }
    }
}
