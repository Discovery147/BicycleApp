package com.sizonenko.bicycleapp.parser;

import com.sizonenko.bicycleapp.entity.Member;

import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MemberParser {
    public Member editProfile(String login, String phone, Part file) throws IOException {
        Member member = new Member();
        InputStream input = file.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] byteImage = new byte[1000000]; // MEDIUM BLOB 16MB
        for (int length = 0; (length = input.read(byteImage)) > 0;) output.write(byteImage, 0, length);
        member.setLogin(login);
        member.setPhone(phone);
        member.setImage(org.apache.commons.codec.binary.Base64.encodeBase64String(byteImage));
        return member;
    }
}
