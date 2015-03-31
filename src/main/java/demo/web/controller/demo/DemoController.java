package demo.web.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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


  @RequestMapping(value = "/profile", method = RequestMethod.POST, consumes = "application/json")
  @ResponseBody
  public User processAjaxRequest(@RequestBody User user) {
    return user;
  }


}
