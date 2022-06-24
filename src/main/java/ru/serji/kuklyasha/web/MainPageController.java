package ru.serji.kuklyasha.web;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainPageController {

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}