package demo.web.controller;

import demo.dto.BPMPost;
import demo.dto.Variable;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import demo.service.ProcessServiceHandler;
import demo.service.ProcessServiceHandlerImpl;
import demo.service.TaskServiceHandler;
import demo.service.TaskServiceHandlerImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/vacationRequest")
public class VacationController {

  @RequestMapping(value="/new", method= RequestMethod.GET)
  public String create(Model model){
    model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
    return "vacationRequest/new";
  }

  @RequestMapping(value="/list", method= RequestMethod.GET)
  public String list(Model model){
    ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();
    model.addAttribute("runningProcesses", processServiceHandler.getRunningProcessInstances());
    return "vacationRequest/list";
  }
  @RequestMapping(value="/myJobs", method= RequestMethod.GET)
  public String confirmation(Model model){
    TaskServiceHandler handler= new TaskServiceHandlerImpl();
    model.addAttribute("myTasks", handler.getTasksFor("kermit"));
    return "vacationRequest/myJobs";
  }

  @RequestMapping(value="/finished", method= RequestMethod.GET)
  public String finished(Model model){
    ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();
    model.addAttribute("runningProcesses", processServiceHandler.getRunningProcessInstances());
    return "vacationRequest/finished";
  }

  @RequestMapping(value="/new", method= RequestMethod.POST)
  public String vacationRequest(Model model, @ModelAttribute("vacationRequestDetails") VacationRequestDetails vacationRequestDetails, BindingResult result, SessionStatus status){

    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
    if (result.hasErrors()) {
      return "vacationRequest/new";
    } else {
      ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();

      BPMPost post = new BPMPost();
      post.setProcessDefinitionKey("vacationRequest");
      List<Variable> variables=  new ArrayList<>();
      variables.add(new Variable("numberOfDays", vacationRequestDetails.getNumberOfDays()+""));
      variables.add(new Variable("employeeName",vacationRequestDetails.getEmployeeName()));
      variables.add(new Variable("vacationMotivation",vacationRequestDetails.getVacationMotivation()));
      post.setVariables(variables);

      processServiceHandler.initiateVacationRequestProcess(post);
      model.addAttribute("successMessage", "Operation completed successfully");
      model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
      return "vacationRequest/new";
    }
  }
}
