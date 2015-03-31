package demo.web.controller.vacationRequest;

import demo.dto.CompletedVacationRequestDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/vacationRequest")
public class FinishedController extends BaseController {

  /**
   * Finished jobs page request.
   */
  @RequestMapping(value = "/finished", method = RequestMethod.GET)
  public String finished(Model model) {
    List<CompletedVacationRequestDetails> detailsList = createCompletedVacationDetailsFromVars(
        (List<Map<String, String>>) getHistoryServiceHandler().getFinishedProcesses().getData());
    model.addAttribute("historicProcessInstances", detailsList);
    return "vacationRequest/finished";
  }
}
