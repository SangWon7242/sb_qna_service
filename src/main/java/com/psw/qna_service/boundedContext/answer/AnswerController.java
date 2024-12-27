package com.psw.qna_service.boundedContext.answer;

import com.psw.qna_service.boundedContext.question.Question;
import com.psw.qna_service.boundedContext.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

  private final QuestionService questionService;
  private final AnswerService answerService;

  @PostMapping("/create/{id}")
  public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content, @Valid AnswerForm answerForm, BindingResult bindingResult) {
    // 관련 질문을 얻어온다.
    Question question = questionService.getQuestion(id);

    if (bindingResult.hasErrors()) {
      model.addAttribute("question", question);
      return "question_detail";
    }

    // TODO: 답변을 저장한다.
    Answer answer = answerService.create(question, answerForm.getContent());
    // 아래와 같이 처리요청에 대해서는, 처리 후 빨리 떠나는 게 좋다.
    // 단 적절한 if 문을 사용하면 유용한 방법이 될 수 도 있다. 즉 머무는게 좋은 경우도 있음
    // return "%d번 질문에 대한 답변이 생성되었습니다.(답변번호 : %d)".formatted(id, answer.getId());

    return "redirect:/question/detail/%s".formatted(id);
  }
}
