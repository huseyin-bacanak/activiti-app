package demo.web.controller;

import demo.dto.AdjustVacationRequestDetails;
import demo.dto.JsonResponse;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/vacationRequest")
public class MyJobsController extends BaseController {
  /**
   * My jobs page request.
   */
  @RequestMapping(value = "/myJobs", method = RequestMethod.GET)
  public String confirmation(Model model) {
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars(
        (List<Map<String, String>>) getTaskServiceHandler().getTasksFor("kermit").getData());
    model.addAttribute("myTasks", detailsList);
    model.addAttribute("processInstances", getTaskServiceHandler().getTasksFor("kermit").getData());
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
      getTaskServiceHandler().approveVacationRequest(taskId);
    } else if (action.equals("reject")) {
      getTaskServiceHandler().rejectVacationRequest(taskId);
    }
    return "redirect:myJobs";
  }

  /**
   * Handle a adjust request process instance.
   */
  @RequestMapping(value = "/adjustVacationRequest",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public  @ResponseBody JsonResponse adjustVacationRequest(
      @RequestBody AdjustVacationRequestDetails vacationRequestDetails, BindingResult result) {
    JsonResponse jsonResponse = new JsonResponse();
    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
    if (result.hasErrors()) {
      jsonResponse.setStatus("FAIL");
      jsonResponse.setResult(result.getAllErrors());
    } else {
      jsonResponse.setStatus("SUCCESS");
      if(vacationRequestDetails.getOperation().equals("resend")){
        getTaskServiceHandler().adjustVacationRequest(vacationRequestDetails.getTaskId(),
            vacationRequestDetails.getStartDate(),vacationRequestDetails.getNumberOfDays(),
            vacationRequestDetails.getVacationMotivation());
      } else if (vacationRequestDetails.getOperation().equals("cancel")) {
        getTaskServiceHandler().cancelVacationRequest(vacationRequestDetails.getTaskId());
      }
    }
    return jsonResponse;
  }
}
