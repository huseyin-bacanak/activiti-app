package demo.service;

import demo.rest.ProcessList;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;

public interface ProcessServiceHandler {
  ProcessDefinitionResponse initiateVacationRequestProcess(ProcessInstanceCreateRequest post);
  ProcessList getRunningProcessInstances();
}
