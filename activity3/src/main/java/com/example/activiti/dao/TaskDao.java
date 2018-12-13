package com.example.activiti.dao;

import com.example.activiti.Entity.TaskOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by tangminyan on 2018/12/13.
 */
public interface TaskDao extends JpaRepository<TaskOrder, Long> {
    /**
     *
     * @param approveId
     * @return
     */
    @Query(value = "SELECT t from TaskOrder t WHERE t.approver.id = ?1")
    List<TaskOrder> findByApprover(Long approveId);
}
