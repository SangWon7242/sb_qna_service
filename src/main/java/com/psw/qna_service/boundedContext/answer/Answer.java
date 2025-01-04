package com.psw.qna_service.boundedContext.answer;

import com.psw.qna_service.boundedContext.question.Question;
import com.psw.qna_service.boundedContext.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

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
  private LocalDateTime modifyDate;

  @ManyToOne
  @ToString.Exclude // ToString 대상에서 제외
  private Question question;

  @ManyToOne
  private SiteUser author;

  @ManyToMany
  private Set<SiteUser> voters = new LinkedHashSet<>();

  public void addVoter(SiteUser voter) {
    voters.add(voter);
  }
}
