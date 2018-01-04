package com.sizonenko.bicycleapp.api.sms;

import com.sizonenko.bicycleapp.entity.Confirm;
import java.util.HashMap;

public class SmsSender {
    public static final String ACCOUNT_SID = "AC9a3916abba1f7158695b8f832243d7bb";
    public static final String AUTH_TOKEN = "69c8b726029eb30dd13d10f9170834d1";

   /* public static void send(Confirm confirm, String phone) throws TwilioRestException {
        TwilioRestClient client = new TwilioRestClient(System.getenv(ACCOUNT_SID), System.getenv(AUTH_TOKEN));
        Account account = client.getAccount();
        SmsFactory factory = account.getSmsFactory();
        HashMap<String,String> message = new HashMap<>();
        message.put("To", "+"+phone);
        message.put("From", "Rovar");
        message.put("Body", "Login: " + confirm.getLogin() + " Code: " + confirm.getCode());
        factory.create(message);
    }*/
}
