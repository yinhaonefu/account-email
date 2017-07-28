package com.maven.demo.account.email;

/**
 * Created by yinhao on 17/7/28.
 */
public interface AccountEmailService {
    void sendMail(String to,String subject,String htmlText) throws Exception;
}
