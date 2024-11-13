package com.psw.qna_service.boundedContext.answer;

import com.psw.qna_service.boundedContext.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Answer {
  @Id // PRIMARY KEY
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
  private Integer id;

  @Column(columnDefinition = "TEXT") // TEXT
  private String content;

  private LocalDateTime createDate; // DATETIME

  @ManyToOne
  private Question question;
}
