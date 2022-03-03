package luadjotest.com.ua.service;

import freemarker.template.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String username;
    private final JavaMailSender mailSender;
    private final Configuration fmConfiguration;

    public MailService(JavaMailSender mailSender, Configuration fmConfiguration) {
        this.mailSender = mailSender;
        this.fmConfiguration = fmConfiguration;
    }

    public void sendTextMail(String mailTo, String subject, String text) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(username);
        mail.setTo(mailTo);
        mail.setSubject(subject);
        mail.setText(text);
        mailSender.send(mail);
    }

    public void sendMailWithTemplate(String mailTo, String subject, Model model, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(mailTo);
            mimeMessageHelper.setSubject(subject);
            Template template = fmConfiguration.getTemplate("emailOrder.ftlh");
            StringBuffer sb = new StringBuffer();
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            sb.append(text);
            sb.append(html);
            String result = sb.toString();
            mimeMessageHelper.setText(result, true);
            mailSender.send(mimeMessage);
            mimeMessageHelper.setFrom(username);
            mimeMessageHelper.setTo(username);
            mimeMessageHelper.setSubject("Новый заказ на сайте!");
            mimeMessageHelper.setText(html, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
