package demo.service;


import demo.rest.TaskList;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.runtime.task.TaskResponse;

import java.util.Date;

public interface TaskServiceHandler {
  DataResponse getPool();
  void claim(TaskResponse task, String username);
  DataResponse getTasksFor(String assignee);
  void approveVacationRequest(String taskId);

}
