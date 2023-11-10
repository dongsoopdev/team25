package com.shop.service;

import com.shop.domain.Product;
import com.shop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public int save(Product product) {
        return  productMapper.save(product);
    }

    @Override
    public List<Product> productList() {
        return productMapper.productList();
    }

    @Override
    public Product getproduct(Long pno) {
        return productMapper.getproduct(pno);
    }



    @Override
    public void addProduct(Product product) {
        productMapper.addProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void delProduct(Long pno) {
        productMapper.delProduct(pno);
    }

    @Override
    public Product getLatestproduct() {
        return productMapper.getLatestproduct();
    }


}
