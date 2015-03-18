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

    RestVariable var2= new RestVariable();
    var2.setName("employeeName");
    var2.setValue("kermit");
    variables.add(var2);

    RestVariable var3= new RestVariable();
    var3.setName("vacationMotivation");
    var3.setValue("sasaas");
    variables.add(var3);

    RestVariable var4= new RestVariable();
    var4.setName("startDate");
    var4.setValue("12/12/2015");
    variables.add(var);

    post.setVariables(variables);

    ProcessDefinitionResponse pi = processServiceHandler.initiateVacationRequestProcess(post);
    assertNotNull(pi);
  }

}
