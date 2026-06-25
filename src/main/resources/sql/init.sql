-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS board DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE board;

-- 회원 테이블
CREATE TABLE IF NOT EXISTS member (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    member_id VARCHAR(50)  NOT NULL UNIQUE COMMENT '로그인 아이디',
    password  VARCHAR(255) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    nickname  VARCHAR(50)  NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 게시글 테이블
CREATE TABLE IF NOT EXISTS board (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(200) NOT NULL,
    content    TEXT         NOT NULL,
    member_id  INT          NOT NULL,
    view_count INT          DEFAULT 0,
    created_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member (id)
);

-- 댓글 테이블
CREATE TABLE IF NOT EXISTS comment (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    board_id   INT       NOT NULL,
    member_id  INT       NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (board_id)  REFERENCES board (id)  ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member (id)
);
