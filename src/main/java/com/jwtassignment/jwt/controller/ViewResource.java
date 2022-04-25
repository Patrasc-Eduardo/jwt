package com.jwtassignment.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class ViewResource {
    @RequestMapping({"view"})
    public ModelAndView firstPage() {
        ModelAndView modelAndView = new ModelAndView("view");
        return modelAndView;
    }
}
