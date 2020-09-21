package by.chernyavski;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class MailService {
    public static void main(String[] args) {

        String receiverAddress = "xxx";
        String subject = "subject";
        String message = "message";
        String senderAddress = "xxx@gmail.com";
        String senderPassword = "password";

        send(senderAddress, senderPassword, receiverAddress, subject, message);
    }

    public static void send(final String senderAddress, final String senderPassword, String receiverAddress,
                            String subject, String message){
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/main/resources/application.properties"));

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderAddress, senderPassword);
                        }
                    });

            Transport transport = session.getTransport();
            InternetAddress addressFrom = new InternetAddress(senderAddress);
            InternetAddress addressTo = new InternetAddress(receiverAddress);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(addressFrom);
            msg.setSubject(subject);
            msg.setContent(message, "text/plain");
            msg.addRecipient(Message.RecipientType.TO, addressTo);

            transport.connect();
            Transport.send(msg);

            System.out.println("Sent message successfully....");
            transport.close();
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

}
