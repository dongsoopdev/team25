package com.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long pno;
    private Long cateno;
    private String pname;
    private String pcomment;
    private int price;
    private String seller;  //작성자
    private String quality;
    private String quantity;
    private String status;    // 판매 중 / 예약 중 / 판매완료
    private String imgsrc1;
    private String imgsrc2;
    private String imgsrc3;
    private String imgsrc4;
   // private String imgPath;   // 이미지 조회 경로
    private String resdate;   //등록일



}


