package com.housebooking.app.test.Services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.housebooking.app.service.*;
import com.housebooking.app.model.*;
import com.housebooking.app.dao.*;

@ExtendWith(MockitoExtension.class)
public class CommonServiceTest {

    @InjectMocks
    private CommonService commonService;

    @Mock
    private ReviewRepo reviewRepo;

    @Mock
    private TicketRepo ticketRepo;

    @Test
    public void AddReviewTest() {

        ReviewModel rm= new ReviewModel();
        rm.setId(9l);
        rm.setDescription("Super");
        rm.setRating("4");
        rm.setUserMail("yannapu@ilstu.edu");
        when(reviewRepo.save(rm)).thenReturn(rm);

        assertEquals(1,commonService.saveReview(rm));
    }

    @Test
    public void AddTicketTest(){
        TicketModel ticket=new TicketModel();
        ticket.setId(7l);
        ticket.setDescription("Application is not down");
        ticket.setUserMail("tester@gmail.com");
        when(ticketRepo.save(ticket)).thenReturn(ticket);

        assertEquals(1, commonService.saveTicket(ticket));
    }

    @Test
    public void NotAddReviewTest() {

        ReviewModel rm= new ReviewModel();
        rm.setId(9l);
        rm.setDescription("error");
        rm.setRating("1");
        rm.setUserMail("thceruk@ilstu.edu");
        when(reviewRepo.save(rm)).thenThrow(NullPointerException.class);

        assertEquals(0,commonService.saveReview(rm));
    }

    @Test
    public void NotAddTicketTest(){
        TicketModel ticket=new TicketModel();
        ticket.setId(16l);
        ticket.setDescription("Application down");
        ticket.setUserMail("error@gmail.com");
        when(ticketRepo.save(ticket)).thenThrow(NullPointerException.class);

        assertEquals(0, commonService.saveTicket(ticket));
    }

}
