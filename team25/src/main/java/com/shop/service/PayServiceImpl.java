package com.shop.service;

import com.shop.domain.Pay;
import com.shop.mapper.PayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService{
    @Autowired
    private PayMapper payMapper;

    @Override
    public int payInsert(Pay pay) {
        return payMapper.payInsert(pay);
    }

    @Override
    public Pay getPay(Long pno) {
        return payMapper.getPay(pno);
    }

    @Override
    public List<Pay> myPayListByUserId(String userId) {
        return payMapper.myPayListByUserId(userId);
    }

    @Override
    public void updateShip(int ship,Long payNo) {
        payMapper.updateShip(ship, payNo);
    }
}
