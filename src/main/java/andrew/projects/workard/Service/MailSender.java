package andrew.projects.workard.Service;

import andrew.projects.workard.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendValidationCode(User a) {
        sendEmail(a.getEmail(), "Workard Activation Code", "Hello! Click here to activate your account  http://localhost:8080/activate/" + a.getEmailConfirmation());
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


    //заготовка для письма с html
    class asyncSendHtmlMessage implements Runnable {
        MimeMessage mm;

        public asyncSendHtmlMessage(MimeMessage msg) {

            this.mm = msg;
        }

        public void run() {
            mailSender.send(mm);
        }

    }
    public void sendEmailWithHtml(final String to, final String subject, final String text) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            msg.setContent(text, "text/html; charset=UTF-8");
            msg.setText("");//сюда подгони html
            helper.setTo(to);
            helper.setSubject(subject);

            new Thread(new asyncSendHtmlMessage(msg)).start();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}