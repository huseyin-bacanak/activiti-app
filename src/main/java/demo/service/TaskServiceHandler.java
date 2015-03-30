package demo.service;


import org.activiti.rest.common.api.DataResponse;

public interface TaskServiceHandler {
  DataResponse getPool();

  void claim(int taskId, String username);

  DataResponse getTasksFor(String assignee);

  void approveVacationRequest(int taskId);

  void rejectVacationRequest(int taskId);
}
