package com.example.backend.controller;

import com.example.backend.model.Administrator;
import com.example.backend.model.AdministratorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorDao administratorDao;

    @GetMapping("/get/name")
    Administrator getAdministratorByName(HttpServletRequest request) {
        return administratorDao.getAdministratorByName(request.getParameter("name"));
    }

    @GetMapping("/get/id")
    Administrator getAdministratorById(HttpServletRequest request) {
        return administratorDao.getAdministratorByName(request.getParameter("id"));
    }

    @GetMapping("/get")
    List<Administrator> getAllAdministrator() {
        return administratorDao.getAllAdministrator();
    }

}