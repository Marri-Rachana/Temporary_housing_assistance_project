package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.housebooking.app.model.AddressModel;

public interface AddressRepo extends JpaRepository<AddressModel, Long>{


    @Query( value = "select * from addresses where profile_id = :id and house_id = 0", nativeQuery = true)
    AddressModel findUserAddress(@Param("id") Long id);


    @Query( value = "delete from addresses where profile_id = :id", nativeQuery = true)
    void deleteByUserProfile(@Param("id") Long id);



    @Query( value = "select * from addresses where house_id = :id", nativeQuery = true)
    AddressModel findHouseAddress(@Param("id") Long id);




}
