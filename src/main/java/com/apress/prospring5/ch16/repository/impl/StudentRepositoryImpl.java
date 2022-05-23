package com.apress.prospring5.ch16.repository.impl;

import com.apress.prospring5.ch16.model.Student;
import com.apress.prospring5.ch16.repository.StudentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    @Override
    public Student getStudentById(Long id) {
        return getSession().find(Student.class, id);
    }

    private static Session getSession() {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
        return sessionFactory.openSession();
    }
}
