package com.learn.learningproject.commonservices;

import com.learn.learningproject.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/*
created by Rahul sawaria on 22/05/21
*/
@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private Environment environment;

    private static Session session;

    @PostConstruct
    public void emailConfig() {

        Properties prop = System.getProperties();

        prop.put(Constant.MAIL_STARTTLS_ENABLE, environment.getRequiredProperty(Constant.MAIL_STARTTLS_ENABLE));
        prop.put(Constant.MAIL_SMTP_PORT, environment.getRequiredProperty(Constant.MAIL_SMTP_PORT));//port of smtp server,need to change acc to smtp server
        prop.put(Constant.MAIL_SMTP_AUTH, environment.getRequiredProperty(Constant.MAIL_SMTP_AUTH));
        prop.put(Constant.MAIL_SMTP_HOST, environment.getRequiredProperty(Constant.MAIL_SMTP_HOST));// this need to change acc to smtp server
        prop.put(Constant.MAIL_SSL_PROTOCOL, environment.getRequiredProperty(Constant.MAIL_SSL_PROTOCOL));
        prop.put(Constant.MAIL_DEBUG, environment.getRequiredProperty(Constant.MAIL_DEBUG)); //used to debug the mail logs

        session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(environment.getRequiredProperty(Constant.MAIL_SESSION_USER),
                        environment.getRequiredProperty(Constant.MAIL_SESSION_PASSWORD));
            }
        });
    }

    @Async
    public void sendEmail(String[] recipientList, String emailSubject, String msgBody, String contentType, File attachmentFile, String fileName) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getRequiredProperty(Constant.MAIL_FROM)));
            message.setSubject(emailSubject);

            for (String recipient : recipientList) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msgBody, contentType);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            //check if there is any attachment need to send with mail
            if ((attachmentFile != null) && attachmentFile.exists() && (attachmentFile.length() != 0)) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.attachFile(attachmentFile);
                if (fileName != null)
                    attachmentBodyPart.setFileName(fileName);
                multipart.addBodyPart(attachmentBodyPart);
            }

            message.setContent(multipart);
            send(message);

        } catch (Exception e) {
            logger.error("Some Exception occurred while generating email structure..");
            e.printStackTrace();
        }
    }


    public void send(Message message) {
        try {
            Transport.send(message);
            logger.info("Email send successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during sending email..");
            e.printStackTrace();
        }
    }

}
