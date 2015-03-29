package demo.web.controller;

import demo.dto.JsonResponse;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import demo.service.HistoryServiceHandler;
import demo.service.HistoryServiceHandlerImpl;
import demo.service.ProcessServiceHandler;
import demo.service.ProcessServiceHandlerImpl;
import demo.service.TaskServiceHandler;
import demo.service.TaskServiceHandlerImpl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

  @RequestMapping(value="/new",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody JsonResponse newVacationRequest( @RequestBody VacationRequestDetails vacationRequestDetails, BindingResult result) {
    JsonResponse res = new JsonResponse();
    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
    if (result.hasErrors()) {
      res.setStatus("FAIL");
      res.setResult(result.getAllErrors());
    } else {
      ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();

      processServiceHandler.initiateVacationRequest("kermit",
                                                    vacationRequestDetails.getStartDate(),
                                                    vacationRequestDetails.getNumberOfDays(),
                                                    vacationRequestDetails.getVacationMotivation());
//      model.addAttribute("successMessage", "Operation completed successfully");
//      model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
      res.setStatus("SUCCESS");
    }
    return res;
  }

//  @RequestMapping(value="/new",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//  public @ResponseBody VacationRequestDetails newVacationRequest( @RequestBody VacationRequestDetails vrd) {
//
//    System.out.println(vrd);
//    return vrd;
//  }

  @RequestMapping(value="/list", method= RequestMethod.GET)
  public String list(Model model){
    ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();
    model.addAttribute("runningProcesses", processServiceHandler.getRunningProcessInstances());
    return "vacationRequest/list";
  }

  @RequestMapping(value="/myJobs", method= RequestMethod.GET)
  public String confirmation(Model model){
    TaskServiceHandler handler= new TaskServiceHandlerImpl();
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars((List <Map<String,String>>)handler.getTasksFor("kermit").getData());
    model.addAttribute("myTasks", detailsList);
    model.addAttribute("processInstances",handler.getTasksFor("kermit").getData());
    model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
    return "vacationRequest/myJobs";
  }

  @RequestMapping(value="/myJobsForm", method=RequestMethod.POST)
  public String myJobsForm(@RequestParam String action, @RequestParam int processInstanceId, @RequestParam int taskId){
    ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();
    TaskServiceHandler taskServiceHandler= new TaskServiceHandlerImpl();
    if( action.equals("approve") ){
      taskServiceHandler.approveVacationRequest(taskId);
    }
    else if( action.equals("reject") ){
      taskServiceHandler.rejectVacationRequest(taskId);
    }
    return "redirect:myJobs";
  }

  @RequestMapping(value="/finished", method= RequestMethod.GET)
  public String finished(Model model){
    HistoryServiceHandler historyServiceHandler= new HistoryServiceHandlerImpl();
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars((List <Map<String,String>>)historyServiceHandler.getFinishedProcesses().getData());
    model.addAttribute("historicProcessInstances", detailsList);
    return "vacationRequest/finished";
  }

  @RequestMapping(value="/pool", method= RequestMethod.GET)
  public String pool(Model model){
    TaskServiceHandler handler= new TaskServiceHandlerImpl();
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars((List <Map<String,String>>)handler.getPool().getData());
    model.addAttribute("pool",detailsList);
    model.addAttribute("processInstances",handler.getPool().getData());
    return "vacationRequest/pool";
  }
  @RequestMapping(value="/poolForm", method=RequestMethod.POST)
  public String poolForm(@RequestParam String action, @RequestParam int processInstanceId, @RequestParam int taskId){
    ProcessServiceHandler processServiceHandler = new ProcessServiceHandlerImpl();
    TaskServiceHandler taskServiceHandler= new TaskServiceHandlerImpl();
    if( action.equals("claim") ){
      taskServiceHandler.claim(taskId, "kermit");
    }
    else if( action.equals("delete") ){
      processServiceHandler.deleteProcessInstance(processInstanceId);
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

  private List<VacationRequestDetails> createVacationDetailsFromVars(List <Map<String,String>> vars){
    List<VacationRequestDetails> detailsList= new ArrayList<>();
    for(Map task: vars){
      System.out.println(task);
      List variables= (List)task.get("variables");
      VacationRequestDetails details = new VacationRequestDetails();
      details.setEmployeeName(findValue("employeeName",variables).toString());
      details.setNumberOfDays((Integer)findValue("numberOfDays",variables));
      details.setVacationMotivation((String) findValue("vacationMotivation", variables));
      details.setStartDate(new Date((Long) findValue("startDate", variables)));
      detailsList.add(details);
    }
    return detailsList;
  }
}
