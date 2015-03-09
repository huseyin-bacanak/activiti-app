package demo.dto;

import java.util.Date;

public class VacationRequestDetails {
  private String employeeName;
  private int numberOfDays;
  private String vacationMotivation;
  private Date startDate;


  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public int getNumberOfDays() {
    return numberOfDays;
  }

  public void setNumberOfDays(int numberOfDays) {
    this.numberOfDays = numberOfDays;
  }

  public String getVacationMotivation() {
    return vacationMotivation;
  }

  public void setVacationMotivation(String vacationMotivation) {
    this.vacationMotivation = vacationMotivation;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  @Override
  public String toString() {
    return "VacationRequestDetails{" +
        "employeeName='" + employeeName + '\'' +
        ", numberOfDays=" + numberOfDays +
        ", vacationMotivation='" + vacationMotivation + '\'' +
        '}';
  }
}
