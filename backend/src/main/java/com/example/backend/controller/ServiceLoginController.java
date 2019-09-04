package com.example.backend.controller;

import com.example.backend.model.Administrator;
import com.example.backend.model.AdministratorDao;
import com.example.backend.model.Worker;
import com.example.backend.model.WorkerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ServiceLoginController {

    @Autowired
    private AdministratorDao administratorDao;

    @Autowired
    private WorkerDao workerDao;

    @GetMapping("/service_login")
    public Map<String, Object> login(HttpServletRequest request){
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String realPassword = null;
        String id = null;
        String phone = null;
        if(type.equals("administrator")){
            Administrator administrator = administratorDao.getAdministratorByName(name);
            if(administrator==null){
                return nameFalse();
            }
            id = administrator.getId();
            realPassword = administrator.getPassword();
        } else {
            Worker worker = workerDao.getWorkerByName(name);
            if(worker==null){
                return nameFalse();
            }
            id = worker.getId();
            realPassword = worker.getPassword();
            phone = worker.getPhone();
        }
        Map<String, Object> result = new HashMap<>();
        if(realPassword.equals(password)){
            result.put("ok", true);
            result.put("id", id);
            result.put("phone", phone);
        } else {
            result.put("ok", false);
            result.put("name", true);
            result.put("password", false);
        }
        return result;
    }

    private Map<String, Object> nameFalse(){
        Map<String, Object> result = new HashMap<>();
        result.put("ok", false);
        result.put("name", false);
        result.put("password", false);
        return result;
    }
}
