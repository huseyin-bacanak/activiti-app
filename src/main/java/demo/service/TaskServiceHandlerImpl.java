package demo.service;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskActionRequest;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskServiceHandlerImpl implements TaskServiceHandler {
  private static final Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerImpl.class);
  private static final String QUERY_URL =
      "http://localhost:9000/activiti/service/runtime/tasks"
          + "?processDefinitionKey=vacationRequest&includeProcessVariables=true";
  private static final String URL = "http://localhost:9000/activiti/service/runtime/tasks";
  private final HttpHost host = new HttpHost("localhost", 9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  @Override
  public DataResponse getPool() {
    DataResponse result =
        restTemplate.getForObject(QUERY_URL + "&candidateUser=kermit", DataResponse.class);
    return result;
  }

  @Override
  public void claim(int taskId, String username) {
    String url = "http://localhost:9000/activiti/service/runtime/tasks/{taskId}";
    TaskActionRequest tar = new TaskActionRequest();
    tar.setAction(TaskActionRequest.ACTION_CLAIM);
    tar.setAssignee(username);
    restTemplate.postForObject(url, tar, String.class, taskId + "");
  }

  @Override
  public DataResponse getTasksFor(String assignee) {
    DataResponse result =
        restTemplate.getForObject(QUERY_URL + "&assignee=" + assignee, DataResponse.class);
    logger.info(result.toString());
    return result;
  }

  @Override
  public void approveVacationRequest(int taskId) {
    RestVariable var = new RestVariable();
    var.setName("vacationApproved");
    var.setValue(Boolean.TRUE);
    List<RestVariable> vars = new ArrayList<>();
    vars.add(var);
    TaskActionRequest tar = new TaskActionRequest();
    tar.setAction(TaskActionRequest.ACTION_COMPLETE);
    tar.setVariables(vars);

    String url = "http://localhost:9000/activiti/service/runtime/tasks/{taskId}";
    restTemplate.postForObject(url, tar, String.class, taskId + "");
  }

  @Override
  public void rejectVacationRequest(int taskId) {
    RestVariable var = new RestVariable();
    var.setName("vacationApproved");
    var.setValue(Boolean.FALSE);

    RestVariable var2 = new RestVariable();
    var2.setName("managerMotivation");
    var2.setValue("nein");

    List<RestVariable> vars = new ArrayList<>();
    vars.add(var);
    vars.add(var2);

    TaskActionRequest tar = new TaskActionRequest();
    tar.setAction(TaskActionRequest.ACTION_COMPLETE);
    tar.setVariables(vars);

    String url = "http://localhost:9000/activiti/service/runtime/tasks/{taskId}";
    restTemplate.postForObject(url, tar, String.class, taskId + "");
  }

  @Override
  public void adjustVacationRequest(int taskId, Date startDate, Integer numberOfDays, String motivation) {
    RestVariable startDateVar = new RestVariable();
    startDateVar.setName("startDate");
    startDateVar.setValue(startDate);

    RestVariable vacationMotivationVar = new RestVariable();
    vacationMotivationVar.setName("vacationMotivation");
    vacationMotivationVar.setValue(motivation);

    RestVariable numberOfDaysVar = new RestVariable();
    numberOfDaysVar.setName("numberOfDays");
    numberOfDaysVar.setValue(numberOfDays);

    RestVariable resendRequest = new RestVariable();
    resendRequest.setName("resendRequest");
    resendRequest.setValue(Boolean.TRUE);

    List<RestVariable> variables = new ArrayList<>();
    variables.add(startDateVar);
    variables.add(vacationMotivationVar);
    variables.add(numberOfDaysVar);
    variables.add(resendRequest);

    TaskActionRequest tar = new TaskActionRequest();
    tar.setAction(TaskActionRequest.ACTION_COMPLETE);
    tar.setVariables(variables);

    String url = "http://localhost:9000/activiti/service/runtime/tasks/{taskId}";
    restTemplate.postForObject(url, tar, String.class, taskId + "");
  }

  @Override
  public void cancelVacationRequest(int taskId) {
    RestVariable resendRequestVar = new RestVariable();
    resendRequestVar.setName("resendRequest");
    resendRequestVar.setValue(Boolean.FALSE);

    List<RestVariable> variables = new ArrayList<>();
    variables.add(resendRequestVar);

    TaskActionRequest tar = new TaskActionRequest();
    tar.setAction(TaskActionRequest.ACTION_COMPLETE);
    tar.setVariables(variables);

    String url = "http://localhost:9000/activiti/service/runtime/tasks/{taskId}";
    restTemplate.postForObject(url, tar, String.class, taskId + "");
  }
}