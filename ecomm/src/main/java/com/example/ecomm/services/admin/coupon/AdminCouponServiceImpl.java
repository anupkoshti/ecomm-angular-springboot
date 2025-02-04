package com.example.ecomm.services.admin.coupon;

import com.example.ecomm.entity.Coupon;
import com.example.ecomm.exceptions.ValidationException;
import com.example.ecomm.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCouponServiceImpl implements AdminCouponService {

    @Autowired
    CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(Coupon coupon){
        if(couponRepository.existsByCode(coupon.getCode())) {
            throw new ValidationException("Coupon code already exists");
        }
        else {
            return couponRepository.save(coupon);
        }

    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }


}
