package com.nighthawk.spring_portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class Meal {

    // CONTROLLER handles GET request for /birds, maps it to birds() method
    @GetMapping("/meal")
    public String meal() {

        // load HTML VIEW (birds.html)
        return "meal";

    }
}

