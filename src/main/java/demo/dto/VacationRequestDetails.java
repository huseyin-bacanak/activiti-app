package demo.dto;

public class VacationRequestDetails {
  private String employeeName;
  private int numberOfDays;
  private String vacationMotivation;

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

  @Override
  public String toString() {
    return "VacationRequestDetails{" +
        "employeeName='" + employeeName + '\'' +
        ", numberOfDays=" + numberOfDays +
        ", vacationMotivation='" + vacationMotivation + '\'' +
        '}';
  }
}
