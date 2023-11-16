CREATE DATABASE tsherpa;

USE tsherpa;

-- 승원 
-- 부여할 권한 테이블
CREATE TABLE role(
	roleId INT PRIMARY KEY AUTO_INCREMENT,
	roleName VARCHAR(255) DEFAULT NULL -- 'USER' / 'TEACHER' / 'ADMIN etc'
);

-- role 더미
INSERT INTO role VALUES (DEFAULT, 'ADMIN'); -- 1
INSERT INTO role VALUES (DEFAULT, 'TEACHER'); -- 2
INSERT INTO role VALUES (DEFAULT, 'STAFF'); -- 3
INSERT INTO role VALUES (DEFAULT, 'MANAGER'); -- 4
INSERT INTO role VALUES (DEFAULT, 'USER'); -- 5

-- 회원 테이블
CREATE TABLE user(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 고유번호
	userId VARCHAR(255) NOT NULL, 	        -- 로그인아이디
	userName VARCHAR(255) NOT NULL,          -- 이름
	password VARCHAR(300) NOT NULL,          -- 비밀번호
	active VARCHAR(20)DEFAULT 'JOIN', -- JOIN(활동 중) / DORMANT(휴면 중) / WITHDRAW(탈퇴)
	email VARCHAR(100) NOT NULL,
	address VARCHAR(300),
	tel VARCHAR(20),
	POINT INT DEFAULT 0,
	regdate DATETIME DEFAULT CURRENT_TIME,
	CONSTRAINT key_name UNIQUE(userId)
);
-- 권한 부여된 회원 정보 저장 테이블
CREATE TABLE userRole(
	id bigINT NOT null,
	roleId INT NOT NULL,
	PRIMARY KEY(id,roleId)	
);


-- user 더미
INSERT INTO user VALUES (DEFAULT, 'admin', '관리자','1234', DEFAULT,'admin@edu.co.kr','서울특별시 구로구','010-0000-0000', DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'kim', '김기태','1234', DEFAULT,'kim@edu.co.kr','서울특별시 구로구', '010-1111-1111', DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'ku', '구예진','1234', DEFAULT, 'ku@edu.co.kr','서울특별시 구로구','010-2222-2222',DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'lee','이슬비','1234', DEFAULT, 'lee@edu.co.kr','서울특별시 구로구','010-3333-3333',DEFAULT, DEFAULT);	
INSERT INTO user VALUES (DEFAULT, 'shin', '신승원','1234', DEFAULT, 'shin@edu.co.kr','서울특별시 구로구','010-4444-4444',DEFAULT, DEFAULT);
INSERT INTO user VALUES (DEFAULT, 'so', '이소윤','1234', DEFAULT, 'so@edu.co.kr','서울특별시 구로구','010-5555-5555', DEFAULT, DEFAULT);

UPDATE user SET password='$2a$10$AmGZdqMKiNhpxtCd/z.tyuYL2r5rUmBCeFzzn4xZrwDYWHePyYiEa';

-- userrole 더미
INSERT INTO userrole VALUES(1,1);
INSERT INTO userrole VALUES(2,2);
INSERT INTO userrole VALUES(3,5);
INSERT INTO userrole VALUES(4,5);
INSERT INTO userrole VALUES(5,5);
INSERT INTO userrole VALUES(6,5);


-- 예진 
CREATE TABLE product(
	pno BIGINT PRIMARY KEY AUTO_INCREMENT,  #상품고유번호
	cateno BIGINT,                         #카테고리번호
	pname VARCHAR(100) NOT NULL,         #상품명
	pcomment VARCHAR(2000),              #상품설명
	price INT DEFAULT 1000,              #상품가격	
	seller VARCHAR(255), 						 #판매자
	quantity INT DEFAULT 1,						# 상품수량
	quality VARCHAR(20),		    				 #최상 / 상 / 중 / 중하 /최하
	status VARCHAR(20) DEFAULT '판매중',    # 판매 중 / 예약 중 / 판매완료
	imgsrc1 VARCHAR(300),                   #상품이미지 (썸네일)
	imgsrc2 VARCHAR(300), 
	imgsrc3 VARCHAR(300), 
	imgsrc4 VARCHAR(300), 
	resdate timestamp DEFAULT CURRENT_TIMESTAMP(),       #상품등록일
	FOREIGN KEY(seller) REFERENCES user(userId) ON DELETE CASCADE
	-- FOREIGN KEY(cateno) REFERENCES category(cateno) ON DELETE CASCADE -- cateno를 category테이블의 cateno를 이용해 외래키로 사용
);

INSERT INTO product VALUES(DEFAULT, 1, '도유니 서적','도유니 서적에 대한 설명이오~!', 12000, 'lee',DEFAULT, '최상', 'img1.jpg' ,'img2.jpg','img3.jpg','img4.jpg','dddd', DEFAULT);


-- 슬비
SELECT * FROM product;
CREATE TABLE chatRoom (
	roomId BIGINT PRIMARY KEY AUTO_INCREMENT,
	userId VARCHAR(20) NOT NULL,		-- 구매자
	pno INT NOT NULL,						-- 상품번호
	status VARCHAR(50) DEFAULT 'ON', -- ON(진행), OFF(차단)
	UNIQUE (userId, pno)					-- userId와 pno 묶기
);
CREATE TABLE chatMsg (
	chatId BIGINT PRIMARY KEY AUTO_INCREMENT,
	type VARCHAR(20) NOT NULL, 							-- 채팅 타입: ENTER, TALK, LEAVE, NOTICE
	roomId BIGINT NOT NULL, 									-- 채팅방 번호
	sender VARCHAR(20) NOT NULL,							-- 송신자
	msg VARCHAR(2000) NOT NULL,						-- 채팅메세지
	status VARCHAR(50) DEFAULT 'UNREAD', 				-- 읽음 여부
	chatDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP		-- 발송시간
);

-- 소윤 시작
-- 결제 테이블 생성 pay
CREATE TABLE pay (
	payNo BIGINT PRIMARY KEY AUTO_INCREMENT, -- 결제 코드
	userId VARCHAR(255), -- 구매자
	pno BIGINT, -- 구매한 상품
	price INT, -- 결제 가격
	username VARCHAR(255), -- 구매자명
	email VARCHAR(100) , -- 전화번호
	address VARCHAR(300), -- 이메일
	tel VARCHAR(20), -- 배송지
	ship INT DEFAULT 1, -- 배송 현황  -  1: 배송 전 2: 배송 중 3: 배송완료 4: 거래종료
	scode VARCHAR(100), -- 운송장 정보
	sname VARCHAR(20), -- 회사 정보
	resdate timestamp DEFAULT CURRENT_TIMESTAMP() -- 구매일 
);