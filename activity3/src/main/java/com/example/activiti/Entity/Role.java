package com.example.activiti.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by tangminyan on 2018/12/12.
 */
@Entity
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
