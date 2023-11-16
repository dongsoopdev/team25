package com.shop.mapper;

import com.shop.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {

    void save(Product item);
    List<Product> findAll();      //상품 전체
    Product getProduct(Long pno);    //상품 상세
    List<Product> findByUserId(String seller);
    Product getLatestproduct();      //최신 상품

    void addProduct(Product product);
    void updateProduct(Product product);
    void delProduct(Long pno);
    void updateStatus(Map<String, Object> paramMap);
}
