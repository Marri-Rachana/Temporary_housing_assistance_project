package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.housebooking.app.model.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Long>{

}
