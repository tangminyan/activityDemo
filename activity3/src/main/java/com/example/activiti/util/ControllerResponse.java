package com.example.activiti.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by tangminyan on 2018/12/13.
 */
public class ControllerResponse {
    public static JSONObject createSuccessResponse(Object body) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", true);
        jsonObject.put("data", body);
        return jsonObject;
    }

    public static JSONObject createFailureResponse(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("state", false);
        jsonObject.put("data", msg);
        return jsonObject;
    }
}
