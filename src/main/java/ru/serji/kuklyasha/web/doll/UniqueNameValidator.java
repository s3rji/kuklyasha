package ru.serji.kuklyasha.web.doll;

import org.springframework.beans.factory.annotation.*;
import org.springframework.lang.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.validation.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.service.*;

import javax.servlet.http.*;

@Component
public class UniqueNameValidator implements Validator {

    static final String EXCEPTION_DUPLICATE_NAME = "Doll with this name already exists";

    private final DollService dollService;

    private final HttpServletRequest request;

    @Autowired
    public UniqueNameValidator(DollService dollService, HttpServletRequest request) {
        this.dollService = dollService;
        this.request = request;
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return HasIdAndName.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        HasIdAndName doll = (HasIdAndName) target;
        if (StringUtils.hasText(doll.getName())) {
            dollService.getByName(doll.getName())
                    .ifPresent(dbDoll -> {
                        if (request.getMethod().equals("PUT")) {
                            int dbId = dbDoll.id();
                            String requestURI = request.getRequestURI();
                            if (requestURI.endsWith("/" + dbId)) return;
                        }

                        errors.rejectValue("name", "", EXCEPTION_DUPLICATE_NAME);
                    });
        }
    }
}