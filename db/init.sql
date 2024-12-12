# DB 생성
DROP DATABASE IF EXISTS sbb_qna;
CREATE DATABASE sbb_qna;
USE sbb_qna

# 각 테이블 초기화(꼭 answer 먼저 삭제, 이유는 두 테이블간에 외래키가 걸려있기 때문)
# 외래키 제약 비활성화
SET FOREIGN_KEY_CHECKS = 0;

# 테이블 초기화
TRUNCATE answer;
TRUNCATE question;

# 외래키 제약 활성화
SET FOREIGN_KEY_CHECKS = 1;