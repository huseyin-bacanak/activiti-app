package demo.web.controller;

import demo.dto.BPMPost;
import demo.dto.Entry;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import demo.service.BPMService;
import demo.service.BPMServiceImpl;
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
    BPMService bpmService= new BPMServiceImpl();
    model.addAttribute("runningProcesses", bpmService.getRunningProcessInstances());
    return "vacationRequest/list";
  }


  @RequestMapping(value="/new", method= RequestMethod.POST)
  public String vacationRequest(Model model, @ModelAttribute("vacationRequestDetails") VacationRequestDetails vacationRequestDetails, BindingResult result, SessionStatus status){
//    System.out.println("\n\n\n"+ vacationRequestDetails+"\n\n\n");
//    model.addAttribute("vacationRequestDetails", vacationRequestDetails);
//    return "vacationRequest/pending";

    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
    if (result.hasErrors()) {
      return "vacationRequest/new";
    } else {
      BPMService bpmService= new BPMServiceImpl();

      BPMPost post = new BPMPost();
      post.setProcessDefinitionKey("vacationRequest");
      List<Entry> variables=  new ArrayList<>();
      variables.add(new Entry("numberOfDays", vacationRequestDetails.getNumberOfDays()+""));
      variables.add(new Entry("employeeName",vacationRequestDetails.getEmployeeName()));
      variables.add(new Entry("vacationMotivation",vacationRequestDetails.getVacationMotivation()));
      post.setVariables(variables);

      bpmService.initiateVacationRequestProcess(post);
      model.addAttribute("successMessage", "Operation completed successfully");
      model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
      return "vacationRequest/new";
    }
  }
}
