package info.dmerej;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import info.dmerej.mailprovider.SendMailRequest;
import info.dmerej.mailprovider.SendMailResponse;

public class MailSenderTest {

    User user = new User("Meunier", "meunier@gmail.com");
    SendMailRequest sendMailRequest;
    Object requestObject;

    MailSender mailSender = new MailSender(new HttpClient() {
        @Override
        public SendMailResponse post(String url, Object request) {
            sendMailRequest = (SendMailRequest) request;
            return null;
        }
    });

    MailSender failedMailSender = new MailSender(new HttpClient() {
        @Override
        public SendMailResponse post(String url, Object request) {
            requestObject = request;
            return new SendMailResponse(503, "Error http");
        }
    });

    @Test
    void should_make_a_valid_http_request() {
        mailSender.sendV1(user, "Hello World?");
        assertEquals("meunier@gmail.com", sendMailRequest.recipient());
        assertEquals("New notification", sendMailRequest.subject());
        assertEquals("Hello World?", sendMailRequest.body());
    }

    @Test
    void should_retry_when_getting_a_503_error() {
        SendMailRequest request = new SendMailRequest("meunier@gmail.com", "New notification", "Hello World?");
        failedMailSender.sendV2(user, "Hello World?");
        assertEquals(requestObject, request);
    }
}
