package demo.web.controller;

import demo.dto.CompletedVacationRequestDetails;
import demo.dto.VacationRequestDetails;
import demo.service.HistoryServiceHandler;
import demo.service.ProcessServiceHandler;
import demo.service.TaskServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController {

  @Autowired
  private ProcessServiceHandler processServiceHandler;

  @Autowired
  private TaskServiceHandler taskServiceHandler;

  @Autowired
  private HistoryServiceHandler historyServiceHandler;

  protected ProcessServiceHandler getProcessServiceHandler() {
    return processServiceHandler;
  }

  protected TaskServiceHandler getTaskServiceHandler() {
    return taskServiceHandler;
  }

  protected HistoryServiceHandler getHistoryServiceHandler() {
    return historyServiceHandler;
  }

  /**
   * Find value of an object within variables.
   * @param name name of the variable.
   * @param detailsList variable obj.
   * @return value object
   */
  protected Object findValue(String name, List<LinkedHashMap> detailsList) {
    for (Map<String, Object> map : detailsList) {
      if (map.get("name").equals(name)) {
        return map.get("value");
      }
    }
    return "";
  }

  /**
   * Create VacationRequestDetails dto from varaibles.
   * @param vars variables object
   * @return list of vacation request details.
   */
  protected List<VacationRequestDetails> createVacationDetailsFromVars(
      List<Map<String, String>> vars) {

    List<VacationRequestDetails> detailsList = new ArrayList<>();
    for (Map task : vars) {
      List variables = (List) task.get("variables");
      VacationRequestDetails details = new VacationRequestDetails();
      details.setEmployeeName(findValue("employeeName", variables).toString());
      details.setNumberOfDays((Integer) findValue("numberOfDays", variables));
      details.setVacationMotivation((String) findValue("vacationMotivation", variables));
      details.setStartDate(new Date((Long) findValue("startDate", variables)));
      detailsList.add(details);
    }
    return detailsList;
  }

  /**
   * Create VacationRequestDetails dto from varaibles.
   * @param vars variables object
   * @return list of vacation request details.
   */
  protected List<CompletedVacationRequestDetails> createCompletedVacationDetailsFromVars(
      List<Map<String, String>> vars) {

    List<CompletedVacationRequestDetails> detailsList = new ArrayList<>();
    for (Map task : vars) {
      List variables = (List) task.get("variables");
      CompletedVacationRequestDetails details = new CompletedVacationRequestDetails();
      details.setEmployeeName(findValue("employeeName", variables).toString());
      details.setNumberOfDays((Integer) findValue("numberOfDays", variables));
      details.setVacationMotivation((String) findValue("vacationMotivation", variables));
      details.setStartDate(new Date((Long) findValue("startDate", variables)));
      details.setStatus((Boolean) findValue("vacationApproved", variables));
      detailsList.add(details);
    }
    return detailsList;
  }
}
