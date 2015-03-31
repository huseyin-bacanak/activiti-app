package demo.web.controller.vacationrequest;

import demo.dto.VacationRequestDetails;
import demo.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/vacationRequest")
public class PoolController extends BaseController {
  /**
   * Job Pool page request.
   */
  @RequestMapping(value = "/pool", method = RequestMethod.GET)
  public String pool(Model model) {
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
}
