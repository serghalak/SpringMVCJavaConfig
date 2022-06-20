package com.apress.prospring5.ch16;

import com.apress.prospring5.ch16.model.Company;
import com.apress.prospring5.ch16.model.PersonalInfo;
import com.apress.prospring5.ch16.model.Role;
import com.apress.prospring5.ch16.model.User;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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
                        .birthday(new Birthday(LocalDate.of(2000,1,19)))
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