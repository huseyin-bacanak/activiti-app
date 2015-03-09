package demo.web.controller;

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

@Controller
public class VacationController {
  @RequestMapping(value="/vacationRequest", method= RequestMethod.GET)
  public String vacationRequest(Model model){
    model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
    return "vacationRequest/new";
  }

//  @RequestMapping(value="/vacationRequest", method= RequestMethod.POST)
//  public String vacationRequest(Model model, @ModelAttribute("vacationRequestDetails") VacationRequestDetails vacationRequestDetails, BindingResult result, SessionStatus status){
////    System.out.println("\n\n\n"+ vacationRequestDetails+"\n\n\n");
////    model.addAttribute("vacationRequestDetails", vacationRequestDetails);
////    return "vacationRequest/pending";
//
//    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
//    if (result.hasErrors()) {
//      return "vacationRequest/new";
//    } else {
//      BPMService bpmService= new BPMServiceImpl();
//      bpmService.initiateVacationRequestProcess(vacationRequestDetails);
//      model.addAttribute("successMessage", "Operation completed successfully");
//      model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
//      return "vacationRequest/new";
//    }
//  }
}
