package com.psw.qna_service.boundedContext.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {
  @NotEmpty(message = "내용은 필수항목입니다.")
  @Size(max = 200, message = "내용을 20,000자 이하로 입력해주세요.")
  private String content;
}
