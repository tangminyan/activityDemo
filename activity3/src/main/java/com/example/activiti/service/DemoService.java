package com.example.activiti.service;

/**
 * Created by tangminyan on 2018/12/11.
 */
public interface DemoService {

    void firstDemo();

    void getStart(Long personId, Long approvePersonId);

    void getTasks(Long personId);

    String approve(String taskId, Boolean decision);
}
