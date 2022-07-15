package com.apress.prospring5.ch16.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

//@Getter
//@Setter
//@MappedSuperclass
public interface BaseEntity<T extends Serializable> {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private T id;\
    void setId(T id);

    T getId();
}
