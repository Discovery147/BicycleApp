package com.sizonenko.bicycleapp.validator;

import com.sizonenko.bicycleapp.entity.Member;

import javax.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberValidator {
    private static final String REGEX_LOGIN = "[A-Za-z0-9]+";
    private static final String REGEX_PASSWORD = "[A-Za-z0-9]+";
    private static final String REGEX_NAME = "[A-Za-z]+";
    private static final String REGEX_EMAIL = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static final String REGEX_PHONE = "\\d{12}";

    public boolean validateLogIn(String login, String password){
        Matcher matherLogin = Pattern.compile(REGEX_LOGIN).matcher(login);
        Matcher matherPassword = Pattern.compile(REGEX_PASSWORD).matcher(password);
        return matherLogin.matches() && matherPassword.matches();
    }

    public boolean validateRegister(Member member){
        Matcher mather = Pattern.compile(REGEX_LOGIN).matcher(member.getLogin());
        if(!mather.matches()) {
            return false;
        }
        mather = Pattern.compile(REGEX_PASSWORD).matcher(member.getPassword());
        if(!mather.matches()) {
            return false;
        }
        mather = Pattern.compile(REGEX_NAME).matcher(member.getFirstname());
        if(!mather.matches()) {
            return false;
        }
        mather = Pattern.compile(REGEX_NAME).matcher(member.getLastname());
        if(!mather.matches()) {
            return false;
        }
        mather = Pattern.compile(REGEX_EMAIL).matcher(member.getEmail());
        if(!mather.matches()) {
            return false;
        }
        mather = Pattern.compile(REGEX_PHONE).matcher(member.getPhone());
        if(!mather.matches()) {
            return false;
        }
        return true;
    }

    public boolean validateEditProfile(String login, String phone, Part file) {
        Matcher mather = Pattern.compile(REGEX_LOGIN).matcher(login);
        if(!mather.matches()) {
            return false;
        }
        mather = Pattern.compile(REGEX_PHONE).matcher(phone);
        if(!mather.matches()) {
            return false;
        }
        return file.getSize() <= 1_048_576; // <= 1 MB; (16 MB MEDIUMBLOB)
    }
}
