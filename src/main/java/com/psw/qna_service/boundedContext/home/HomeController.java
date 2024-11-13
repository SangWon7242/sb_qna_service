package com.psw.qna_service.boundedContext.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

  @GetMapping("/data")
  public String test() {
    return "안녕";
  }
}
