package com.psw.qna_service.boundedContext.question;

import com.psw.qna_service.boundedContext.answer.Answer;
import com.psw.qna_service.boundedContext.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@ToString
public class Question {
  @Id // PRIMARY KEY
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
  private Integer id; // INT id

  @Column(length = 200) // VARCHAR(200)
  private String subject;

  @Column(columnDefinition = "TEXT") // TEST
  private String content;

  private LocalDateTime createDate; // DATETIME

  @ManyToOne
  private SiteUser author;

  // @OneToMany 자바세상에서의 편의를 위해서 필드 생성
  // 이 녀석은 실제 DB 테이블에 칼럼이 생성되지 않는다.
  // DB는 리스트나 배열을 저장할 수 없다.
  // 만들어도 되고 만들지 않아도 된다.
  // 다만 만들면 해당 객체(질문객체)에서 관련된 답변들을 찾을 때 편하다.
  @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
  // OneToMany 에는 직접객체초기화
  private List<Answer> answerList = new ArrayList<>();
  
  // 객체 내부의 상태를 캡슐화 할 수 있다.
  // 외부에서 컬렉션 필드로 접근하는 것을 차단
  public void addAnswer(Answer a) {
    a.setQuestion(this); // Question 객체에 Answer 추가
    answerList.add(a); // Answer 객체에 Question 설정
  }
}
