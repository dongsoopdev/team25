package com.shop.service;

import com.shop.domain.Product;
import com.shop.domain.ProductFile;
import com.shop.domain.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ReviewService {


    public void insertReview(Review review);

    public Review getProReview(Review review);

    public List<Review> proReview(String userId);

}

