package com.shop.service;

import com.shop.domain.Product;

import java.util.List;

public interface ProductService {
    public int save(Product item);
    public List<Product> productList();      //상품 전체
    public Product getproduct(Long pno);    //상품 상세
    public Product getLatestproduct();      //최신 상품



    public void addProduct(Product product);
    public void updateProduct(Product product);
    public void delProduct(Long pno);


}
