package com.psw.qna_service.boundedContext.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Controller + ResponseBody를 결함한 어노테이션
// 해당 클래스 내부에 모든 메서드가 반환되는 데이터를 HTTP 응답 본문으로 반환
// JSON이나 텍스트 같은 형식으로 데이터를 전달할 때 유용함
@RequestMapping("/api")
// 모든 메서드의 기본 URL 경로를 /api로 시작하도록 설정
public class HomeController {

  @GetMapping("/data")
  // HTTP GET 요청을 /api/data 경로에서 처리하도록
  public String test() {
    return "안녕";
  }
}
