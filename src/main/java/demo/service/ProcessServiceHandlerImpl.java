package demo.service;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class ProcessServiceHandlerImpl implements ProcessServiceHandler {
  private static final Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerImpl.class);
  private static final String URL = "http://localhost:9000/activiti/service/runtime/process-instances?includeProcessVariables=true&size=100&processDefinitionKey=vacationRequest&includeProcessVariables=true";
  private final HttpHost host = new HttpHost("localhost", 9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  @Override
  public ProcessDefinitionResponse initiateVacationRequestProcess(
      ProcessInstanceCreateRequest post) {
    ProcessDefinitionResponse result =
        restTemplate.postForObject(URL, post, ProcessDefinitionResponse.class);
    logger.info(result.toString());
    return result;
  }

  @Override
  public ProcessInstanceResponse getRunningProcessInstances() {
    ProcessInstanceResponse result = restTemplate.getForObject(URL, ProcessInstanceResponse.class);
    logger.info(result.toString());
    return result;
  }

  /**
   * Get running vacation request process instances.
   */
  public void getRunningInstances() throws Exception {
    restTemplate.getForObject(URL, String.class, String.class);
  }

  @Override
  public ProcessInstanceResponse initiateVacationRequest(String employeeName, Date startDate,
                                                         int numberOfDays, String motivation) {
    ProcessInstanceCreateRequest req = new ProcessInstanceCreateRequest();
    req.setProcessDefinitionKey("vacationRequest");

    RestVariable var1 = new RestVariable();
    var1.setName("employeeName");
    var1.setValue(employeeName);

    RestVariable var2 = new RestVariable();
    var2.setName("startDate");
    var2.setValue(startDate);

    RestVariable var3 = new RestVariable();
    var3.setName("vacationMotivation");
    var3.setValue(motivation);

    RestVariable var4 = new RestVariable();
    var4.setName("numberOfDays");
    var4.setValue(numberOfDays);

    List<RestVariable> variables = new ArrayList<>();
    variables.add(var1);
    variables.add(var2);
    variables.add(var3);
    variables.add(var4);
    req.setVariables(variables);

    String url = "http://localhost:9000/activiti/service/runtime/process-instances";
    ProcessInstanceResponse result =
        restTemplate.postForObject(url, req, ProcessInstanceResponse.class);
    logger.info(result.toString());
    return result;
  }

  @Override
  public void deleteProcessInstance(int processInstanceId) {
    String url = "http://localhost:9000/activiti/service/runtime/process-instances/{processInstanceId}";
    restTemplate.delete(url, processInstanceId + "");
  }
}