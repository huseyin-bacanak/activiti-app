package demo.service;

import demo.dto.BPMPost;
import demo.dto.VacationRequestDetails;
import demo.rest.VacationProcessInstance;

public interface BPMService {
  VacationProcessInstance initiateVacationRequestProcess(BPMPost post);
}
