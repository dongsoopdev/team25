CREATE DATABASE tsherpa;

USE tsherpa;

CREATE TABLE role(
	role_id INT PRIMARY KEY AUTO_INCREMENT,
	role VARCHAR(255) DEFAULT NULL -- 'USER' / 'TEACHER' / 'ADMIN'
);
DESC role;
SELECT * FROM role;

-- DROP TABLE USER;
CREATE TABLE user(
	user_id INT PRIMARY KEY AUTO_INCREMENT,  -- 고유번호
	login_id VARCHAR(255) NOT NULL, 	        -- 로그인아이디
	username VARCHAR(255) NOT NULL,          -- 이름
	password VARCHAR(300) NOT NULL,          -- 비밀번호
	active VARCHAR(20)DEFAULT 'JOIN', -- JOIN(활동 중) / DORMANT(휴면 중) / WITHDRAW(탈퇴)
	email VARCHAR(100) NOT NULL,
	address VARCHAR(300),
	tel VARCHAR(20),
	regdate DATETIME DEFAULT CURRENT_TIME
	CONSTRAINT key_name UNIQUE(login_id)
);
DESC user;
SELECT * FROM user;

CREATE TABLE user_role(
	user_id INT NOT null,
	role_id INT NOT null,
	PRIMARY KEY(user_id,role_id)	
);
DESC user_role;
SELECT * FROM user_role;





UPDATE user SET password='$2a$10$AmGZdqMKiNhpxtCd/z.tyuYL2r5rUmBCeFzzn4xZrwDYWHePyYiEa'; 







-- 게시판
-- drop table board;
CREATE TABLE board(
	bno INT PRIMARY KEY AUTO_INCREMENT, -- qna 글 번호
	title VARCHAR(200) NOT NULL, -- 제목
	content VARCHAR(1000), -- 내용
	author VARCHAR(16), -- 작성자
	resdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP(), -- 작성일
	cnt INT DEFAULT 0, -- 조회수
	lev INT DEFAULT 0, -- 게시글 0, 답글 1 이상
	par INT, -- 부모 게시글 번호
	FOREIGN KEY(author) REFERENCES euser(name) ON DELETE 		
		CASCADE -- 작성자를 member id를 이용해 외래키로 사용
);

DESC board;
SELECT * FROM board;

INSERT INTO board(title, content, author) VALUES('본문 제목1', '본문 내용1', 'admin');
UPDATE board SET par=bno WHERE bno=1;

INSERT INTO board(title, content, author) VALUES('본문 제목2', '본문 내용2', 'ku'); 
UPDATE board SET par=bno WHERE bno=2;

INSERT INTO board(title, content, author) VALUES('본문 제목3', '본문 내용3', 'so');
UPDATE board SET par=bno WHERE bno=3;

INSERT INTO board(title, content, author) VALUES('본문 제목4', '본문 내용4', 'shin');
UPDATE board SET par=bno WHERE bno=4;

INSERT INTO board(title, content, author) VALUES('본문 제목5', '본문 내용5', 'lee');
UPDATE board SET par=bno WHERE bno=5;

INSERT INTO board(title, content, author) VALUES('본문 제목6', '본문 내용6', 'user');
UPDATE board SET par=bno WHERE bno=6; 

INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 7);
INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 7);
INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 6);
INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 5);
INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 4);
INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 3);
INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 2);
INSERT INTO board(title, content, author, lev, par) VALUES('댓글', '댓글내용', 'admin', 1, 1);

select * from board where par = 7 and lev = 1 order by resdate DESC;



-- QnA
-- DROP TABLE qna;
CREATE TABLE qna(
	
);
DESC qna;
SELECT * FROM qna;

-- FAQ
-- DROP TABLE faq;
CREATE TABLE faq(
	
);
DESC faq;
SELECT * FROM faq;

-- Review
-- DROP TABLE review;
CREATE TABLE review(
	
);
DESC review;
SELECT * FROM review;



-- 카테고리
-- drop table category;
CREATE TABLE category(
	cateno INT PRIMARY KEY AUTO_INCREMENT,  --카테고리고유번호
	catename VARCHAR(100) NOT NULL,         --카테고리명
);
DESC category;
SELECT * FROM category;



-- 상품
-- drop table product;
CREATE TABLE product(
	pno INT PRIMARY KEY AUTO_INCREMENT,  --상품고유번호
	cateno INT PRIMARY KEY AUTO_INCREMENT,  --카테고리번호
	pname VARCHAR(100) NOT NULL,         --상품명
	pcomment VARCHAR(2000) NOT NULL,     --상품설명
	price INT DEFAULT 1000,              --상품가격
	imgsrc1 VARCHAR(300),                --상품이미지 (썸네일)
	imgsrc2 VARCHAR(300),                --상품이미지 (상품상세이미지)
	imgsrc3 VARCHAR(300),                --상품이미지 (임시이미지)
	resdate timestamp DEFAULT CURRENT_TIMESTAMP()       --상품등록일
	FOREIGN KEY(cateno) REFERENCES category(cateno) ON DELETE CASCADE -- cateno를 category테이블의 cateno를 이용해 외래키로 사용
);
DESC product;
SELECT * FROM product;




-- 장바구니
-- DROP TABLE cart;
CREATE TABLE cart(
	
);
DESC cart;
SELECT * FROM cart;



-- 좋아요
-- DROP TABLE like;
CREATE TABLE like(
	
);
DESC like;
SELECT * FROM like;



-- 주문
-- DROP TABLE order;
CREATE TABLE order(
	
);
DESC order;
SELECT * FROM order;



-- 결제
-- DROP TABLE payment;
CREATE TABLE payment(
	
);
DESC payment;
SELECT * FROM payment;


-- 배송
-- DROP TABLE delivery;
CREATE TABLE delivery(
	
);
DESC delivery;
SELECT * FROM delivery;


