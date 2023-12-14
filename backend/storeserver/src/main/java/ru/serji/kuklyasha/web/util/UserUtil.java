package ru.serji.kuklyasha.web.util;

import lombok.experimental.*;
import org.springframework.lang.*;
import org.springframework.security.crypto.factory.*;
import org.springframework.security.crypto.password.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@UtilityClass
public class UserUtil {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static User createNewFromTo(@NonNull UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getEmail(), userTo.getPassword(), Role.USER);
    }

    public static User updateFromTo(@NonNull User user, @NonNull UserTo userTo) {
        Address address = new Address(userTo.getCountry(), userTo.getCity(), userTo.getRegion(), userTo.getStreet(), userTo.getZipcode());
        UserInfo info = new UserInfo(userTo.getLastname(), userTo.getPhone(), address);
        Notice notice = new Notice(userTo.isEmailNotice(), userTo.isPhoneNotice());
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        user.setInfo(info);
        user.setNotice(notice);
        return user;
    }

    public static User prepareToSave(@NonNull User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static UserTo createToFromUser(@NonNull User user) {
        if (user.getInfo() == null) {
            return new UserTo(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    user.getNotice().isEmail(),
                    user.getNotice().isPhone(),
                    user.isEnabled());
        }

        return new UserTo(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getInfo().getLastname(),
                user.getInfo().getPhone(),
                user.getInfo().getAddress().getCountry(),
                user.getInfo().getAddress().getCity(),
                user.getInfo().getAddress().getRegion(),
                user.getInfo().getAddress().getStreet(),
                user.getInfo().getAddress().getZipcode(),
                user.getNotice().isEmail(),
                user.getNotice().isPhone(),
                user.isEnabled());
    }

    public static UserPage createUserPage(List<UserTo> content, int total) {
        return new UserPage(content, total);
    }
}