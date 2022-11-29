package com.housebooking.app.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.housebooking.app.dao.AnnouncementRepo;
import com.housebooking.app.model.Announcement;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AdminControllerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AnnouncementRepo announcementRepo;


    @Test
    public void shouldPostAnnouncements() {

        Announcement announcement = new Announcement();

        announcement.setAnnouncementDescription("Greater community apartments opening this week");
        announcement.setAnnouncementTitle("Invitation for Apartment opening");
        announcement.setStartDate("2022-06-20");
        announcement.setStartTime("10:00");

        List<Announcement> announcementdetails = announcementRepo.findAll();

        assertThat(announcementdetails).isEmpty();

        announcementRepo.save(announcement);

        List<Announcement> announcements = announcementRepo.findAll();

        assertThat(announcements).hasSize(1);

    }



}

