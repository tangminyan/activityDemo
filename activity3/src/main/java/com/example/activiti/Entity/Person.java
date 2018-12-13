package com.example.activiti.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Created by tangminyan on 2018/12/11.
 */
@Entity
@Setter
@Getter
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String mobile;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
