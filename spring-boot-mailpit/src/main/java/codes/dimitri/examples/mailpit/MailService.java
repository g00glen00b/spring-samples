package codes.dimitri.examples.mailpit;

import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send() throws MessagingException {
        var mimeMessage = mailSender.createMimeMessage();
        var message = new MimeMessageHelper(mimeMessage);
        var content = """
        <html>
        <h1>This is a test email</h1>
        <p>Please do not respond to this email.</p>
        </html>
        """;
        message.setFrom("noreply@example.org");
        message.setTo("me@example.org");
        message.setSubject("Test email");
        message.setText(content, true);
        mailSender.send(message.getMimeMessage());
    }
}
