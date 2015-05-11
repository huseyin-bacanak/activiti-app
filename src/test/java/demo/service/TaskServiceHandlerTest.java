package demo.service;

import static org.junit.Assert.assertNotNull;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import org.activiti.rest.common.api.DataResponse;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class TaskServiceHandlerTest {
  private static final String URL =
      "http://localhost:9000/activiti/service/runtime/process-instances/";
  private static final  Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerTest.class);
  private final HttpHost host = new HttpHost("localhost", 9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  private TaskServiceHandler taskServiceHandler = new TaskServiceHandlerImpl();

  @Test
  public void authorizedRequest() {
    String result = restTemplate.getForObject(URL, String.class);
    assertNotNull(result);
    logger.info(result.toString());
  }

  @Test(expected = HttpClientErrorException.class)
  public void unauthorizedRequest() {

    final AuthHttpComponentsClientHttpRequestFactory requestFactory =
        new AuthHttpComponentsClientHttpRequestFactory(
            host, "kermit", "wrongPassword");
    final RestTemplate restTemplate2 = new RestTemplate(requestFactory);
    String result = restTemplate2.getForObject(URL, String.class);
    logger.info(result.toString());
  }

  @Test
  public void getPoolTest() {
    DataResponse result = taskServiceHandler.getPool();
    assertNotNull(result);
    logger.info(result.toString());
  }
}
