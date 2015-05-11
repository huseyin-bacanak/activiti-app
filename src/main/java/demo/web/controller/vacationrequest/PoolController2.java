package demo.web.controller.vacationrequest;

import demo.dto.JsonResponse;
import demo.dto.Pagination;
import demo.dto.RequestStatus;
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
import java.util.Map;

@Controller
@RequestMapping(value = "/vacationRequest/pool2")
public class PoolController2 extends BaseController {

  private static final String PAGE_INDEX_SELECTED = "pageIndexSelected";
  private static final String PAGE_INDEX_TO = "pageIndexTo";
  private static final String PAGE_INDEX_FROM = "pageIndexFrom";
  private static final int TASKS_PER_PAGE = 10;


  /**
   * Job Pool page request.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String pool(Model model) {
    return "vacationRequest/pool21";
  }

  /**
   * Handle claim request of a task within a task pool.
   */
  @RequestMapping(value = "/poolForm", method = RequestMethod.POST)
  public String poolForm(@RequestParam String action, @RequestParam int taskId) {
    if (action.equals("claim")) {
      getTaskServiceHandler().claim(taskId, "kermit");
    }

    return "redirect:/";
  }

  /**
   * Load page indexes.
   */
  @RequestMapping(value = "/page/{pageIndex}", method = RequestMethod.GET)
  @ResponseBody
  public JsonResponse getPage(@PathVariable long pageIndex) {
    DataResponse pool = getTaskServiceHandler().getPool(pageIndex);

    int size = pool.getSize();
    long total = pool.getTotal();
    long max = size == 0 ? 0 : total / size;

    Pagination pagination = new Pagination();

    if (pageIndex > max || pageIndex < 0) {
      return getInvalidPageNumberResponse();
    } else if (pageIndex - TASKS_PER_PAGE / 2 < 0) {
      pagination = getFrontPageIndex(pageIndex, size, total);
    } else if (pageIndex + TASKS_PER_PAGE / 2 > max) {
      pagination = getBackPageIndex(pageIndex, size, total);
    } else {
      pagination = getMiddlePageIndex(pageIndex, size, total);
    }

    Map<String, Object> data = new HashMap<>();
    data.put("tasks", pool);
    data.put("pagination", pagination);
    JsonResponse res = new JsonResponse(data);
    return res;
  }

  private JsonResponse getInvalidPageNumberResponse() {
    JsonResponse res = new JsonResponse();
    res.setStatus(RequestStatus.FAILURE);
    res.setMessage("Page Does Not Exist!");
    return res;
  }

  private Pagination getFrontPageIndex(long pageIndex, int size, long total) {
    long max = total / size;
    long from = 0L;
    long to = size;

    if (max < to) {
      to = max;
    }

    return new Pagination(from, to, pageIndex);
  }

  private Pagination getBackPageIndex(long pageIndex, int size, long total) {
    long max = total / size;
    long from = max - size;

    if (from < 0) {
      from = 0L;
    }

    return new Pagination(from, max, pageIndex);
  }

  private Pagination getMiddlePageIndex(long pageIndex, int size, long total) {
    long from = pageIndex - size / 2;
    long to = pageIndex + size / 2;
    return new Pagination(from, to, pageIndex);
  }
}
