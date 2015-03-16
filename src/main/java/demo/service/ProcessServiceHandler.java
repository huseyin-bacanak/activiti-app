package demo.service;

import demo.dto.BPMPost;
import demo.dto.VacationRequestDetails;
import demo.rest.ProcessList;
import demo.rest.VacationProcessInstance;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;

public interface ProcessServiceHandler {
  ProcessDefinitionResponse initiateVacationRequestProcess(BPMPost post);
  ProcessList getRunningProcessInstances();
}
