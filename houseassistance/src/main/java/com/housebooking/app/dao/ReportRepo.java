package com.housebooking.app.dao;

import com.housebooking.app.model.ReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepo extends JpaRepository<ReportModel, Long> {

}
