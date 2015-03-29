package demo.web.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DemoController {
  @RequestMapping("/")
  public String home() {
    return "redirect:vacationRequest/new";
  }

  @RequestMapping("/ajax")
  public String ajax() {
    return "demo/ajax";
  }

  @RequestMapping("/ajax/item")
  @ResponseBody
  public String item() throws Exception {
    return "sasasa";
  }

  @RequestMapping("/websocket")
  public String websocket() {
    return "demo/websocket";
  }
  @RequestMapping("/contextJs")

  public String contextJs() {
    return "demo/contextJs";
  }


  @RequestMapping(value = "/profile", method=RequestMethod.POST, consumes="application/json")
  public @ResponseBody User processAJAXRequest(@RequestBody User user) {
    // Process the request
    // Prepare the response string
    return user;
  }


}
