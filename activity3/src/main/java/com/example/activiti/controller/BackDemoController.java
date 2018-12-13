package com.example.activiti.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.activiti.Entity.TaskOrder;
import com.example.activiti.service.BackDemoService;
import com.example.activiti.util.ControllerResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by tangminyan on 2018/12/13.
 */
@RestController
@RequestMapping(value = "/workflow/back")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class BackDemoController {

    @Autowired
    private BackDemoService backDemoService;

    @RequestMapping(value = "/begin", method = RequestMethod.POST)
    public JSONObject getStart(@RequestParam Long personId, @RequestParam Long approveId, @RequestParam String taskName) {
        return ControllerResponse.createSuccessResponse(backDemoService.startFlow(personId, approveId, taskName));
    }

    @RequestMapping(value = "/decision", method = RequestMethod.POST)
    public JSONObject approve(@RequestParam Long taskOrderId, @RequestParam boolean decision) {
        try {
            backDemoService.approve(taskOrderId, decision);
            return ControllerResponse.createSuccessResponse("ok");
        } catch (Exception e) {
            return ControllerResponse.createFailureResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/getTaskList", method = RequestMethod.POST)
    public JSONObject getTaskList(@RequestParam Long approveId) {
        List<JSONObject> taskList = backDemoService.getTaskListByApprove(approveId);
        return ControllerResponse.createSuccessResponse(taskList);
    }
}
