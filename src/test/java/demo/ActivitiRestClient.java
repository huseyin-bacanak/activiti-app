package demo;

import static org.junit.Assert.assertNotNull;

import demo.dto.BPMPost;
import demo.dto.Entry;
import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.ProcessList;
import demo.rest.VacationProcessInstance;
import demo.service.BPMService;
import demo.service.BPMServiceImpl;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    ProcessList result = restTemplate.getForObject(URL, ProcessList.class);
    assertNotNull(result);
    logger.info(result.toString());
  }

  @Test
  public void createNewVacationRequest(){
    BPMPost post = new BPMPost();
    post.setProcessDefinitionKey("vacationRequest");
    List<Entry> variables=  new ArrayList<>();
    variables.add(new Entry("numberOfDays", "5"));
    variables.add(new Entry("employeeName","kermit"));
    variables.add(new Entry("vacationMotivation","sasaas"));
    post.setVariables(variables);

    VacationProcessInstance pi = bpmService.initiateVacationRequestProcess(post);
    assertNotNull(pi);
  }

}
