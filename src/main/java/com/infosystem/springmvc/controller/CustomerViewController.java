package com.infosystem.springmvc.controller;

import com.infosystem.springmvc.dto.ContractPageDto;
import com.infosystem.springmvc.dto.SessionCart;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.MyBusinessException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.DataService.DataService;
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
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
//@SessionAttributes("roles")
public class CustomerViewController extends ViewControllerTemplate {

    public CustomerViewController(){
        super("customer/");
    }


    @Autowired
    UserDetailsService customUserDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @Autowired
    TariffOptionService tariffOptionService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TariffService tariffService;

    @Autowired
    DataService dataService;



    /**
     * Returns customer panel view with user info.
     * @param model
     * @return view
     */
    @RequestMapping("/customerPanel")
    public String customerPanel(ModelMap model) {
        String login =  getPrincipal();
        User user = userService.findByLogin(login);
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", user);
        return path +"customerPanel";
    }

    /**
     * Returns view with contract page.
     * @param contractId
     * @param model
     * @return view
     * @throws DatabaseException
     */
    @RequestMapping(value = "/customerPanel/contract/{contractId}")
    public String contract(@PathVariable(value = "contractId") Integer contractId, ModelMap model){
        ContractPageDto contractPageDto = null;
        try {
            contractPageDto = dataService.getContractPageData(contractId);
        } catch (DatabaseException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contractPageDto", contractPageDto);
        return path +"contract";
    }

    /**
     * Returns view with cart page.
     * @return view
     */
    @RequestMapping(value = "/customerPanel/cart")
    public String cart(ModelMap model){
        String login =  getPrincipal();
        User user = userService.findByLogin(login);
        model.addAttribute("loggedinuser", login);
        model.addAttribute("user", user);
        return path +"cart";
    }


    /**
     * Buys items from cart for user.
     * @param model
     * @return success view.
     */
    @RequestMapping("/customerPanel/buyOptions")
    public String buyOptionsInCart(ModelMap model) {
        String login =  getPrincipal();
        try {
            contractService.customerAddOptions();
        } catch (MyBusinessException e) {
            return prepareErrorPage(model,e);
        }
        model.addAttribute("success","You bought smth.");
        model.addAttribute("loggedinuser", login);
        return path +"success";
    }
}
