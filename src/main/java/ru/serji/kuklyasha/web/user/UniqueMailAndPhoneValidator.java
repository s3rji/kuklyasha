package ru.serji.kuklyasha.web.user;

import org.springframework.beans.factory.annotation.*;
import org.springframework.lang.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.validation.*;
import ru.serji.kuklyasha.dto.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;
import ru.serji.kuklyasha.web.util.*;

import javax.servlet.http.*;

@Component
public class UniqueMailAndPhoneValidator implements Validator {

    public static final String EXCEPTION_DUPLICATE_EMAIL = "User with this email already exists";

    public static final String EXCEPTION_DUPLICATE_PHONE = "User with this phone already exists";

    private final UserService userService;

    private final HttpServletRequest request;

    @Autowired
    public UniqueMailAndPhoneValidator(UserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasIdAndEmail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserTo user = ((UserTo) target);
        if (StringUtils.hasText(user.getEmail())) {
            userService.getByEmailIgnoreCase(user.getEmail())
                    .ifPresent(dbUser -> {
                        if (duplicateNotFound(user, dbUser)) {
                            return;
                        }
                        errors.rejectValue("email", "", EXCEPTION_DUPLICATE_EMAIL);
                    });
        }

        if (StringUtils.hasText(user.getPhone())) {
            userService.getByPhone(user.getPhone())
                    .ifPresent(dbUser -> {
                        if (duplicateNotFound(user, dbUser)) {
                            return;
                        }
                        errors.rejectValue("phone", "", EXCEPTION_DUPLICATE_PHONE);
                    });
        }
    }

    private boolean duplicateNotFound(UserTo user, User dbUser) {
        if (request.getMethod().equals("PUT")) {
            int dbId = dbUser.id();

            if (user.getId() != null && dbId == user.id()) return true;

            String requestURI = request.getRequestURI();
            return requestURI.endsWith("/" + dbId) || (dbId == SecurityUtil.authId() && requestURI.contains("/profile"));
        }
        return false;
    }
}