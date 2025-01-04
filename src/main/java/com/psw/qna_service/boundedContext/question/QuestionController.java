package com.psw.qna_service.boundedContext.question;

import com.psw.qna_service.boundedContext.answer.AnswerForm;
import com.psw.qna_service.boundedContext.user.SiteUser;
import com.psw.qna_service.boundedContext.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionService questionService;
  private final UserService userService;

  @GetMapping("/list")
  public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
    Page<Question> paging = questionService.getList(page);

    model.addAttribute("paging", paging);
    return "question_list";
  }

  @GetMapping("/detail/{id}")
  public String detail(Model model, @PathVariable("id") Integer id,  AnswerForm answerForm) {
    Question question = questionService.getQuestion(id);

    model.addAttribute("question", question);
    model.addAttribute("answerForm", answerForm);

    return "question_detail";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/create")
  public String questionCreate(Model model) {
    model.addAttribute("questionForm", new QuestionForm());
    return "question_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/create")
  // @Valid QuestionForm questionForm
  // questionForm 값을 바인딩 할 때 유효성 체크를 해라!
  public String questionCreate(
      @Valid QuestionForm questionForm,
      BindingResult bindingResult,
      Principal principal
  ) {

    SiteUser siteUser = userService.getUser(principal.getName());

    if (bindingResult.hasErrors()) {
      // question_form.html 실행
      // 다시 작성하라는 의미로 응답에 폼을 실어서 보냄
      return "question_form";
    }

    questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);

    return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/modify/{id}")
  public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
    Question question = questionService.getQuestion(id);

    if(!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
      // 런타임 입셉션을 사용해도 상관없음
    }

    questionForm.setSubject(question.getSubject());
    questionForm.setContent(question.getContent());

    return "question_form";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/modify/{id}")
  public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                               Principal principal, @PathVariable("id") Integer id) {
    if (bindingResult.hasErrors()) {
      return "question_form";
    }

    Question question = questionService.getQuestion(id);

    // 체크로직이 없으면 해커가 URL 입력을 통해 질문을 수정할 수 있음
    // 수정 권한이 있는지 체크 필요
    if (!question.getAuthor().getUsername().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
    }

    questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
    return String.format("redirect:/question/detail/%s", id);
  }
}


