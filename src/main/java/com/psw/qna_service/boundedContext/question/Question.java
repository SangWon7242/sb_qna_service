package com.psw.qna_service.boundedContext.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Question {
  @Id // PRIMARY KEY
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
  private Integer id; // INT id

  @Column(length = 200) // VARCHAR(200)
  private String subject;

  @Column(columnDefinition = "TEXT") // TEST
  private String content;

  private LocalDateTime createDate; // DATETIME
}
