package demo.demo;

import static org.junit.Assert.assertNotNull;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.ProcessList;
import demo.service.ProcessServiceHandler;
import demo.service.ProcessServiceHandlerImpl;
import org.activiti.rest.service.api.engine.variable.RestVariable;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceCreateRequest;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProcessServiceHandlerTest {
  private static final String URL="http://localhost:9000/activiti/service/runtime/process-instances/";
  private final static Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerTest.class);
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  private ProcessServiceHandler processServiceHandler =new ProcessServiceHandlerImpl();

  @Test
  public void authorizedRequest() {
    String result = restTemplate.getForObject(URL, String.class);
    assertNotNull(result);
    logger.info(result.toString());
  }

  @Test( expected = HttpClientErrorException.class)
  public void unauthorizedRequest() {

    final AuthHttpComponentsClientHttpRequestFactory requestFactory =
        new AuthHttpComponentsClientHttpRequestFactory(
            host, "kermit", "wrongPassword");
    final RestTemplate restTemplate2 = new RestTemplate(requestFactory);
    String result = restTemplate2.getForObject(URL, String.class);
    logger.info(result.toString());
  }

  @Test
  public void getAllProcessInstances(){
    ProcessList result = processServiceHandler.getRunningProcessInstances();
    assertNotNull(result);
    logger.info(result.toString());
  }

  @Test
  public void createNewVacationRequest(){
//    BPMPost post = new BPMPost();
//    post.setProcessDefinitionKey("vacationRequest");
//    List<Variable> variables=  new ArrayList<>();
//    variables.add(new Variable("numberOfDays", "5"));
//    variables.add(new Variable("employeeName","kermit"));
//    variables.add(new Variable("vacationMotivation","sasaas"));
//    variables.add(new Variable("startDate","12/12/2015"));
//    post.setVariables(variables);
//
//    ProcessDefinitionResponse pi = processServiceHandler.initiateVacationRequestProcess(post);
//    assertNotNull(pi);
  }

  @Test
  public void approveVacationRequest(){
    ProcessInstanceCreateRequest post = new ProcessInstanceCreateRequest();
    post.setProcessDefinitionKey("vacationRequest");
    List<RestVariable> variables=  new ArrayList<>();
    RestVariable var= new RestVariable();
    var.setName("numberOfDays");
    var.setValue("5");
    variables.add(var);

    var.setName("employeeName");
    var.setValue("kermit");
    variables.add(var);

    var.setName("vacationMotivation");
    var.setValue("sasaas");
    variables.add(var);

    var.setName("startDate");
    var.setValue("12/12/2015");
    variables.add(var);

//    variables.add(new RestVariable("numberOfDays", "5"));
//    variables.add(new RestVariable("employeeName","kermit"));
//    variables.add(new RestVariable("vacationMotivation","sasaas"));
//    variables.add(new RestVariable("startDate","12/12/2015"));
    post.setVariables(variables);

    ProcessDefinitionResponse pi = processServiceHandler.initiateVacationRequestProcess(post);
    assertNotNull(pi);
  }

}
