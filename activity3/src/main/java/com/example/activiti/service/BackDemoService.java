package com.example.activiti.service;

/**
 * Created by tangminyan on 2018/12/13.
 */
public interface BackDemoService {
    void startFlow(Long personId, Long approveId);

    void approve(String taskId, boolean decision);
}
