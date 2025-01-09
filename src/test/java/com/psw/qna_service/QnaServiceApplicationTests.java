package com.psw.qna_service;

import com.psw.qna_service.boundedContext.answer.Answer;
import com.psw.qna_service.boundedContext.answer.AnswerRepository;
import com.psw.qna_service.boundedContext.answer.AnswerService;
import com.psw.qna_service.boundedContext.question.Question;
import com.psw.qna_service.boundedContext.question.QuestionRepository;
import com.psw.qna_service.boundedContext.question.QuestionService;
import com.psw.qna_service.boundedContext.user.SiteUser;
import com.psw.qna_service.boundedContext.user.UserRepository;
import com.psw.qna_service.boundedContext.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QnaServiceApplicationTests {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private UserService userService;

  @Autowired
  private AnswerService answerService;

  @Autowired // 필드 주입
  private QuestionRepository questionRepository;

  @Autowired
  private AnswerRepository answerRepository;

  @Autowired
  private UserRepository userRepository;

  @BeforeEach // 아래 메서드는 각 테스트케이스가 실행되기 전에 실행
  void beforeEach() {
    
    // 답변 모든 데이터 삭제
    answerRepository.deleteAll();
    answerRepository.clearAutoIncrement();

    // 모든 데이터 삭제
    questionRepository.deleteAll();

    // 흔적삭제(다음번 INSERT 때 id가 1번으로 설정되도록)
    questionRepository.clearAutoIncrement();

    // 모든 데이터 삭제
    userRepository.deleteAll();
    userRepository.clearAutoIncrement();
    
    // 회원 2명 생성
    SiteUser user1 = userService.create("user1", "user1@test.com", "1234");
    SiteUser user2 = userService.create("user2", "user2@test.com", "1234");
    
    // 질문 두개 생성
    Question q1 = questionService.create("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.", user1);
    Question q2 = questionService.create("스프링부트 모델 질문입니다.", "id는 자동으로 생성되나요?", user2);
    
    // 답변 1개 생성하기
    Answer a1 = answerService.create(q2, "네 자동으로 생성됩니다.", user2);

    // 1번 질문에 2명의 회원이 추천을 한다.
    // user1 (이)가 q1 (을)를 추천했다.
    q1.addVoter(user1);
    q1.addVoter(user2);
    questionRepository.save(q1);

    q2.addVoter(user1);
    q2.addVoter(user2);
    questionRepository.save(q2);

    a1.addVoter(user1);
    a1.addVoter(user2);
    answerRepository.save(a1);
  }

  @Test
  // @DisplayName : 테스트의 의도를 사람이 읽기 쉬운 설명 형태로 표시
  @DisplayName("데이터 저장하기")
  void t001() {
    SiteUser user1 = userService.getUser("user1");

    // 질문 1개 생성
    Question q1 = questionService.create("겨울 제철 음식으로는 무엇을 먹어야 하나요?", "겨울 제철 음식 알려주세요.", user1);

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
  @Transactional
  @Rollback(false)
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

    SiteUser user2 = userService.getUser("user2");

    Answer a = answerService.create(q, "네 자동으로 생성됩니다.", user2);

    assertEquals("네 자동으로 생성됩니다.", a.getContent());
  }

  /*
  select A.*, Q.*
  FROM answer AS A
  LEFT JOIN question AS Q
  ON Q.id = A.question_id
  WHERE A.id = ?
  */
  @Test
  @DisplayName("답변 조회하기")
  void t010() {
    Optional<Answer> oa = answerRepository.findById(1);
    assertTrue(oa.isPresent());
    Answer a = oa.get();
    assertEquals(2, a.getQuestion().getId());
  }

  // 테스트 코드에서는 Transactional을 붙여줘야 한다.
  // findById 메서드 실행하고 나면 DB가 끝어지기 때문에
  // @Transactional 애너테이션을 사용하면 메서드가 종료될 때까지 DB 세션이 유지된다.
  @Transactional
  @Rollback(false)
  @Test
  @DisplayName("질문을 통해 답변 찾기")
  void t011() {
    // SELECT * FROM question WHERE id = 2;
    Optional<Question> oq = questionRepository.findById(2);
    assertTrue(oq.isPresent());
    Question q = oq.get();
    // 테스트 환경에서는 get해서 가져온 뒤 DB 연결을 끊음

    // SELECT * FROM answer WHERE question_id = 2;
    List<Answer> answerList = q.getAnswerList(); // DB 통신이 끊긴 뒤 answer를 가져옴 => 실패

    assertEquals(1, answerList.size());
    assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
  }

  @Test
  @DisplayName("검색, 질문 제목으로 검색")
  void t012() {
    Page<Question> searchResult = questionService.getList(0, "sbb가 무엇인가요?");

    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 질문 내용으로 검색")
  void t013() {
    Page<Question> searchResult = questionService.getList(0, "sbb에 대해서 알고 싶습니다.");

    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 질문자이름으로 검색")
  void t014() {
    Page<Question> searchResult = questionService.getList(0, "user1");
    assertEquals(2, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 답변내용으로 검색")
  void t015() {
    Page<Question> searchResult = questionService.getList(0, "네 자동으로 생성됩니다.");
    assertEquals(2, searchResult.getContent().get(0).getId());
    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("검색, 답변자이름으로 검색")
  void t016() {
    Page<Question> searchResult = questionService.getList(0, "user2");
    assertEquals(2, searchResult.getContent().get(0).getId());
    assertEquals(1, searchResult.getTotalElements());
  }

  @Test
  @DisplayName("대량의 테스트 데이터 만들기")
  void t999() {
    SiteUser user2 = userService.getUser("user2");

    IntStream.rangeClosed(3, 300)
        .forEach(
            no -> questionService.create(
                "테스트 제목입니다. %d".formatted(no), "테스트 내용입니다. %d".formatted(no),
                user2));
  }
}

