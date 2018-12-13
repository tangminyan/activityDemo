package com.example.activiti.controller;

import com.example.activiti.service.DemoService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tangminyan on 2018/12/11.
 */
@RestController
@RequestMapping("/workflow/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/firstDemo")
    public void firstDemo() {
        demoService.firstDemo();
    }

    @RequestMapping("/start/{personId}/{approvePersonId}")
    public String startProcess(@PathVariable Long personId, @PathVariable Long approvePersonId) {
        demoService.getStart(personId, approvePersonId);
        return "create ok";
    }

    @RequestMapping(value = "/gettask/{personId}")
    public String getTask(@PathVariable Long personId) {
        demoService.getTasks(personId);
        return "get tasks ok";
    }

    @RequestMapping(value = "/approve/{taskId}/{decision}")
    public String approveProcess(@PathVariable String taskId, @PathVariable String decision) {
        return demoService.approve(taskId, Boolean.parseBoolean(decision));
    }
}
