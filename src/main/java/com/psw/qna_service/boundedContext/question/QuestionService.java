package com.psw.qna_service.boundedContext.question;

import com.psw.qna_service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionRepository questionRepository;

  public List<Question> findAll() {
    return questionRepository.findAll();
  }

  public Question getQuestion(Integer id) {
    Optional<Question> oq = questionRepository.findById(id);


    if (oq.isEmpty()) throw new DataNotFoundException("question not found");

    return oq.get();
  }

  public Question create(String subject, String content) {

    Question q = new Question();
    q.setSubject(subject);
    q.setContent(content);
    q.setCreateDate(LocalDateTime.now());
    questionRepository.save(q);

    return q;
  }
}
