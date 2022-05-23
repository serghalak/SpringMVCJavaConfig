package com.apress.prospring5.ch16.service.impl;

import com.apress.prospring5.ch16.model.Student;
import com.apress.prospring5.ch16.repository.StudentRepository;
import com.apress.prospring5.ch16.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentById(Long id) {
        final Student studentById = studentRepository.getStudentById(id);
        System.out.println(studentById);
        return studentById;
    }
}
