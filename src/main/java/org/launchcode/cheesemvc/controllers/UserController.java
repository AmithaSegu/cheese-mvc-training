package org.launchcode.cheesemvc.controllers;

import org.launchcode.cheesemvc.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping(value= "add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title", "ADD USER");
        return "user/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid User user, /*String verify,*/ Errors errors){
//        model.addAttribute("user",user);
//        if(user.getPassword().equals(verify)){
//            return "user/index";
//        }
//        else{
//            return "user/add";
//        }
        if (errors.hasErrors()){
            model.addAttribute("username",user.getUsername());
            model.addAttribute("email",user.getEmail());
            model.addAttribute("errors","Password do nto match");
            return "user/add";
        }
        return "user/index";
    }

}
