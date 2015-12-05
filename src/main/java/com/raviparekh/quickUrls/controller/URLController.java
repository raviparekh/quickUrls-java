package com.raviparekh.quickUrls.controller;

import com.raviparekh.quickUrls.exceptions.EncodingError;
import com.raviparekh.quickUrls.exceptions.URLNotFound;
import com.raviparekh.quickUrls.model.URLForm;
import com.raviparekh.quickUrls.services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/url")
public class URLController {

    @Autowired
    private URLService urlService;

    @RequestMapping("/{urlKey}")
    public RedirectView redirectToFullUrl(HttpServletRequest request, @PathVariable("urlKey") String urlKey) throws URLNotFound {

        String fullUrl = urlService.getFullUrl(urlKey);

        return new RedirectView(fullUrl);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView createShortenURL(HttpServletRequest request, @Valid @ModelAttribute("urlForm") URLForm form, BindingResult result) throws EncodingError {
        if (result.hasErrors()) {
            return new ModelAndView("create");
        }

        String URLKey = urlService.createShortenUrl(form.getFullURL());

        String shortenUrl = request.getRequestURL().append(URLKey).toString();

        Map<String, String> model = new HashMap<String, String>();

        model.put("shortenURL", shortenUrl);

        return new ModelAndView("url", model);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getURLCreationView(@ModelAttribute("urlForm") URLForm form) {
        return "create";
    }

    @ExceptionHandler({URLNotFound.class})
    public ModelAndView commonCaseErrorHandler(Exception e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorMsg", e.getMessage());
        return model;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView unexpectedErrorHandler(Exception e) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("errorMsg", "Unexpected error");
        return model;
    }
}
