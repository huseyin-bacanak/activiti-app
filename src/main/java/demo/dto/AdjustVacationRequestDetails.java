package demo.dto;

/**
 * Created by huseyin.bacanak on 31.03.2015.
 */
public class AdjustVacationRequestDetails extends VacationRequestDetails{
  private int taskId;
  private String operation;

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }
}
