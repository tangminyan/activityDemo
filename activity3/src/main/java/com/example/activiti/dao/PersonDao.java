package com.example.activiti.dao;

import com.example.activiti.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by tangminyan on 2018/12/13.
 */
public interface PersonDao extends JpaRepository<Person, Long> {
}
