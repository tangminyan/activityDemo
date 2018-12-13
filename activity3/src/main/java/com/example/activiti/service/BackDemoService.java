package com.example.activiti.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by tangminyan on 2018/12/13.
 */
public interface BackDemoService {
    JSONObject startFlow(Long personId, Long approveId, String taskName);

    void approve(Long taskId, boolean decision) throws Exception;

    List<JSONObject> getTaskListByApprove(Long approveId);
}
