package com.example.ecomm.services.admin.coupon;

import com.example.ecomm.entity.Coupon;

import java.util.List;

public interface AdminCouponService {
    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
