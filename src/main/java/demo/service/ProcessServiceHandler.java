package demo.service;

import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;

import java.util.Date;

public interface ProcessServiceHandler {
  ProcessDefinitionResponse initiateVacationRequestProcess(ProcessInstanceCreateRequest post);
  ProcessInstanceResponse getRunningProcessInstances();
  ProcessInstanceResponse initiateVacationRequest(String employeeName, Date startDate, int numberOfDays, String motivation);
  void deleteProcessInstance(int id);
}
