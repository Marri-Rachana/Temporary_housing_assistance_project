package com.housebooking.app.dao;

import com.housebooking.app.model.ReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReportRepo extends JpaRepository<ReportModel, Long> {

    @Query( value = "select * from reports where user_type = 'student'", nativeQuery = true)
    List<ReportModel> findAllByStudentType();

    @Query( value = "select * from reports where user_type = 'houseowner'", nativeQuery = true)
    List<ReportModel> findAllByOwnerType();

	@Query( value = "select * from reports where id = :id", nativeQuery = true)
	ReportModel findReportById(@Param("id") Long id);

}
