package com.example.activiti.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.activiti.Entity.Person;
import com.example.activiti.Entity.TaskOrder;
import com.example.activiti.dao.PersonDao;
import com.example.activiti.dao.TaskDao;
import com.example.activiti.enums.TaskProcessEnum;
import com.example.activiti.service.BackDemoService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private PersonDao personDao;

    @Override
    public JSONObject startFlow(Long personId, Long approveId, String taskName) {
        TaskOrder order = taskDao.save(createTaskOrder(personDao.getOne(personId), personDao.getOne(approveId), taskName));
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("templates/demo2.bpmn").deploy();
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("personId", personId);
        map.put("approvePersonId", approveId);
        map.put("taskOrderId", order.getId());
        runtimeService.startProcessInstanceById(definition.getId(), map);
        return generateJsonData(order);
    }

    @Override
    public void approve(Long taskOrderId, boolean decision) throws Exception {
        TaskOrder taskOrder = taskDao.getOne(taskOrderId);
        Execution execution = runtimeService.createExecutionQuery().variableValueEquals("taskOrderId", taskOrderId).singleResult();
        Task task=taskService.createTaskQuery().processInstanceId(execution.getId()).singleResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("decision", decision);
        taskService.complete(task.getId(), map);
        task=taskService.createTaskQuery().processInstanceId(execution.getId()).singleResult();
        if(task == null) {
            taskOrder.setStatus(TaskProcessEnum.FINISHED.getNum());
        } else if(task.getTaskDefinitionKey().equals("userProcess")) {
            taskOrder.setStatus(TaskProcessEnum.CMCHECK.getNum());
        } else if(task.getTaskDefinitionKey().equals("approveProcess")) {
            taskOrder.setStatus(TaskProcessEnum.ITMCHECK.getNum());
        } else {
            throw new Exception("can not be approve");
        }
        taskDao.save(taskOrder);

    }

    @Override
    public List<JSONObject> getTaskListByApprove(Long approveId) {
        List<TaskOrder> taskList = taskDao.findByApprover(approveId);
        List<JSONObject> jsonList = new ArrayList<>();
        taskList.forEach(t -> jsonList.add(generateJsonData(t)));
        return jsonList;
    }

    private TaskOrder createTaskOrder(Person one, Person approver, String taskName) {
        TaskOrder order = new TaskOrder();
        order.setCreator(one);
        order.setApprover(approver);
        order.setTaskName(taskName);
        order.setStatus(TaskProcessEnum.CMCHECK.getNum());
        return order;
    }

    private JSONObject generateJsonData(TaskOrder order) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", order.getTaskName());
        jsonObject.put("creator", order.getCreator().getName());
        jsonObject.put("approver", order.getApprover().getName());
        jsonObject.put("status", TaskProcessEnum.getEnum(order.getStatus()));
        return jsonObject;
    }

}
