package be.kdg.trips.service.impl;

import be.kdg.trips.service.EmailHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by Matthias on 10/08/2015.
 */
@Service("emailHelper")
public class EmailHelperServiceImpl implements EmailHelperService {

    @Autowired
    private MailSender emailSender;

    public void sendEmail(String toAddress,  String subject, String msgBody){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toAddress);
        msg.setSubject(subject);
        msg.setText(msgBody);
        emailSender.send(msg);

    }
}
