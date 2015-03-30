package demo.web.controller;

import demo.dto.CompletedVacationRequestDetails;
import demo.dto.JsonResponse;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import demo.service.HistoryServiceHandler;
import demo.service.ProcessServiceHandler;
import demo.service.TaskServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/vacationRequest")
public class VacationController {

  @Autowired
  private ProcessServiceHandler processServiceHandler;

  @Autowired
  private TaskServiceHandler taskServiceHandler;

  @Autowired
  private HistoryServiceHandler historyServiceHandler;


  /**
   * New Process page request.
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String create(Model model) {
    model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
    return "vacationRequest/new";
  }

  /**
   * Handle a new vacation request process instance.
   */
  @RequestMapping(value = "/new",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public  @ResponseBody JsonResponse newVacationRequest(
      @RequestBody VacationRequestDetails vacationRequestDetails, BindingResult result) {

    JsonResponse res = new JsonResponse();
    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
    if (result.hasErrors()) {
      res.setStatus("FAIL");
      res.setResult(result.getAllErrors());
    } else {
      processServiceHandler.initiateVacationRequest("kermit",
          vacationRequestDetails.getStartDate(),
          vacationRequestDetails.getNumberOfDays(),
          vacationRequestDetails.getVacationMotivation());
      res.setStatus("SUCCESS");
    }
    return res;
  }

  /**
   * My jobs page request.
   */
  @RequestMapping(value = "/myJobs", method = RequestMethod.GET)
  public String confirmation(Model model) {
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars(
            (List<Map<String, String>>) taskServiceHandler.getTasksFor("kermit").getData());
    model.addAttribute("myTasks", detailsList);
    model.addAttribute("processInstances", taskServiceHandler.getTasksFor("kermit").getData());
    model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
    return "vacationRequest/myJobs";
  }

  /**
   * Approve or reject a vacation request.
   */
  @RequestMapping(value = "/myJobsForm", method = RequestMethod.POST)
  public String myJobsForm(@RequestParam String action,
                           @RequestParam int taskId) {
    if (action.equals("approve")) {
      taskServiceHandler.approveVacationRequest(taskId);
    } else if (action.equals("reject")) {
      taskServiceHandler.rejectVacationRequest(taskId);
    }
    return "redirect:myJobs";
  }

  /**
   * Job Pool page request.
   */
  @RequestMapping(value = "/pool", method = RequestMethod.GET)
  public String pool(Model model) {
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars(
        (List<Map<String, String>>) taskServiceHandler.getPool().getData());
    model.addAttribute("pool", detailsList);
    model.addAttribute("processInstances", taskServiceHandler.getPool().getData());
    return "vacationRequest/pool";
  }

  /**
   * Handle claim request of a task within a task pool.
   */
  @RequestMapping(value = "/poolForm", method = RequestMethod.POST)
  public String poolForm(@RequestParam String action,
                         @RequestParam int processInstanceId, @RequestParam int taskId) {
    if (action.equals("claim")) {
      taskServiceHandler.claim(taskId, "kermit");
    }
//    else if (action.equals("delete")) {
//      processServiceHandler.deleteProcessInstance(processInstanceId);
//    }
    return "redirect:pool";
  }

  /**
   * Finished jobs page request.
   */
  @RequestMapping(value = "/finished", method = RequestMethod.GET)
  public String finished(Model model) {
    List<CompletedVacationRequestDetails> detailsList = createCompletedVacationDetailsFromVars(
        (List<Map<String, String>>) historyServiceHandler.getFinishedProcesses().getData());
    model.addAttribute("historicProcessInstances", detailsList);
    return "vacationRequest/finished";
  }

  /**
   * Find value of an object within variables.
   * @param name name of the variable.
   * @param detailsList variable obj.
   * @return
   */
  private Object findValue(String name, List<LinkedHashMap> detailsList) {
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
  private List<VacationRequestDetails> createVacationDetailsFromVars(
      List<Map<String, String>> vars) {

    List<VacationRequestDetails> detailsList = new ArrayList<>();
    for (Map task : vars) {
      System.out.println(task);
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
  private List<CompletedVacationRequestDetails> createCompletedVacationDetailsFromVars(
      List<Map<String, String>> vars) {

    List<CompletedVacationRequestDetails> detailsList = new ArrayList<>();
    for (Map task : vars) {
      System.out.println(task);
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
