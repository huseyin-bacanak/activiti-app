package demo.service;


import demo.dto.AdjustVacationRequestDetails;
import org.activiti.rest.common.api.DataResponse;

import java.util.Date;

public interface TaskServiceHandler {
  DataResponse getPool();

  DataResponse getPool(long pageIndex);

  void claim(int taskId, String username);

  DataResponse getTasksFor(String assignee);

  void approveVacationRequest(int taskId);

  void rejectVacationRequest(int taskId, String managerMotivation);

  void adjustVacationRequest(int taskId, Date startDate, Integer numberOfDays, String motivation);

  void adjustVacationRequest(AdjustVacationRequestDetails vacationRequestDetails);

  void cancelVacationRequest(int taskId);
}
