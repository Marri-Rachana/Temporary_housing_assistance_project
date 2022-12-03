package com.housebooking.app.communication;

import org.springframework.mail.SimpleMailMessage;

public interface Communication{

    public int sendSimpleMail(SimpleMailMessage mailMessage);
}