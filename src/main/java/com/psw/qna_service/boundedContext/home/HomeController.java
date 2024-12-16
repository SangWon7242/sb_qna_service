package com.psw.qna_service.boundedContext.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
// Controller + ResponseBody를 결함한 어노테이션
// 해당 클래스 내부에 모든 메서드가 반환되는 데이터를 HTTP 응답 본문으로 반환
// JSON이나 텍스트 같은 형식으로 데이터를 전달할 때 유용함
@RequestMapping("/api")
public class HomeController {

  @GetMapping("/home")
  public List<String> showHome() {
    System.out.println("실행");
    List<String> arr = new ArrayList<>() {{
      add("반가워");
      add("어서와");
      add("잘가");
    }};
    return arr;
  }
}
