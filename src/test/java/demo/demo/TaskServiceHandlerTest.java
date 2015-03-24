package demo.demo;

import static org.junit.Assert.assertNotNull;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.Task;
import demo.rest.TaskList;
import demo.service.TaskServiceHandler;
import demo.service.TaskServiceHandlerImpl;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskResponse;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
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
  public void startVacationRequest(){

  }

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
    DataResponse result = taskServiceHandler.getPool();
    assertNotNull(result);
    logger.info(result.toString());
  }

  @Test
  public void claimTaskTest(){
//    DataResponse result = taskServiceHandler.getPool();
//    List<TaskResponse> tasks = (List<TaskResponse>)result.getData();
//    TaskResponse task=tasks.get(0);
//    taskServiceHandler.claim(task, "kermit");
  }

  @Test
  public void approveVacationRequest() {
    DataResponse result = taskServiceHandler.getTasksFor("kermit");
    List<Object> tasks = (List<Object>)result.getData();
    TaskResponse task=(TaskResponse)tasks.get(0);
    taskServiceHandler.approveVacationRequest(task.getId());
  }
}
