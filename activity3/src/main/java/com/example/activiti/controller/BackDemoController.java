package com.example.activiti.controller;

import com.example.activiti.service.BackDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tangminyan on 2018/12/13.
 */
@RestController
@RequestMapping(value = "/workflow/back")
public class BackDemoController {

    @Autowired
    private BackDemoService backDemoService;

    @RequestMapping(value = "/begin/{personId}/{approveId}")
    public String getStart(@PathVariable Long personId, @PathVariable Long approveId) {
        backDemoService.startFlow(personId, approveId);
        return "back process create ok";
    }

    @RequestMapping(value = "/decision/{taskId}/{decision}")
    public String approve(@PathVariable String taskId, @PathVariable boolean decision) {
        backDemoService.approve(taskId, decision);
        return "approve ok";
    }
}
