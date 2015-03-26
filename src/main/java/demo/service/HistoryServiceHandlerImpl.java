package demo.service;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import demo.rest.ProcessList;
import org.activiti.rest.common.api.DataResponse;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by huseyin.bacanak on 26.03.2015.
 */
public class HistoryServiceHandlerImpl implements HistoryServiceHandler{
  private final static Logger logger = LoggerFactory.getLogger(ProcessServiceHandlerImpl.class);
  private static final String URL = "http://localhost:9000/activiti/service/history/historic-process-instances?includeProcessVariables=true&size=100&processDefinitionKey=vacationRequest&includeProcessVariables=true&finished=true";
  private final HttpHost host = new HttpHost("localhost",9000);
  private final AuthHttpComponentsClientHttpRequestFactory requestFactory =
      new AuthHttpComponentsClientHttpRequestFactory(
          host, "kermit", "kermit");
  private final RestTemplate restTemplate = new RestTemplate(requestFactory);

  @Override
  public DataResponse getFinishedProcesses() {
    DataResponse result = restTemplate.getForObject(URL, DataResponse.class);
    logger.info(result.toString());
    return result;
  }
}
