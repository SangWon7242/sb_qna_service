package com.psw.qna_service.boundedContext.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionService questionService;

  @GetMapping("/list")
  public String list(Model model) {
    List<Question> questionList = questionService.findAll();
    model.addAttribute("questionList", questionList);
    return "question_list";
  }

  @GetMapping(value = "/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id) {
    Question question = questionService.getQuestion(id);

    model.addAttribute("question", question);

    return "question_detail";
  }

  @GetMapping("/create")
  public String questionCreate() {
    return "question_form";
  }

  @PostMapping("/create")
  public String questionCreate(String subject, String content) {
    questionService.create(subject, content);
    return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
  }
}

