package demo.service;

import demo.dto.BPMPost;
import demo.dto.VacationRequestDetails;

public interface BPMService {
  void initiateVacationRequestProcess(BPMPost post);
}
