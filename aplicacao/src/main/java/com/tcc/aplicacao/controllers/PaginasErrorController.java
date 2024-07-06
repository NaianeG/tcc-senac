package com.tcc.aplicacao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PaginasErrorController {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/413");
        modelAndView.addObject("message", "O arquivo é muito grande. O tamanho máximo permitido é 10MB.");
        return modelAndView;
    }
}
