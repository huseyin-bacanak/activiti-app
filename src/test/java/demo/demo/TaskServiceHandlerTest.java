package demo.demo;

import static org.junit.Assert.assertNotNull;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.Task;
import demo.rest.TaskList;
import demo.service.TaskServiceHandler;
import demo.service.TaskServiceHandlerImpl;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class TaskServiceHandlerTest {
  private static final String URL="http://localhost:9000/activiti/service/runtime/process-instances/";
  private final static Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerTest.class);
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  private TaskServiceHandler taskServiceHandler =new TaskServiceHandlerImpl();


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
  public void getPoolTest(){
    TaskList result = taskServiceHandler.getPool();
    assertNotNull(result);
    logger.info(result.toString());
  }

  @Test
  public void claimTaskTest(){
    TaskList result = taskServiceHandler.getPool();
    List<Task> tasks = result.getData();
    Task task=tasks.get(0);
    taskServiceHandler.claim(task, "kermit");
  }

  @Test
  public void approveVacationRequest() {
    TaskList result = taskServiceHandler.getTasksFor("kermit");
    List<Task> tasks = result.getData();
    Task task=tasks.get(0);
    taskServiceHandler.approveVacationRequest(task.getId());
  }
}
