package demo.web.controller.vacationrequest;

import demo.dto.JsonResponse;
import demo.dto.RequestStatus;
import demo.dto.VacationRequestDetails;
import demo.web.controller.BaseController;

import org.activiti.rest.common.api.DataResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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

  private static final String PAGE_INDEX_SELECTED = "pageIndexSelected";
  private static final String PAGE_INDEX_TO = "pageIndexTo";
  private static final String PAGE_INDEX_FROM = "pageIndexFrom";
  private static final int TASKS_PER_PAGE = 10;

  /**
   * Job Pool page request.
   */
  @RequestMapping(value = "/pool", method = RequestMethod.GET)
  public String pool(Model model) {
    List<VacationRequestDetails> detailsList = createVacationDetailsFromVars(
            (List<Map<String, String>>) getTaskServiceHandler().getPool().getData());
    model.addAttribute("pool", detailsList);
    model.addAttribute("processInstances", getTaskServiceHandler().getPool().getData());

    long totalProcessInstances = getTaskServiceHandler().getPool().getTotal();
    long totalProcessInstancesPerPage = getTaskServiceHandler().getPool().getSize();
    long numberOfPages;
    if (totalProcessInstancesPerPage != 0) {
      numberOfPages = totalProcessInstances / totalProcessInstancesPerPage;
    }
    numberOfPages = 1;

    model.addAttribute("totalProcessInstances", totalProcessInstances);
    model.addAttribute("totalProcessInstancesPerPage", totalProcessInstancesPerPage);
    model.addAttribute("numberOfPages", numberOfPages);
    return "vacationRequest/pool";
  }

  /**
   * Load page indexes.
   */
  @RequestMapping(value = "/pool/page/{pageIndex}", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse getPageIndexes(@PathVariable long pageIndex) {
    DataResponse pool = getTaskServiceHandler().getPool();

    int size = pool.getSize();
    if (size <= 10) {
      size = 10;
    }
    long total = pool.getTotal();
    long max = total / size;

    if (pageIndex > max || pageIndex < 0) {
      return getInvalidPageNumberResponse();
    } else if (pageIndex - TASKS_PER_PAGE / 2 < 0) {
      return getFrontPageIndex(pageIndex, size, total);
    } else if (pageIndex + TASKS_PER_PAGE / 2 > max) {
      return getBackPageIndex(pageIndex, size, total);
    } else {
      return getMiddlePageIndex(pageIndex, size);
    }
  }

  /**
   * Get tasks for a page.
   * @param pageIndex page index.
   */
  @RequestMapping(value = "/pool/rows/{pageIndex}", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse getRows(@PathVariable long pageIndex) {
    JsonResponse res = new JsonResponse();
    DataResponse pool = getTaskServiceHandler().getPool(pageIndex);
    res.setStatus(RequestStatus.SUCCESS);
    res.setResult(pool);
    return res;
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

  private JsonResponse getInvalidPageNumberResponse() {
    JsonResponse res = new JsonResponse();
    res.setStatus(RequestStatus.FAILURE);
    res.setMessage("Page Does Not Exist!");
    return res;
  }

  private JsonResponse getFrontPageIndex(long pageIndex, int size, long total) {
    long max = total / size;
    long from = 0L;
    long to = size - 1;

    if (max < to) {
      to = max;
    }

    return getSuccessJsonResponse(pageIndex, from, to);
  }

  private JsonResponse getBackPageIndex(long pageIndex, int size, long total) {
    long max = total / size;
    long from = max - size;

    if (from < 0) {
      from = 0L;
    }

    return getSuccessJsonResponse(pageIndex, from, max);
  }

  private JsonResponse getMiddlePageIndex(long pageIndex, int size) {
    long from = pageIndex - size / 2;
    long to = pageIndex + size / 2;
    return getSuccessJsonResponse(pageIndex, from, to);
  }

  private JsonResponse getSuccessJsonResponse(long selected, long from, long to) {
    Map<String, Long> vars = new HashMap<>();
    vars.put(PAGE_INDEX_SELECTED, selected);
    vars.put(PAGE_INDEX_FROM, from);
    vars.put(PAGE_INDEX_TO, to);

    JsonResponse res = new JsonResponse();
    res.setStatus(RequestStatus.SUCCESS);
    res.setResult(vars);
    return res;
  }

}
