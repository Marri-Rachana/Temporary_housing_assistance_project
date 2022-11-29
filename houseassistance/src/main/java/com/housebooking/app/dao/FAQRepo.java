package com.housebooking.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.housebooking.app.model.FAQModel;

@Repository
public interface FAQRepo extends JpaRepository<FAQModel, Long>{

    @Query( value = "select * from faqs where id = :id", nativeQuery = true)
	FAQModel findFAQById(@Param("id") Long id);

}
