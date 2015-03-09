package demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import demo.dto.BPMPost;
import demo.dto.Entry;
import demo.dto.VacationRequestDetails;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class BPMServiceImpl implements BPMService{
  private static final String URL="http://localhost:9000/activiti/service/runtime/process-instances/";

  @Override
  public void initiateVacationRequestProcess(BPMPost post) {
    try{
      ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
      String json = ow.writeValueAsString(post);
      System.out.println(json);
    } catch(Exception e){
      e.printStackTrace();
    }

    MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
    String base64Creds = "a2VybWl0Omtlcm1pdA==";
    headers.add("Authorization", "Basic " + base64Creds);
    headers.add("Content-Type", "application/json");

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpEntity<BPMPost> request = new HttpEntity<BPMPost>(post, headers);

    String result  = restTemplate.postForObject(URL, request, String.class);
    System.out.println(result);
  }

  public void getRunningInstances() throws Exception{
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
  public static void main(String[] args) throws Exception{
    BPMServiceImpl service =new BPMServiceImpl();
    VacationRequestDetails vrd= new VacationRequestDetails();
    vrd.setEmployeeName("kermit");
    vrd.setNumberOfDays(5);
    vrd.setStartDate(new Date());
    vrd.setVacationMotivation("asd");

    BPMPost post = new BPMPost();
    post.setProcessDefinitionKey("vacationRequest");
    List<Entry> variables=  new ArrayList<>();
    variables.add(new Entry("numberOfDays", "5"));
    variables.add(new Entry("employeeName","kermit"));
    variables.add(new Entry("vacationMotivation","sasaas"));
    post.setVariables(variables);

    service.initiateVacationRequestProcess(post);
  }
}
