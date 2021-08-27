package com.example.sping_portfolio.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller  // HTTP requests are handled as a controller, using the @Controller annotation
public class BryantJinBirdsAndGreet {
    @GetMapping("/birds2")
    // CONTROLLER handles GET request for /greeting, maps it to greeting() and does variable bindings
    public String birds() {
        return "birds"; // returns HTML VIEW (greeting)
    }

    @GetMapping("/greet2")    // CONTROLLER handles GET request for /greeting, maps it to greeting() and does variable bindings
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        // @RequestParam handles required and default values, name and model are class variables, model looking like JSON
        model.addAttribute("name", name); // MODEL is passed to html
        return "greet"; // returns HTML VIEW (greeting)
    }

    @GetMapping("/html_demo")    // CONTROLLER handles GET request for /greeting, maps it to greeting() and does variable bindings
    public String htmldemo(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        return "demo"; // returns HTML VIEW (greeting)
    }
}

