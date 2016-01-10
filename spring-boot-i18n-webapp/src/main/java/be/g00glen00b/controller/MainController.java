package be.g00glen00b.controller;

import be.g00glen00b.service.impl.AwesomeWebsiteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class MainController {
    @Autowired
    private AwesomeWebsiteServiceImpl service;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/first")
    public ModelAndView getAwesomeWebsite() {
        final String[] params = {service.getAwesomeWebsite()};
        final String msg = messageSource.getMessage("message.mostAwesomeWebsite", params, LocaleContextHolder.getLocale());
        return new ModelAndView("awesomeWebsite", "website", msg);
    }

    @RequestMapping("/second")
    public ModelAndView getAwesomeWebsiteSecond() {
        return new ModelAndView("awesomeWebsite2", "website", service.getAwesomeWebsite());
    }
}
