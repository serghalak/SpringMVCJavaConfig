package com.apress.prospring5.ch16.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import org.apache.commons.io.IOUtils;

//@Controller
@RestController
public class HelloController {

    private MessageSource messageSource;

    public HelloController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello-world")
    public String sayHello(Model model) {

        //model.addAttribute("test", messageSource.getMessage("singer_save_success", null, Locale.ENGLISH));
        System.out.println(messageSource.getMessage("userform.title", null, Locale.ENGLISH));
        System.out.println(messageSource.getMessage("label_welcome", null, Locale.ENGLISH));
        return "hello_world";
    }


    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping("/fileup")
    public String create(/*@Valid Singer singer, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes,
                         Locale locale, */@RequestParam(value="file", required=false) Part file) {
        System.out.println("Creating singer");
//        if (bindingResult.hasErrors()) {
//            uiModel.addAttribute("message", new Message("error",
//                    messageSource.getMessage("singer_save_fail", new Object[]{}, locale)));
//            uiModel.addAttribute("singer", singer);
//            return "singers/create";
//        }
//        uiModel.asMap().clear();
//        redirectAttributes.addFlashAttribute("message", new Message("success",
//                messageSource.getMessage("singer_save_success", new Object[]{}, locale)));
//
//        logger.info("Singer id: " + singer.getId());

        // Process upload file
        if (file != null) {
//            logger.info("File name: " + file.getName());
//            logger.info("File size: " + file.getSize());
//            logger.info("File content type: " + file.getContentType());
            byte[] fileContent = null;
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null) System.out.println("File inputstream is null");
                fileContent = IOUtils.toByteArray(inputStream);
                System.out.println(fileContent.toString());
                //singer.setPhoto(fileContent);
            } catch (IOException ex) {
                System.out.println("Error saving uploaded file");
            }
            //singer.setPhoto(fileContent);
        }

        //singerService.save(singer);
        //return "redirect:/singers/";
        return "ok file download from request to byte array";
    }

    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] downloadPhoto(@PathVariable("id") Long id) {
        System.out.println("return byte arr id=" + id );
        //Singer singer = singerService.findById(id);

//        if (singer.getPhoto() != null) {
//            logger.info("Downloading photo for id: {} with size: {}", singer.getId(),
//                    singer.getPhoto().length);
//        }

        //return singer.getPhoto();
        return new byte[] {25,32,67,-102};
    }
}
