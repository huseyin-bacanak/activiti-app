package demo.dto;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by huseyin.bacanak on 04.03.2015.
 */
public class VacationRequestDetailsValidator {

  public void validate(VacationRequestDetails vacationRequestDetails, Errors errors) {
    int numberOfDays = vacationRequestDetails.getNumberOfDays();
    if (numberOfDays<5) {
      errors.rejectValue("numberOfDays", "required", "required");
    }
  }
}
