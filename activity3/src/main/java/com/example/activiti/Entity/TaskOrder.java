package com.example.activiti.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Created by tangminyan on 2018/12/12.
 */
@Entity
@Setter
@Getter
public class TaskOrder {
    @Id
    @GeneratedValue
    private Long id;

    private String taskName;

    @OneToOne
    @JoinColumn(name = "creator_id")
    private Person creator;

    @OneToOne
    @JoinColumn(name = "approve_id")
    private Person approver;

    private Integer status;
}
