package com.apress.prospring5.ch16;

import com.apress.prospring5.ch16.model.Role;
import com.apress.prospring5.ch16.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    void checkReflectionApi() throws SQLException, IllegalAccessException {
        User user = User.builder()
                .username("ivan@gmail.com")
                .firstname("Ivan")
                .lastname("Ivanov")
                .birthDate(LocalDate.of(2000, 1, 19))
                .age(20)
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