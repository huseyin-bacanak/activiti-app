package demo.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huseyin.bacanak on 06.03.2015.
 */
public class BPMPost {
  private String processDefinitionKey;
  List<Entry> variables = new ArrayList<>();

  public String getProcessDefinitionKey() {
    return processDefinitionKey;
  }

  public void setProcessDefinitionKey(String processDefinitionKey) {
    this.processDefinitionKey = processDefinitionKey;
  }

  public List<Entry> getVariables() {
    return variables;
  }

  public void setVariables(List<Entry> variables) {
    this.variables = variables;
  }
}
