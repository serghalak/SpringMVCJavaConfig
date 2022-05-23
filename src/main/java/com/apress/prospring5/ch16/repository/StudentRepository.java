package com.apress.prospring5.ch16.repository;

import com.apress.prospring5.ch16.model.Student;

public interface StudentRepository {

    Student getStudentById(Long id);
}
