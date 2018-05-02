package com.websystique.springmvc.controller;


import com.websystique.springmvc.dto.ContractUserIdDto;
import com.websystique.springmvc.dto.GetTarifAsJsonDto;
import com.websystique.springmvc.model.*;
import com.websystique.springmvc.service.ContractService;
import com.websystique.springmvc.service.TariffOptionService;
import com.websystique.springmvc.service.TariffService;
import com.websystique.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class ViewController {

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
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping("/")
    public String startPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "index";
    }

    /**
     * Mapping to login screen.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String onLogin(@Valid User user, BindingResult result,
                          ModelMap model) {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/list";
        }

    }

    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            //new SecurityContextLogoutHandler().logout(request, response, auth);
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping("/lk")
    public String lk(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedinuser", getPrincipal());

        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        }
        if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/adminPanel";
        }
        return "redirect:/customerPanel";
    }

    @RequestMapping("/adminPanel")
    public String adminPanel(ModelMap model) {
        List<User> users = userService.findFirstUsers();
        List<Tariff> tariffs =tariffService.findFirstTariffs();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users",users);
        model.addAttribute("tariffs",tariffs);
        return "adminPanel";
    }

    @RequestMapping("/adminPanel/allUsers")
    public String adminPanelAllUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("users",users);
        return "allUsers";
    }

    @RequestMapping("/adminPanel/allContracts")
    public String adminPanelAllContracts(ModelMap model) {
        List<Contract> contracts = contractService.findAllContracts();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contracts",contracts);
        return "allContracts";
    }

    @RequestMapping("/adminPanel/allTariffs")
    public String adminPanelAllTariffs(ModelMap model) {
        List<Tariff> tariffs = tariffService.findAllTariffs();
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("tariffs",tariffs);
        return "allTariffs";
    }


    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.GET)
    public String addUser(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        HashSet<String> roles = new HashSet<>(Arrays.asList(Role.CUSTOMER.getRole(), Role.ADMIN.getRole()));
        model.addAttribute("roles", roles);
        return "addUser";
    }


    @RequestMapping(value = "/adminPanel/addUser", method = RequestMethod.POST)
    public String saveUser( @Valid User user, BindingResult result,
                           ModelMap model) {

        if (result.hasErrors()) {
            return "addUser";
        }

        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " + user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        //return "success";
        return "addSuccess";
    }

    @RequestMapping(value ="/adminPanel/addContract", method = RequestMethod.GET)
    public String addContract(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        ContractUserIdDto contractUserIdDto = new ContractUserIdDto();
        model.addAttribute("contractUserIdDto", contractUserIdDto);
        model.addAttribute("edit", false);
        return "addContract";
    }


    @RequestMapping(value = "/adminPanel/addContract", method = RequestMethod.POST)
    public String saveContract(@Valid ContractUserIdDto contractUserIdDto, BindingResult result,
                               ModelMap model) {


        if (result.hasErrors()) {
            return "addContract";
        }

        Contract contract = new Contract();
        contract.setPhoneNumber(contractUserIdDto.getPhoneNumber());
        contract.setUser(userService.findById(contractUserIdDto.getUser_id()));
        contractService.saveContract(contract);

        model.addAttribute("success", "Contract " + contract.getId() + "registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        //return "success";
        return "addSuccess";
    }

    @RequestMapping(value = "/adminPanel/addTariff", method = RequestMethod.GET)
    public String addTariff(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        List<TariffOption> options = tariffOptionService.findAllTariffOptions();

        model.addAttribute("options", options);
        model.addAttribute("edit", false);
        return "addTariff";
    }

    @RequestMapping(value = "/adminPanel/addTariff", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.POST)
    public String saveTariffAjax(@RequestBody @Valid GetTarifAsJsonDto getTarifAsJsonDto, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "addTariff";
        }
        tariffService.saveTariff(getTarifAsJsonDto);
        model.addAttribute("success", "Tariff " + getTarifAsJsonDto.getTariffDto().getName()
                + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "addSuccess";
    }

    @RequestMapping(value = "/adminPanel/user/{user_id}")
    public String user(@PathVariable(value = "user_id") Integer user_id, ModelMap model) {
        User user = userService.findById(user_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/adminPanel/contract/{contract_id}")
    public String contract(@PathVariable(value = "contract_id") Integer contract_id, ModelMap model) {
        Contract contract = contractService.findById(contract_id);
        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("contract", contract);
        return "contract";
    }




    @RequestMapping("/customerPanel")
    public String customerPanel(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());

        return "customerPanel";
    }



    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     */
    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }


}
