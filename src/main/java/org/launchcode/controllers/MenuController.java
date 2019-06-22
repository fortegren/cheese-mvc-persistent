package org.launchcode.controllers;


import org.launchcode.models.AddMenuItemForm;
import org.launchcode.models.Cheese;
import org.launchcode.models.Menu;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
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
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;
    private CheeseDao cheeseDao;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("menus", menuDao.findAll());
        model.addAttribute("title", "Menus");

        return "menu/index";

    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute(new Menu());

        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(@ModelAttribute @Valid Menu menu, Errors errors) {

        if (errors.hasErrors()){
            return "menu/add";
        }

        menuDao.save(menu);

        return "redirect:view/" + menu.getId();
    }

    @RequestMapping(value = "view/{id}", method = RequestMethod.GET)
    public String viewMenu (@PathVariable int id, Model model) {
        Menu menu = menuDao.findOne(id);

        model.addAttribute("menu", menu);
        return "menu/view";

    }

    @RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(@PathVariable int menuId, Model model) {
        Menu menu = menuDao.findOne(menuId);

        AddMenuItemForm form = new AddMenuItemForm(menu, cheeseDao.findAll());

        model.addAttribute("form", form);
        model.addAttribute("title", "Add item to menu:" + menu.getName());

        return "menu/add-item";
    }

    @RequestMapping(value = "add-item", method = RequestMethod.POST)
    public String addItem(@ModelAttribute @Valid AddMenuItemForm form, Errors errors) {

        if (errors.hasErrors()) {
            return "menu/add-item";
        }

        Menu theMenu = menuDao.findOne(form.getMenuId());
        Cheese theCheese = cheeseDao.findOne(form.getCheeseId());

        theMenu.addItem(theCheese);

        menuDao.save(theMenu);


        return "redirect:view/" + theMenu.getId();
    }




}
