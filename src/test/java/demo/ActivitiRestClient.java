package demo;

import static org.junit.Assert.assertNotNull;

import demo.dto.BPMPost;
import demo.dto.Variable;
import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.ProcessList;
import demo.rest.VacationProcessInstance;
import demo.service.BPMService;
import demo.service.BPMServiceImpl;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ActivitiRestClient {
  private static final String URL="http://localhost:9000/activiti/service/runtime/process-instances/";
  private final static Logger logger = LoggerFactory.getLogger(ActivitiRestClient.class);
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  private BPMService bpmService=new BPMServiceImpl();


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
    ProcessList result = bpmService.getRunningProcessInstances();
    assertNotNull(result);
    logger.info(result.toString());
  }

  @Test
  public void createNewVacationRequest(){
    BPMPost post = new BPMPost();
    post.setProcessDefinitionKey("vacationRequest");
    List<Variable> variables=  new ArrayList<>();
    variables.add(new Variable("numberOfDays", "5"));
    variables.add(new Variable("employeeName","kermit"));
    variables.add(new Variable("vacationMotivation","sasaas"));
    variables.add(new Variable("startDate","12/12/2015"));
    variables.add(new Variable("ali","veli"));
    post.setVariables(variables);

    VacationProcessInstance pi = bpmService.initiateVacationRequestProcess(post);
    assertNotNull(pi);
  }

}
