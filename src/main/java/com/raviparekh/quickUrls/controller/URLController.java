package com.raviparekh.quickUrls.controller;

import com.raviparekh.quickUrls.exceptions.URLNotFound;
import com.raviparekh.quickUrls.model.URLForm;
import com.raviparekh.quickUrls.services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public RedirectView redirectToFullUrl(HttpServletRequest request, @PathVariable("urlKey") String urlKey) {
        String fullUrl;

        try {
            fullUrl = urlService.getFullUrl(urlKey);
        } catch (URLNotFound e) {
            return new RedirectView(request.getLocalName());
        }

        return new RedirectView(fullUrl);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView createShortenURL(HttpServletRequest request, @Valid @ModelAttribute("urlForm") URLForm form, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("create");
        }

        String URLKey = urlService.createShortenUrl(form.getURL());

        String shortenUrl = request.getRequestURL().append(URLKey).toString();

        Map<String, String> model = new HashMap<String, String>();

        model.put("shortenURL", shortenUrl);

        return new ModelAndView("create", model);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getURLCreationView() {
        return "create";
    }

}
