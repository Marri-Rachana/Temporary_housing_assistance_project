package com.housebooking.app.dao;

import com.housebooking.app.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepo extends JpaRepository<Announcement, Long>{

}
