package andrew.projects.workard.Service;

import andrew.projects.workard.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSender {
    public static final String SUBJECT = "Workard Activation Code";
    public static final String MSG_TEXT = "Hello! Click here to activate your account  http://localhost:8080/activate/";
    @Autowired
    private JavaMailSender mailSender;

    public void sendValidationCode(User a) {
        sendEmail(a.getEmail(), SUBJECT, MSG_TEXT + a.getEmailConfirmation());
    }

    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);

        msg.setSubject(subject);
        msg.setText(text);
        new Thread(new asyncSendSimpleMessage(msg)).start();
    }

    class asyncSendSimpleMessage implements Runnable {
        SimpleMailMessage sm;

        public asyncSendSimpleMessage(SimpleMailMessage msg) {

            this.sm = msg;
        }

        public void run() {
            mailSender.send(sm);
        }

    }

}