package com.crmtracker.controller;

import com.crmtracker.dao.CustomerDAOImpl;
import com.crmtracker.entity.Customer;
import com.crmtracker.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final Logger log = (Logger) LogManager.getLogger(CustomerController.class.getName());

    // need to inject the customer service
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomers(Model model){
        log.info("Starting CustomerController listCustomers[0]");

        log.info("listCustomers[1]: /customer/list - Getting customer list");

        log.debug("listCustomers[2]: Getting customer from the service");
        List<Customer> customers = customerService.getCustomers();

        log.debug("listCustomers[3]: Adding the customers to the model");
        model.addAttribute("customers", customers);

        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        log.info("Starting CustomerController showFormForAdd[0]");

        log.info("showFormForAdd[1]: /customer/showFormForAdd - Showing form for adding");

        log.debug("showFormForAdd[2]: creating model attribute to bind form data");
        model.addAttribute("customer", new Customer());

        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer){
        log.info("Starting CustomerController saveCustomer[0]");
        log.info("saveCustomer[1]: /customer/showFormForAdd - saving customer {}", customer);

        customerService.saveCustomer(customer);
        log.debug("saveCustomer[2]: redirecting to /customer/list");

        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int id, Model model){
        log.info("Starting CustomerController showFormForUpdate[0]");
        log.info("showFormForUpdate[1]: /customer/showFormForUpdate - updating customer {}", id);

        log.debug("showFormForUpdate[2]: Getting the customer from db by id {}", id);
        Customer customer = customerService.getCustomer(id);

        log.debug("showFormForUpdate[3]: Setting customer as a model attribute to pre-populate the form");
        model.addAttribute("customer", customer);

        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") int id, Model model){
        log.info("Starting CustomerController deleteCustomer[0]");
        log.info("deleteCustomer[1]: /customer/delete - deleting customer {}", id);

        // delete the customer
        log.debug("deleteCustomer[2]: Deleting the customer {}", id);
        customerService.deleteCustomer(id);

        log.debug("deleteCustomer[3]: Redirecting to /customer/list");
        return "redirect:/customer/list";
    }

    @GetMapping("/search")
    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
                                  Model theModel) {
        log.info("Starting CustomerController searchCustomers[0]");
        log.info("searchCustomers[1]: searchName - {}", theSearchName);

        log.debug("searchCustomers[2]: searching customers from the service");
        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

        log.debug("searchCustomers[3]: adding the customers to the model");
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";
    }

}
