package com.sizonenko.bicycleapp.api.mail;

import com.sizonenko.bicycleapp.entity.Confirm;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailSender {

    private static final String PATH_MAIL_PROPERTIES = "/mail_properties/mail";

    public void send(Confirm confirm, String email) {
        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream(PATH_MAIL_PROPERTIES + ".properties");
            Properties props = new Properties();
            props.load(input);
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(props.getProperty("mail.user.name"), props.getProperty("mail.user.password"));
                        }
                    });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(props.getProperty("mail.user.from")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Activate Account");
            String msg = "Your Login: <h3>" + confirm.getLogin() + "</h3><br>" +
                    "<form action=\"http://localhost:8080/InvokerServlet?\" method=\"GET\">"+
                    "  <input type=\"hidden\" name=\"command\" value=\"confirm\" /> "+
                    "  <input type=\"hidden\" name=\"login\" value=\""+confirm.getLogin()+"\" /> "+
                    "  <input type=\"hidden\" name=\"code\" value=\""+confirm.getCode()+"\" /> "+
                    "  <input type=\"submit\" value=\"Activate\" /> "+
                    "</form>";
            message.setContent(msg, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
