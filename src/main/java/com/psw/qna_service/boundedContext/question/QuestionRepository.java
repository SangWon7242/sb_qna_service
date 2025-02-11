package com.psw.qna_service.boundedContext.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
  Question findBySubject(String subject);

  Question findBySubjectAndContent(String subject, String content);

  List<Question> findBySubjectLike(String subject);

  @Modifying // 아래 Query는 INSERT, UPDATE, DELETE와 같은 데이터 변경 작업에만 사용해야 한다.
  // nativeQuery = true 여야 MySQL 쿼리 사용이 가능하다.
  @Transactional
  @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
  void clearAutoIncrement();

  Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}
