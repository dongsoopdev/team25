USE tsherpa;

-- DROP TABLE role;
CREATE TABLE role(
	id INT PRIMARY KEY AUTO_INCREMENT,
	roleName VARCHAR(20) DEFAULT NULL -- 'USER' / 'TEACHER' / 'ADMIN'
);
DESC role;
SELECT * FROM role;

INSERT INTO role VALUES(DEFAULT, 'ADMIN');
INSERT INTO role VALUES(DEFAULT, 'TEACHER');
INSERT INTO role VALUES(DEFAULT, 'MANAGER');
INSERT INTO role VALUES(DEFAULT, 'STAFF');
INSERT INTO role VALUES(DEFAULT, 'USER');


DROP TABLE USER;
CREATE TABLE user(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,   -- 고유번호
	userId VARCHAR(255) NOT NULL, 	        -- 로그인아이디
	pwd VARCHAR(300) NOT NULL,             -- 비밀번호
	userName VARCHAR(255) NOT NULL,               -- 이름
	email VARCHAR(100) NOT NULL,
	address VARCHAR(300),
	tel VARCHAR(20),
	regdate DATETIME DEFAULT CURRENT_TIME,
	active VARCHAR(20)DEFAULT 'JOIN',    -- JOIN(활동 중) / DORMANT(휴면 중) / WITHDRAW(탈퇴)
	UNIQUE KEY uk_name(userId,email)
);
DESC user;
SELECT * FROM user;

-- DROP TABLE userRole;
CREATE TABLE userRole(
	userId BIGINT,
	roleId INT
);
DESC userRole;
SELECT * FROM userRole;


INSERT INTO user VALUES (DEFAULT, 'admin', 1234, '관리자','admin@edu.co.kr','서울특별시 구로구','010-0000-0000', DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'kim', 1234, '김기태','kim@edu.co.kr','서울특별시 구로구', '010-1111-1111', DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'ku', 1234, '구예진','ku@edu.co.kr','서울특별시 구로구','010-2222-2222',DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'lee', 1234, '이슬비','lee@edu.co.kr','서울특별시 구로구','010-3333-3333',DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'shin', 1234, '신승원','shin@edu.co.kr','서울특별시 구로구','010-4444-4444',DEFAULT, DEFAULT);
INSERT INTO user VALUES (DEFAULT, 'so', 1234, '이소윤','so@edu.co.kr','서울특별시 구로구','010-5555-5555', DEFAULT, DEFAULT);		


UPDATE user SET pwd='$2a$10$AmGZdqMKiNhpxtCd/z.tyuYL2r5rUmBCeFzzn4xZrwDYWHePyYiEa'; 



SELECT a.id, a.userName, b.roleId FROM user a INNER JOIN userRole b ON a.id=b.userId WHERE a.id=1;
UPDATE userRole SET roleId=1 WHERE userId=1;



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
	cateno BIGINT PRIMARY KEY AUTO_INCREMENT,  --카테고리고유번호
	catename VARCHAR(100) NOT NULL,         --카테고리명
);
DESC category;
SELECT * FROM category;



-- 상품
-- drop table product;
CREATE TABLE product(
	pno BIGINT PRIMARY KEY AUTO_INCREMENT,  #상품고유번호
	cateno BIGINT PRIMARY KEY AUTO_INCREMENT,  #카테고리번호
	pname VARCHAR(100) NOT NULL,         #상품명
	pcomment VARCHAR(2000) 				    #상품설명
	price INT DEFAULT 1000,              #상품가격	
	quality VARCHAR(20),					 	 #최상 / 상 / 중 / 중하 /최하
	imgsrc1 VARCHAR(300),                #상품이미지 (썸네일)
	imgsrc2 VARCHAR(300),                #상품이미지 (상품상세이미지)
	imgsrc3 VARCHAR(300),                #상품이미지 (임시이미지)
	resdate timestamp DEFAULT CURRENT_TIMESTAMP()       #상품등록일
	FOREIGN KEY(cateno) REFERENCES category(cateno) ON DELETE CASCADE -- cateno를 category테이블의 cateno를 이용해 외래키로 사용
);
DESC product;
SELECT * FROM product;





-- 찜하기
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








-- 외래 키 체크를 비활성화
-- SET FOREIGN_KEY_CHECKS = 0;

-- 테이블 삭제
-- DROP TABLE lecture;

-- 외래 키 체크를 다시 활성화
 -- SET FOREIGN_KEY_CHECKS = 1;
