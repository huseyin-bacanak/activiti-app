package demo.rest;

import demo.dto.Variable;

import java.util.List;

/**
 * Created by huseyin.bacanak on 09.03.2015.
 */
public class VacationProcessInstance {
  private String id;
  private String url;
  private String businessKey;
  private String suspended;
  private String ended;
  private String processDefinitionId;
  private String processDefinitionUrl;
  private String activityId;
  private List<Variable> variables;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getBusinessKey() {
    return businessKey;
  }

  public void setBusinessKey(String businessKey) {
    this.businessKey = businessKey;
  }

  public String getSuspended() {
    return suspended;
  }

  public void setSuspended(String suspended) {
    this.suspended = suspended;
  }

  public String getEnded() {
    return ended;
  }

  public void setEnded(String ended) {
    this.ended = ended;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getProcessDefinitionUrl() {
    return processDefinitionUrl;
  }

  public void setProcessDefinitionUrl(String processDefinitionUrl) {
    this.processDefinitionUrl = processDefinitionUrl;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public List<Variable> getVariables() {
    return variables;
  }

  public void setVariables(List<Variable> variables) {
    this.variables = variables;
  }


  public Variable getVariable(String variableName){

    for(Variable v : variables) {
      if(v.getName().equals(variableName)) {
        if(v.getValue() == null || v.getValue().toString().isEmpty()){
          v.setValue("-");
        }
        return v;
      }
    }

    Variable v = new Variable();
    v.setName(variableName);
    v.setValue("N/A");
    return v;
  }

  @Override
  public String toString() {
    return "VacationProcessInstance{" +
        "id='" + id + '\'' +
        ", url='" + url + '\'' +
        ", businessKey='" + businessKey + '\'' +
        ", suspended='" + suspended + '\'' +
        ", ended='" + ended + '\'' +
        ", processDefinitionId='" + processDefinitionId + '\'' +
        ", processDefinitionUrl='" + processDefinitionUrl + '\'' +
        ", activityId='" + activityId + '\'' +
        ", variables=" + variables +
        '}';
  }
}
