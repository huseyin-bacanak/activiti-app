package demo.service;

import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import org.activiti.engine.FormService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class FormServiceImpl implements FormService{
  private final static Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerImpl.class);
  private static final String URL = "http://localhost:9000/activiti/service/form/form-data";
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);


  @Override
  public StartFormData getStartFormData(String processDefinitionId) {
    return restTemplate.getForObject(URL+"?processDefinitionId="+processDefinitionId, StartFormDataImpl.class);
  }

  @Override
  public Object getRenderedStartForm(String processDefinitionId) {
    return null;
  }

  @Override
  public Object getRenderedStartForm(String processDefinitionId, String formEngineName) {
    return null;
  }

  @Override
  public ProcessInstance submitStartFormData(String processDefinitionId, Map<String, String> properties) {
    return null;
  }

  @Override
  public ProcessInstance submitStartFormData(String processDefinitionId, String businessKey, Map<String, String> properties) {
    return null;
  }

  @Override
  public TaskFormData getTaskFormData(String taskId) {
    return null;
  }

  @Override
  public Object getRenderedTaskForm(String taskId) {
    return null;
  }

  @Override
  public Object getRenderedTaskForm(String taskId, String formEngineName) {
    return null;
  }

  @Override
  public void submitTaskFormData(String taskId, Map<String, String> properties) {

  }

  @Override
  public void saveFormData(String taskId, Map<String, String> properties) {

  }

  @Override
  public String getStartFormKey(String processDefinitionId) {
    return null;
  }

  @Override
  public String getTaskFormKey(String processDefinitionId, String taskDefinitionKey) {
    return null;
  }
}
