package demo.service;

import demo.dto.BPMPost;
import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.ProcessList;
import org.activiti.rest.service.api.repository.ProcessDefinitionResponse;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

public class ProcessServiceHandlerImpl implements ProcessServiceHandler {
  private final static Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerImpl.class);
  private static final String URL = "http://localhost:9000/activiti/service/runtime/process-instances?includeProcessVariables=true&size=100&processDefinitionKey=vacationRequest&includeProcessVariables=true";
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  @Override
  public ProcessDefinitionResponse initiateVacationRequestProcess(BPMPost post) {
    ProcessDefinitionResponse result = restTemplate.postForObject(URL, post, ProcessDefinitionResponse.class);
    logger.info(result.toString());
    return result;
  }

  @Override
  public ProcessList getRunningProcessInstances() {
    ProcessList result = restTemplate.getForObject(URL, ProcessList.class);
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
}