package demo.service;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class ProcessServiceHandlerImpl implements ProcessServiceHandler {
  private final static Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerImpl.class);
  private static final String URL = "http://localhost:9000/activiti/service/runtime/process-instances?includeProcessVariables=true&size=100&processDefinitionKey=vacationRequest&includeProcessVariables=true";
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  @Override
  public ProcessDefinitionResponse initiateVacationRequestProcess(ProcessInstanceCreateRequest post) {
    ProcessDefinitionResponse result = restTemplate.postForObject(URL, post, ProcessDefinitionResponse.class);
    logger.info(result.toString());
    return result;
  }

  @Override
  public ProcessInstanceResponse getRunningProcessInstances() {
    ProcessInstanceResponse result = restTemplate.getForObject(URL, ProcessInstanceResponse.class);
    logger.info(result.toString());
    return result;
  }

  public void getRunningInstances() throws Exception {
    RestTemplate restTemplate = new RestTemplate();
    String plainCreds = "kermit:kermit";
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
  }

  @Override
  public ProcessInstanceResponse initiateVacationRequest(String employeeName, Date startDate,
                                                              int numberOfDays, String motivation) {
    String url="http://localhost:9000/activiti/service/runtime/process-instances";
    ProcessInstanceCreateRequest req= new ProcessInstanceCreateRequest();
    req.setProcessDefinitionKey("vacationRequest");

    List<RestVariable> variables= new ArrayList<>();
    RestVariable var1= new RestVariable();
    var1.setName("employeeName");
    var1.setValue(employeeName);

    RestVariable var2= new RestVariable();
    var2.setName("startDate");
    var2.setValue(startDate);

    RestVariable var3= new RestVariable();
    var3.setName("vacationMotivation");
    var3.setValue(motivation);

    RestVariable var4= new RestVariable();
    var4.setName("numberOfDays");
    var4.setValue(numberOfDays);

    variables.add(var1);
    variables.add(var2);
    variables.add(var3);
    variables.add(var4);
    req.setVariables(variables);
    ProcessInstanceResponse result = restTemplate.postForObject(url, req, ProcessInstanceResponse.class);
    logger.info(result.toString());
    return result;
  }
  @Override
  public void deleteProcessInstance(int processInstanceId) {
    String url="http://localhost:9000/activiti/service/runtime/process-instances/{processInstanceId}";
    restTemplate.delete(url, processInstanceId+"");
  }
}