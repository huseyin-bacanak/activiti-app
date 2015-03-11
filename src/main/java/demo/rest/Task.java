package demo.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import demo.dto.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task {

  private String id;
  private String url;
  private Object owner;
  private Object assignee;
  private Object delegationState;
  private String name;
  private String description;
  private String createTime;
  private Object dueDate;
  private Integer priority;
  private Boolean suspended;
  private String taskDefinitionKey;
  private String tenantId;
  private Object category;
  private Object formKey;
  private Object parentTaskId;
  private Object parentTaskUrl;
  private String executionId;
  private String executionUrl;
  private String processInstanceId;
  private String processInstanceUrl;
  private String processDefinitionId;
  private String processDefinitionUrl;
  private List<Variable> variables = new ArrayList<Variable>();


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


  public Object getOwner() {
    return owner;
  }


  public void setOwner(Object owner) {
    this.owner = owner;
  }


  public Object getAssignee() {
    return assignee;
  }


  public void setAssignee(Object assignee) {
    this.assignee = assignee;
  }


  public Object getDelegationState() {
    return delegationState;
  }

  public void setDelegationState(Object delegationState) {
    this.delegationState = delegationState;
  }

  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public String getCreateTime() {
    return createTime;
  }


  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }


  public Object getDueDate() {
    return dueDate;
  }


  public void setDueDate(Object dueDate) {
    this.dueDate = dueDate;
  }


  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }


  public Boolean getSuspended() {
    return suspended;
  }


  public void setSuspended(Boolean suspended) {
    this.suspended = suspended;
  }

  public String getTaskDefinitionKey() {
    return taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey) {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public Object getCategory() {
    return category;
  }

  public void setCategory(Object category) {
    this.category = category;
  }

  public Object getFormKey() {
    return formKey;
  }

  public void setFormKey(Object formKey) {
    this.formKey = formKey;
  }

  public Object getParentTaskId() {
    return parentTaskId;
  }

  public void setParentTaskId(Object parentTaskId) {
    this.parentTaskId = parentTaskId;
  }

  public Object getParentTaskUrl() {
    return parentTaskUrl;
  }
  public void setParentTaskUrl(Object parentTaskUrl) {
    this.parentTaskUrl = parentTaskUrl;
  }

  public String getExecutionId() {
    return executionId;
  }


  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }


  public String getExecutionUrl() {
    return executionUrl;
  }


  public void setExecutionUrl(String executionUrl) {
    this.executionUrl = executionUrl;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }
  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcessInstanceUrl() {
    return processInstanceUrl;
  }

  public void setProcessInstanceUrl(String processInstanceUrl) {
    this.processInstanceUrl = processInstanceUrl;
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
  public void setVariable(String variableName, Object value){
    for(Variable v : variables) {
      if(v.getName().equals(variableName)) {
        v.setValue(value);
      }
    }

    Variable v = new Variable();
    v.setName(variableName);
    v.setValue(value);
    variables.add(v);
  }

}