package com.example.activiti.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by tangminyan on 2018/12/12.
 */
@Getter
@AllArgsConstructor
public enum TaskProcessEnum {
    CMCHECK(0, "等待审批"),
    ITMCHECK(1, "审批中"),
    FINISHED(2, "结束");

    private int num;

    private String value;

    public static TaskProcessEnum getEnum(int n) {
        switch (n) {
            case 0: return CMCHECK;
            case 1: return ITMCHECK;
            case 2: return FINISHED;
            default:return null;
        }
    }

}
