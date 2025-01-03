package com.psw.qna_service.boundedContext.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

  @GetMapping("/")
  public String root() {
    // redirect: 302
    // 302 는 "이 분을 찾아가 보세요." 라고 응답
    // 브라우저 주소가 아래로 바뀐다.
    return "redirect:/question/list";
  }
}
