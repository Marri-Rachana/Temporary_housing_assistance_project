package com.housebooking.app.service;

import com.housebooking.app.dao.MessageRepo;
import com.housebooking.app.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private MessageRepo messageRepo;

    public void saveMsg(MessageModel msg) {
        messageRepo.save(msg);

    }

}
