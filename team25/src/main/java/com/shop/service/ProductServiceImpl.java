package com.shop.service;

import com.shop.domain.Product;
import com.shop.domain.ProductFile;
import com.shop.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;



    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    @Override
    public Product getProduct(Long pno) {
        return productMapper.getProduct(pno);
    }

    @Override
    public List<Product> findByUserId(String seller) {
        return productMapper.findByUserId(seller);
    }


    @Override
    public void save(Product product) {
        productMapper.save(product);
    }


    public void saveProduct(Product product, MultipartFile[] imgFiles) throws IOException {
        // static은 정적폴더라서 늦게 업로드됨
        //String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/upload/";
        String projectPath = "D:/team25_upload/";

        for (int i = 0; i < imgFiles.length; i++) {
            MultipartFile imgFile = imgFiles[i];
            String oriImgName = imgFile.getOriginalFilename();
            String imgName = "";

            // UUID 를 이용하여 파일명 새로 생성
            UUID uuid = UUID.randomUUID();
            String savedFileName = uuid + "_" + oriImgName;
            imgName = savedFileName;

            File saveFile = new File(projectPath, imgName);
            imgFile.transferTo(saveFile);

            // 각 이미지에 대한 처리 (imgsrc1, imgsrc2, imgsrc3, imgsrc4)
            switch (i + 1) {
                case 1:
                    product.setImgsrc1(imgName);
                    break;
                case 2:
                    product.setImgsrc2(imgName);
                    break;
                case 3:
                    product.setImgsrc3(imgName);
                    break;
                case 4:
                    product.setImgsrc4(imgName);
                    break;
                // 추가적인 이미지 필요에 따라 계속해서 확장 가능
                // ...
            }
        }

        productMapper.save(product);
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
    public void setproductFile(ProductFile productFile) {

    }


    @Override
    public Product getLatestproduct() {
        return productMapper.getLatestproduct();
    }








}
