package demo;

import static org.junit.Assert.assertNotNull;

import demo.rest.AuthHttpComponentsClientHttpRequestFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

public class ActivitiRestClient {
  private static final String URL="http://localhost:9000/activiti/service/runtime/process-instances/";
  private final static Logger logger = LoggerFactory.getLogger(ActivitiRestClient.class);

  @Test
  public void authorizedRequest() {
//    RestTemplate restTemplate = new RestTemplate();
//    String plainCreds = "kermit:kermit";
//    byte[] plainCredsBytes = plainCreds.getBytes();
//    byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
//    String base64Creds = new String(base64CredsBytes);
//
//    HttpHeaders headers = new HttpHeaders();
//    headers.add("Authorization", "Basic " + base64Creds);
//
//    HttpEntity<String> request = new HttpEntity<String>(headers);
//    ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
//    assertNotNull(response);
//    logger.info(response.toString());

    HttpHost host = new HttpHost("localhost",9000);

    final AuthHttpComponentsClientHttpRequestFactory requestFactory =
        new AuthHttpComponentsClientHttpRequestFactory(
            host, "kermit", "kermit");
    final RestTemplate restTemplate = new RestTemplate(requestFactory);
    String result = restTemplate.getForObject(URL, String.class);
    assertNotNull(result);
    logger.info(result.toString());
  }
  @Test( expected = HttpClientErrorException.class)
  public void unauthorizedRequest() {
    RestTemplate restTemplate = new RestTemplate();
    String plainCreds = "kermit:wrongPassword";
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
    assertNotNull(response);
    logger.info(response.toString());
  }

  @Test
  public void getAllProcessInstances(){
    RestTemplate restTemplate = new RestTemplate();
    String plainCreds = "kermit:kermit";
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    HttpEntity<String> request = new HttpEntity<String>(headers);
    ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, request, String.class);
    assertNotNull(response);
    logger.info(response.toString());
  }


}
