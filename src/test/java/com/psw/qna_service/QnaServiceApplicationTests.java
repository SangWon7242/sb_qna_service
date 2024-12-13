package com.psw.qna_service;

import com.psw.qna_service.boundedContext.answer.Answer;
import com.psw.qna_service.boundedContext.answer.AnswerRepository;
import com.psw.qna_service.boundedContext.question.Question;
import com.psw.qna_service.boundedContext.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QnaServiceApplicationTests {

  @Autowired // 필드 주입
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @BeforeEach // 아래 메서드는 각 테스트케이스가 실행되기 전에 실행
  void beforeEach() {
    
    // 답변 모든 데이터 삭제
    answerRepository.deleteAll();
    answerRepository.clearAutoIncrement();

    // 모든 데이터 삭제
    questionRepository.deleteAll();

    // 흔적삭제(다음번 INSERT 때 id가 1번으로 설정되도록)
    questionRepository.clearAutoIncrement();
    
    // 질문 두개 생성
    Question q1 = new Question();
    q1.setSubject("sbb가 무엇인가요?");
    q1.setContent("sbb에 대해서 알고 싶습니다.");
    q1.setCreateDate(LocalDateTime.now());
    questionRepository.save(q1);  // 첫번째 질문 저장

    Question q2 = new Question();
    q2.setSubject("스프링부트 모델 질문입니다.");
    q2.setContent("id는 자동으로 생성되나요?");
    q2.setCreateDate(LocalDateTime.now());
    questionRepository.save(q2);  // 두번째 질문 저장
    
    // 답변 1개 생성하기
    Answer a1 = new Answer();
    a1.setContent("네 자동으로 생성됩니다.");
    a1.setQuestion(q2);
    a1.setCreateDate(LocalDateTime.now());
    answerRepository.save(a1);

    q2.getAnswerList().add(a1); // 조금 더 객체 지향적으로 변함
  }

  @Test
// @DisplayName : 테스트의 의도를 사람이 읽기 쉬운 설명 형태로 표시
  @DisplayName("데이터 저장하기")
  void t001() {
    Question q = new Question();
    q.setSubject("겨울 제철 음식으로는 무엇을 먹어야 하나요?");
    q.setContent("겨울 제철 음식 알려주세요.");
    q.setCreateDate(LocalDateTime.now());
    questionRepository.save(q);  // 두번째 질문 저장

    assertEquals("겨울 제철 음식으로는 무엇을 먹어야 하나요?", questionRepository.findById(3).get().getSubject());
  }

  /*
  SQL
  SELECT * FROM question;
  */
  @Test
  @DisplayName("findAll")
  void t002() {
    List<Question> all = questionRepository.findAll();
    assertEquals(2, all.size());

    Question q = all.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }

  /*
  SQL
  SELECT *
  FROM question
  WHERE id = 1;
  */
  @Test
  @DisplayName("findById")
  void t003() {
    Optional<Question> oq = questionRepository.findById(1);

    if (oq.isPresent()) { // isPresent : 값의 존재를 확인
      Question q = oq.get();
      assertEquals("sbb가 무엇인가요?", q.getSubject());
    }
  }

  /*
  SQL
  SELECT *
  FROM question
  WHERE subject = 'sbb가 무엇인가요?';
  */
  @Test
  @DisplayName("findBySubject")
  void t004() {
    Question q = questionRepository.findBySubject("sbb가 무엇인가요?");

    assertEquals(1, q.getId());
  }

  /*
  SQL
  SELECT *
  FROM question
  WHERE subject = 'sbb가 무엇인가요?';
  */
  @Test
  @DisplayName("findBySubjectAndContent")
  void t005() {
    Question q = questionRepository.findBySubjectAndContent(
        "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
    assertEquals(1, q.getId());
  }

  /*
  SQL
  SELECT *
  FROM question
  WHERE subject LIKE 'sbb%';
  */
  @Test
  @DisplayName("findBySubjectLike")
  void t006() {
    List<Question> qList = questionRepository.findBySubjectLike("sbb%");
    Question q = qList.get(0);
    assertEquals("sbb가 무엇인가요?", q.getSubject());
  }

  /*
  UPDATE question
  SET content = ?,
  create_date = ?,
  subject = ?
  WHERE id = ?
  */
  @Test
  @DisplayName("데이터 수정하기")
  void t007() {
    Optional<Question> oq = questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    q.setSubject("수정된 제목");
    questionRepository.save(q);
  }


  /*
  DELETE
  FROM question
  WHERE id = ?
  */
  @Test
  @DisplayName("데이터 삭제하기")
  void t008() {
    // questionRepository.count()
    // SQL : SELECT COUNT(*) FROM question;
    assertEquals(2, questionRepository.count());
    Optional<Question> oq = questionRepository.findById(1);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    questionRepository.delete(q);
    assertEquals(1, questionRepository.count());
  }

  /*
  특정 질문 가져오기
  SELECT *
  FROM question AS q1
  WHERE q1.id=?
  */

  /*
  // 특정 질문에 대한 답변 데이터 생성
  INSERT INTO answer
  SET content = ?,
  create_date = ?,
  question_id = ?;
  */
  @Test
  @DisplayName("답변 데이터 생성 후 저장")
  void t009() {
    Optional<Question> oq = questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();

    /*
    // v1
    Optional<Question> oq = questionRepository.findById(2);
    Question q = oq.get();
    */

    /*
    // v2
    Question q = questionRepository.findById(2).get();
    */

    Answer a = new Answer();
    a.setContent("네 자동으로 생성됩니다.");
    a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
    a.setCreateDate(LocalDateTime.now());
    answerRepository.save(a);
  }

  @Test
  @DisplayName("답변 조회하기")
  void t010() {
    Optional<Answer> oa = answerRepository.findById(1L);
    assertTrue(oa.isPresent());
    Answer a = oa.get();
    assertEquals(2, a.getQuestion().getId());
  }
}

