package com.hogwarts.enrollment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

    @RequestMapping("/api/doc.html")
    public String redirectSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }
}
