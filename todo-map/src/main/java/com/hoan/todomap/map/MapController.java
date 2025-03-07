package com.hoan.todomap.map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {


    @GetMapping({"", "/"})
    public String getMap() {
        return "map";
    }


    @GetMapping( "/searchTest")
    public String getSearchTest() {
        return "search";
    }



}
