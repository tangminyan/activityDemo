package com.example.activiti.service.impl;

import com.example.activiti.service.BackDemoService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangminyan on 2018/12/13.
 */
@Service
public class BackDemoServiceImpl implements BackDemoService {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Override
    public void startFlow(Long personId, Long approveId) {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("templates/demo2.bpmn").deploy();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personId", personId);
        map.put("approvePersonId", approveId);
        runtimeService.startProcessInstanceById(definition.getId(), map);
    }

    @Override
    public void approve(String taskId, boolean decision) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("decision", decision);
        taskService.complete(taskId, map);
    }
}
