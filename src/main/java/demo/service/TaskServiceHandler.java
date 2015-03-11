package demo.service;


import demo.rest.Task;
import demo.rest.TaskList;

public interface TaskServiceHandler {
  TaskList getPool();
  void claim(Task task, String username);
  TaskList getTasksFor(String assignee);
  void approveVacationRequest(String taskId);
}
