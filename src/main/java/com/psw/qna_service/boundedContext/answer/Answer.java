package com.psw.qna_service.boundedContext.answer;

import com.psw.qna_service.boundedContext.question.Question;
import com.psw.qna_service.boundedContext.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
public class Answer {
  @Id // PRIMARY KEY
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
  private Integer id;

  @Column(columnDefinition = "TEXT") // TEXT
  private String content;

  private LocalDateTime createDate; // DATETIME

  @ManyToOne
  @ToString.Exclude // ToString 대상에서 제외
  private Question question;

  @ManyToOne
  private SiteUser author;
}
