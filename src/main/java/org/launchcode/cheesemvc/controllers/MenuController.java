package org.launchcode.cheesemvc.controllers;

import org.launchcode.cheesemvc.models.Cheese;
import org.launchcode.cheesemvc.models.Menu;
import org.launchcode.cheesemvc.models.data.CheeseDao;
import org.launchcode.cheesemvc.models.data.MenuDao;
import org.launchcode.cheesemvc.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
@RequestMapping(value="menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value="")
    public String index (Model model){
        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Menu");
        return "menu/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddMenuForm(Model model){
        model.addAttribute(new Menu());
        model.addAttribute("title", "Menu");
        return "menu/add";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String processAddMenuForm(Model model, @ModelAttribute @Valid Menu menu, Errors errors){

        if (errors.hasErrors()){
            return "menu/add";
        }
        menuDao.save(menu);
        model.addAttribute("title", "Menu");
        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String viewMenu (@PathVariable int id,Model model){
        Menu menu = menuDao.findById(id).get();
        model.addAttribute("menu", menu);
        model.addAttribute("title", menu.getName());
        model.addAttribute("cheeses",menu.getCheeses());
        //model.addAttribute("id",menu.getId());
        return "menu/view";
    }

    @RequestMapping(value = "add-item/{id}", method = RequestMethod.GET)
    public  String addItem(@PathVariable int id,Model model) {
        Menu menu = menuDao.findById(id).get();
        AddMenuItemForm form = new AddMenuItemForm(cheeseDao.findAll(), menu);
        model.addAttribute("title", "Add item to menu: MENU NAME" + menu.getName());
        model.addAttribute("form", form);
        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public  String addItem(Model model,@ModelAttribute @Valid AddMenuItemForm form,Errors error){
        if(error.hasErrors()){
            return "menu/add-item";
        }
        Cheese theCheese = cheeseDao.findById(form.getCheeseId()).get();
        Menu theMenu = menuDao.findById(form.getMenuId()).get();
        theMenu.addItem(theCheese);
        menuDao.save(theMenu);
        return "redirect:/menu/view/"+ theMenu.getId();
    }
}
