package demo.service;


import org.activiti.rest.common.api.DataResponse;

public interface HistoryServiceHandler {
  DataResponse getFinishedProcesses();
}
