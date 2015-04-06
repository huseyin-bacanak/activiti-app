package demo.web.controller.vacationrequest;

import demo.dto.JsonResponse;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import demo.web.controller.BaseController;

import org.activiti.rest.common.api.DataResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/vacationRequest")
public class PoolController extends BaseController {
  private final String PAGE_INDEX_SELECTED="pageIndexSelected";
  private final String PAGE_INDEX_TO="pageIndexTo";
  private final String PAGE_INDEX_FROM="pageIndexFrom";
  /**
   * Job Pool page request.
   */
  @RequestMapping(value = "/pool", method = RequestMethod.GET)
  public String pool(Model model) {
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars(
        (List<Map<String, String>>) getTaskServiceHandler().getPool().getData());
    model.addAttribute("pool", detailsList);
    model.addAttribute("processInstances", getTaskServiceHandler().getPool().getData());

    long totalProcessInstances=getTaskServiceHandler().getPool().getTotal();
    long totalProcessInstancesPerPage=getTaskServiceHandler().getPool().getSize();
    long numberOfPages=totalProcessInstances/totalProcessInstancesPerPage;

    model.addAttribute("totalProcessInstances", totalProcessInstances);
    model.addAttribute("totalProcessInstancesPerPage", totalProcessInstancesPerPage);
    model.addAttribute("numberOfPages", numberOfPages);
    return "vacationRequest/pool";
  }

  /**
   * Handle a new vacation request process instance.
   */
  @RequestMapping(value = "/pool/page/{pageIndex}",
    method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse newVacationRequest(@PathVariable long pageIndex) {
    JsonResponse res = new JsonResponse();
    DataResponse pool=getTaskServiceHandler().getPool();

    int numberOfPageNumbers=10;
    int size=pool.getSize();
    long total=pool.getTotal();
    long max=total/size;

    if(pageIndex>max || pageIndex <0){
      return getInvalidPageNumberResponse();
    } else if(pageIndex-numberOfPageNumbers/2 <0){
      return getFrontPageIndex(pageIndex,size, total);
    } else if (pageIndex+numberOfPageNumbers/2 >max){
      return getBackPageIndex(pageIndex, size, total);
    } else {
      return getMiddlePageIndex(pageIndex,size,total);
    }
  }

  /**
   * Job Pool pagination.
   */
  @RequestMapping(value = "/poolPagination", method = RequestMethod.GET)
  public String poolPagination(Model model) {
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars(
      (List<Map<String, String>>) getTaskServiceHandler().getPool().getData());
    model.addAttribute("pool", detailsList);
    model.addAttribute("processInstances", getTaskServiceHandler().getPool().getData());
    return "vacationRequest/pool";
  }

  /**
   * Handle claim request of a task within a task pool.
   */
  @RequestMapping(value = "/poolForm", method = RequestMethod.POST)
  public String poolForm(@RequestParam String action,
                         @RequestParam int processInstanceId, @RequestParam int taskId) {
    if (action.equals("claim")) {
      getTaskServiceHandler().claim(taskId, "kermit");
    }

    return "redirect:pool";
  }

  private JsonResponse getInvalidPageNumberResponse(){
    JsonResponse res= new JsonResponse();
    res.setStatus("Page Does Not Exist!");
    return res;
  }

  private JsonResponse getFrontPageIndex( long pageIndex, int size, long total){
    long max=total/size;
    long from = 0L;
    long to = size;

    if(max<to){
      to=max;
    }

    return getSuccessJsonResponse(pageIndex, from, to);
  }

  private JsonResponse getBackPageIndex( long pageIndex, int size, long total){
    long max=total/size;
    long from = max-size;

    if(from<0){
      from=0L;
    }

    return getSuccessJsonResponse(pageIndex, from, max);
  }

  private JsonResponse getMiddlePageIndex( long pageIndex, int size, long total){
    long from = pageIndex-size/2;
    long to = pageIndex + size/2;
    return getSuccessJsonResponse(pageIndex, from, to);
  }

  private JsonResponse getSuccessJsonResponse(long selected, long from, long to){
    Map<String, Long> vars= new HashMap<>();
    vars.put(PAGE_INDEX_SELECTED,selected);
    vars.put(PAGE_INDEX_FROM,from);
    vars.put(PAGE_INDEX_TO,to);

    JsonResponse res= new JsonResponse();
    res.setStatus(JsonResponse.SUCCESS);
    res.setResult(vars);
    return res;
  }

}
