CREATE TABLE IF NOT EXISTS user
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '사용자 PK',
    name        VARCHAR(30) COMMENT '사용자 이름',
    password    VARCHAR(256) COMMENT '사용자 비밀번호 단방향 암호화',
    phonenumber VARCHAR(15) COMMENT '사용자 휴대폰 번호 양방향 암호화',
    email       VARCHAR(30) UNIQUE NOT NULL COMMENT '사용자 이메일 양방향 암호화'
) ENGINE = 'InnoDB';
