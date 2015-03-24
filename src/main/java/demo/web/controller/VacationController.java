package demo.web.controller;

import demo.dto.BPMPost;
import demo.dto.Variable;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import demo.service.ProcessServiceHandler;
import demo.service.ProcessServiceHandlerImpl;
import demo.service.TaskServiceHandler;
import demo.service.TaskServiceHandlerImpl;

import org.activiti.engine.task.TaskInfo;
import org.activiti.rest.service.api.runtime.process.ProcessInstanceResponse;
import org.activiti.rest.service.api.runtime.task.TaskResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/vacationRequest")
public class VacationController {

  @RequestMapping(value="/new", method= RequestMethod.GET)
  public String create(Model model){
    model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
    return "vacationRequest/new";
  }

  @RequestMapping(value="/new", method= RequestMethod.POST)
  public String vacationRequest(Model model, @ModelAttribute("vacationRequestDetails") VacationRequestDetails vacationRequestDetails, BindingResult result, SessionStatus status){

    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
    if (result.hasErrors()) {
      return "vacationRequest/new";
    } else {
      ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();

//      BPMPost post = new BPMPost();
//      post.setProcessDefinitionKey("vacationRequest");
//      List<Variable> variables=  new ArrayList<>();
//      variables.add(new Variable("numberOfDays", vacationRequestDetails.getNumberOfDays()+""));
//      variables.add(new Variable("employeeName",vacationRequestDetails.getEmployeeName()));
//      variables.add(new Variable("vacationMotivation",vacationRequestDetails.getVacationMotivation()));
//      post.setVariables(variables);

      processServiceHandler.initiateVacationRequest("kermit",
                                                    vacationRequestDetails.getStartDate(),
                                                    vacationRequestDetails.getNumberOfDays(),
                                                    vacationRequestDetails.getVacationMotivation());
      model.addAttribute("successMessage", "Operation completed successfully");
      model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
      return "vacationRequest/new";
    }
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

  @RequestMapping(value="/pool", method= RequestMethod.GET)
  public String pool(Model model){
    TaskServiceHandler handler= new TaskServiceHandlerImpl();
    List<VacationRequestDetails> detailsList= new ArrayList<>();
    for(Map task: (List <Map<String,String>>)handler.getPool().getData()){
      System.out.println(task);
      List variables= (List)task.get("variables");
      VacationRequestDetails details = new VacationRequestDetails();
      details.setEmployeeName((String)findValue("employeeName",variables).toString());
      details.setNumberOfDays((Integer)findValue("numberOfDays",variables));
      details.setVacationMotivation((String) findValue("vacationMotivation", variables));
      details.setStartDate(new Date((Long) findValue("startDate", variables)));
//      details.setEmployeeName("asd");
//      details.setStartDate(new Date());
//      details.setVacationMotivation("asd");
//      details.setNumberOfDays(5);
      detailsList.add(details);
    }
    model.addAttribute("pool",detailsList);
    model.addAttribute("processInstances",handler.getPool().getData());
    return "vacationRequest/pool";
  }
  @RequestMapping(value="/poolForm", method=RequestMethod.POST)
  public String poolForm(@RequestParam String action, @RequestParam int processInstanceId){
    ProcessServiceHandler handler = new ProcessServiceHandlerImpl();
    if( action.equals("claim") ){
      //handle save
    }
    else if( action.equals("delete") ){
      handler.deleteProcessInstance(processInstanceId);
    }
    return "redirect:pool";
  }



  private Object findValue(String name, List<LinkedHashMap> detailsList){
    for(Map<String,Object> map:detailsList){
      if(map.get("name").equals(name)){
        return map.get("value");
      }
    }
    return "";
  }
}
