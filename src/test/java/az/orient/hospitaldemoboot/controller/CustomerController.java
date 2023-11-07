package az.orient.hospitaldemoboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class  CustomerController {


        @GetMapping("/GetCustomerList")
         public void getCustomerList() {
   // localhost:8080/hospital/GetCustomerList---sorgu gonderen zaman bele cagiririq.
         }



}
