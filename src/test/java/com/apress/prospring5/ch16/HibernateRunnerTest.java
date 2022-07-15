package com.apress.prospring5.ch16;

import com.apress.prospring5.ch16.model.*;
import com.apress.prospring5.ch16.model.converter.Birthday;
import com.apress.prospring5.ch16.util.HibernateUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


class HibernateRunnerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void main() {
    }

    @Test
    void checkH2() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            Company company = Company.builder()
                    .name("Google")
                    .build();
            session.save(company);
            session.flush();
            final Company company1 = session.get(Company.class, 1L);
            System.out.println(">>>>>>>"+company1);
            session.getTransaction().commit();


        }
    }

    @Test
    void lacaleInfo() {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            final Company company = session.get(Company.class, 7L);
//            company.getLacales().add(LocalInfo.of("ua", "Опис на українській мові"));
//            company.getLacales().add(LocalInfo.of("en", "Description in English"));
            company.getUsers().forEach(System.out::println);
            session.getTransaction().commit();
        }

    }

    @Test
    void checkManyToMany_Separate() {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = session.get(User.class, 4L);
            Chat chat = session.get(Chat.class, 1L);


            UserChat userChat = UserChat.builder()
                    .createdAt(Instant.now())
                    .createdBy(user.getUsername())
                    .build();
            userChat.setChat(chat);
            userChat.setUser(user);

            session.save(userChat);

            session.getTransaction().commit();
        }
    }

    @Test
    void checkManyToMany() {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            User user = session.get(User.class, 4L);
            //user.getChats().clear();
//            Chat chat = Chat.builder()
//                    .name("misha")
//                    .build();
//
//            user.addChat(chat);
            //chat.addUser(user);
            //session.save(chat);
            session.getTransaction().commit();
        }
    }

    @Test
    void checkOneToOne() {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

//            final User user = session.get(User.class, 4L);
//            System.out.println(user);
//            Company company = Company.builder()
//                    .name("HP")
//                    .build();
            final Company company = session.get(Company.class, 7L);
            User user = User.builder()
                    .username("test5@gmail.com")
                    .company(company)
                    .build();

            company.addUser(user);
            //session.save(company);
            Profile profile = Profile.builder()
                    .street("Racoon")
                    .language("ua")
                    .build();

            session.save(user);
            profile.setUser(user);
            //session.save(profile);

            session.getTransaction().commit();
        }
    }


    @Test
    void checkOrphanRemoval() {

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            Company company = session.get(Company.class, 4L);
            company.getUsers().removeIf(user -> user.getId().equals(2L));

            session.getTransaction().commit();
        }
    }

    @Test
    void checkLazyInitialization() {

        Company company = null;

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            company = session.get(Company.class, 4L);
            //session.delete(company);
            session.getTransaction().commit();
        }

        final List<User> users = company.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }


    @Test
    void deleteCompany() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();

        session.beginTransaction();
        final Company company = session.get(Company.class, 4L);
        //session.delete(company);
        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();

        session.beginTransaction();
        final Company facebook = Company.builder()
                .name("Facebook")
                .build();
        final User build = User.builder()
                .username("sveta@gmail.com")
                .build();
        facebook.addUser(build);
        session.save(facebook);
        session.getTransaction().commit();
    }

    @Test
    void oneToMany() {
        @Cleanup final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup final Session session = sessionFactory.openSession();

        session.beginTransaction();
        final Company company = session.get(Company.class, 3L);
        assertEquals(3L, company.getId());
        assertEquals("Google", company.getName());

        session.getTransaction().commit();
    }

    @Test
    void checkGetReflectionApi() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.getString("user_name");
        resultSet.getString("first_name");
        resultSet.getString("last_name");

        Class<User> clazz = User.class;
        Constructor<User> constructor = clazz.getConstructor();
        User user = constructor.newInstance();
        Field usernameField = clazz.getDeclaredField("user_name");
        usernameField.setAccessible(true);
        usernameField.set(user, resultSet.getString("user_name"));

    }

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("ivan@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Ivanov")
                        .birthday(new Birthday(LocalDate.of(2000, 1, 19)))
                        .build())
//                .firstname("Ivan")
//                .lastname("Ivanov")
//                .birthday(new Birthday(LocalDate.of(2000, 1, 19)))
                //.age(20)
                .role(Role.ADMIN)
                .build();

        String sql = "insert into %s(%s) values (%s)";

        String tableName = Optional.ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> /*tableAnnotation.schema() + "." +*/ tableAnnotation.name())
                .orElse(user.getClass().getName());
        String columnsName = Arrays.stream(user.getClass().getDeclaredFields())
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(user.getClass().getDeclaredFields())
                .map(field -> "?")
                .collect(Collectors.joining(", "));
        System.out.println(String.format(sql, tableName, columnsName, columnValues));

        Connection connection = null;
        PreparedStatement preparedStatement = connection.prepareStatement(
                String.format(sql, tableName, columnsName, columnValues));
        final Field[] declaredFields = user.getClass().getDeclaredFields();
        int index = 0;
        for (Field f : declaredFields) {
            index++;
            f.setAccessible(true);
            preparedStatement.setObject(index, f.get(user));
        }
    }
}