package demo.service;


import org.activiti.rest.common.api.DataResponse;

import java.util.Date;

public interface TaskServiceHandler {
  DataResponse getPool();

  void claim(int taskId, String username);

  DataResponse getTasksFor(String assignee);

  void approveVacationRequest(int taskId);

  void rejectVacationRequest(int taskId);

  void adjustVacationRequest(int taskId, Date startDate, Integer numberOfDays, String motivation);

  void cancelVacationRequest(int taskId);
}
