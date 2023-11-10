package com.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long pno;
    private Long cateno;
    private String pcomment;
    private int price;
    private int imgsrc1;
    private int imgsrc2;
    private int imgsrc3;
    private String resdate;
}



