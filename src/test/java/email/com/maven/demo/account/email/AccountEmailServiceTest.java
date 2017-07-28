package email.com.maven.demo.account.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import com.maven.demo.account.email.AccountEmailService;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.internet.MimeMessage;

/**
 * Created by yinhao on 17/7/28.
 */
public class AccountEmailServiceTest {

    private GreenMail greenMail;

    @Before
    public void startMailServer()throws Exception{
        greenMail = new GreenMail(new ServerSetup(1025, "localhost", "smtp"));
        greenMail.setUser("test@juvenxu.com","123456");
        greenMail.start();
    }

    @Test
    public void testSendMail()throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService emailService = (AccountEmailService)context.getBean("accountEmailService");
        String subject = "The Subject";
        String html = "<h2>hello world</h2>";
        emailService.sendMail("test@juvenxu.com",subject,html);
        greenMail.waitForIncomingEmail(2000,1);
        MimeMessage[] messages = greenMail.getReceivedMessages();
        Assert.assertEquals(1,messages.length);
        Assert.assertEquals(subject,messages[0].getSubject());
        Assert.assertEquals(html, GreenMailUtil.getBody(messages[0]).trim());
    }

    @After
    public void stopMailServer()throws Exception{
        greenMail.stop();
    }
}
