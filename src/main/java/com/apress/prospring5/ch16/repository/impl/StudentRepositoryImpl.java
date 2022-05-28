package com.apress.prospring5.ch16.repository.impl;

import com.apress.prospring5.ch16.model.Student;
import com.apress.prospring5.ch16.repository.StudentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Override
    public Student getStudentById(Long id) {
        System.out.println("Entity manager: " + entityManager.find(Student.class, 101L));
        System.out.println("Entity manager factory: " + entityManagerFactory);
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
