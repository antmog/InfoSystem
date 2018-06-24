package com.infosystem.springmvc.service.security;

import com.infosystem.springmvc.dto.SearchByNumberDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.jms.SmsMessageSender;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.service.UserService;
import org.apache.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.Json;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service("smsService")
@Transactional
public class SmsService {

    private static final Logger logger = Logger.getLogger(SmsService.class);
    private final SmsMessageSender smsMessageSender;
    private final UserService userService;

    @Autowired
    public SmsService(SmsMessageSender smsMessageSender, UserService userService) {
        this.smsMessageSender = smsMessageSender;
        this.userService = userService;
    }

    public void sendNewPassword(String phoneNumber) {
        String newPassword = "newpassword";
        try {
            User user = userService.findById(userService.findByPhoneNumber(new SearchByNumberDto(phoneNumber)));
            user.setPassword(newPassword);
            userService.saveUser(user);
        } catch (DatabaseException | LogicException e) {
            logger.error("Smb is trying to get new password for non-existing phone number.");
        }
        ResteasyWebTarget target = new ResteasyClientBuilder().build().target("http://188.243.228.19:8080/sendSms");
        Response response = target.request()
                .post(Entity.entity(genereateMessageForJms(phoneNumber,newPassword,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime())),
                        "application/json"));
        logger.info("Sent new password to sms server.");
        response.close();
        //smsMessageSender.sendMessage(genereateMessageForJms(phoneNumber, newPassword));
    }

    private String genereateMessageForJms(String phoneNumber, String newPassword, String dateTime) {
        String message = Json.createObjectBuilder()
                .add("phoneNumber", phoneNumber)
                .add("newPassword", newPassword)
                .add("dateTime", dateTime)
                .build()
                .toString();
        return message;
    }
}
