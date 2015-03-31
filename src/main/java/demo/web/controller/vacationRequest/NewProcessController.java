package demo.web.controller.vacationRequest;

import demo.dto.JsonResponse;
import demo.dto.VacationRequestDetails;
import demo.dto.VacationRequestDetailsValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/vacationRequest")
public class NewProcessController extends BaseController {
  /**
   * New Process page request.
   */
  @RequestMapping(value = "/new", method = RequestMethod.GET)
  public String create(Model model) {
    model.addAttribute("vacationRequestDetails", new VacationRequestDetails());
    return "vacationRequest/new";
  }

  /**
   * Handle a new vacation request process instance.
   */
  @RequestMapping(value = "/new",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public  @ResponseBody
  JsonResponse newVacationRequest(
      @RequestBody VacationRequestDetails vacationRequestDetails, BindingResult result) {

    JsonResponse res = new JsonResponse();
    new VacationRequestDetailsValidator().validate(vacationRequestDetails, result);
    if (result.hasErrors()) {
      res.setStatus("FAIL");
      res.setResult(result.getAllErrors());
    } else {
      getProcessServiceHandler().initiateVacationRequest("kermit",
          vacationRequestDetails.getStartDate(),
          vacationRequestDetails.getNumberOfDays(),
          vacationRequestDetails.getVacationMotivation());
      res.setStatus("SUCCESS");
    }
    return res;
  }
}
