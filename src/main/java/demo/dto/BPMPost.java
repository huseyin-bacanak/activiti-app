package demo.dto;

import java.util.ArrayList;
import java.util.List;

public class BPMPost {
  private String processDefinitionKey;
  private List<Entry> variables = new ArrayList<>();

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
