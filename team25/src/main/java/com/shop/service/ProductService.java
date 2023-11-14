package com.shop.service;

import com.shop.domain.Product;
import com.shop.domain.ProductFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public void save(Product product);
    public List<Product> findAll();      //상품 전체
    public Product getProduct(Long pno);    //상품 상세
    public Product getLatestproduct();      //최신 상품



    public void addProduct(Product product);
    public void updateProduct(Product product);
    public void delProduct(Long pno);


    public void setproductFile(ProductFile productFile);


    public void saveProduct(Product product, MultipartFile[] imgFiles) throws IOException;

}
