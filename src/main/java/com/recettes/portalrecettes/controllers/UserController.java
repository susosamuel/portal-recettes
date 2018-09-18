package com.recettes.portalrecettes.controllers;

import com.recettes.portalrecettes.models.User;
import com.recettes.portalrecettes.persistence.UserDao;
import com.recettes.portalrecettes.persistence.IngredientDao;
import com.recettes.portalrecettes.persistence.recettesDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private final IngredientDao ingreDao;
    private final RecetteController recetteController;
    private final UserDao userDao;
    private final recettesDao recetteDao;


    public UserController(UserDao userDao, IngredientDao ingreDao,recettesDao recetteDao, RecetteController recetteController) {
        this.userDao = userDao;
        this.ingreDao = ingreDao;
        this.recetteDao = recetteDao;
        this.recetteController = recetteController;
    }

    @GetMapping("/registration")
    public String showRegistration()
    {
    return "registration";
    }

    @GetMapping("/connection")
    public String showLogin(Model model)
    {
        model.addAttribute("user",new User() );
        return "connection";
    }

    @GetMapping("/home")
    public String showHome(Model model)
    {
        model.addAttribute("data", recetteDao.findAll());
        return "index";
    }

    @GetMapping("")
    public String showIndex(Model model)
    {
        model.addAttribute("data", recetteDao.findAll());
        return "index";
    }

    @GetMapping("/account")
    public String showAccount()
    {
        return "account";
    }

    @GetMapping("/recette")
    public String showRecette()
    {
        return "recette";
    }

    @GetMapping("/probleme")
    public String showProblem()
    {
        return "probleme";
    }

    @PostMapping("/enregistrer")
    public String addUser(User user)
    {
        //TODO ajouter model et showRegistration
        userDao.save(user);
        //TODO redirect to registration success page
        return "connection";
    }

    @PostMapping("/login")
    public ModelAndView doLogin(Model model, User user)
    {
        //TODO check password
        try {
            User u = userDao.findUserByLogin(user.getLogin());
            return new ModelAndView("redirect:/account/"+u.getId());
        }
        catch (Exception e)
        {
            return new ModelAndView("redirect:/probleme");
        }
    }


}
