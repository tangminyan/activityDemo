package com.example.activiti.service.impl;

import com.example.activiti.service.DemoService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangminyan on 2018/12/11.
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Override
    public void firstDemo() {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("templates/demo.bpmn").deploy();
        //获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        //启动流程定义，返回流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceById(processDefinition.getId());
        String processId = pi.getId();
        System.out.println("流程创建成功，当前流程实例ID："+processId);

        taskService.createTaskQuery().taskCandidateUser(processId);
        Task task=taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("第一次执行前，任务名称："+task.getName());

        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("task为null，任务一执行完毕："+task);

    }

    @Override
    public void getStart(Long personId, Long approvePersonId) {
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("templates/demo.bpmn").deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId())
                .singleResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personId", personId);
        map.put("approvePersonId", approvePersonId);
        runtimeService.startProcessInstanceById(processDefinition.getId(), map);
    }

    @Override
    public void getTasks(Long personId) {
        ExecutionQuery exe = runtimeService.createExecutionQuery().variableValueEquals("approvePersonId", personId);
        exe.list().forEach(e -> System.out.println(e.getId() + " " + e.getName() + " " + e.getActivityId() + " "  + e.getDescription()) );

    }

    @Override
    public String approve(Long taskId, Boolean decision) {
        if(decision) {
            taskService.complete(taskId+"");
            return "审批通过，任务完成";
        } else {
            backTask();
            return "审批不通过";
        }
    }

    private void backTask() {

    }

}
