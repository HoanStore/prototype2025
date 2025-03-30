package com.hoan.commitmanager.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping({"", "/"})
@RequiredArgsConstructor
@Controller
public class HomeController {

    @GetMapping({"", "/", "/home"})
    public String homePage() {
        return "home/home";
    }
}
