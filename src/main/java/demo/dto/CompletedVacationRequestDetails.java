package demo.dto;

/**
 * Created by huseyin.bacanak on 30.03.2015.
 */
public class CompletedVacationRequestDetails extends VacationRequestDetails{
  private Boolean status;

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }
}
