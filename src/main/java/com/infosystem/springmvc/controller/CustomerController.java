package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.ContractPageDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.dataservice.DataService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
//@SessionAttributes("roles")
public class CustomerController extends ControllerTemplate {

    public final UserService userService;
    private final DataService dataService;

    @Autowired
    public CustomerController(UserService userService, DataService dataService) {
        super("customer/");
        this.userService = userService;
        this.dataService = dataService;
    }

    //todo
    /**
     * Returns customer panel view with user info.
     *
     * @param model model
     * @return view
     */
    @RequestMapping("/customerPanel")
    public String customerPanel(ModelMap model) {
        String login = getPrincipal();
        User user = null;
        try {
            user = userService.findByLogin(login);
        } catch (DatabaseException e) {
            prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", user);
        return path + "customerPanel";
    }

    /**
     * Returns view with contract page.
     *
     * @param contractId contractId
     * @param model model
     * @return view
     */
    @RequestMapping(value = "/customerPanel/contract/{contractId}")
    public String contract(@PathVariable(value = "contractId") Integer contractId, ModelMap model) {
        ContractPageDto contractPageDto = null;
        try {
            contractPageDto = dataService.getContractPageData(contractId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contractPageDto", contractPageDto);
        return path + "contract";
    }

    //todo
    /**
     * Returns view with cart page.
     *
     * @return view
     */
    @RequestMapping(value = "/customerPanel/cart")
    public String cart(ModelMap model) {
        String login = getPrincipal();
        User user = null;
        try {
            user = userService.findByLogin(login);
        } catch (DatabaseException e) {
            prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", user);
        return path + "cart";
    }


    //todo
    /**
     * Returns addFunds view.
     *
     * @return view
     */
    @RequestMapping("/customerPanel/addFunds")
    public String addFunds(ModelMap model) {
        String login = getPrincipal();
        User user = null;
        try {
            user = userService.findByLogin(login);
        } catch (DatabaseException e) {
            prepareErrorPage(model, e.getMessage());
        }
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", user);
        return path + "addFunds";
    }
}
