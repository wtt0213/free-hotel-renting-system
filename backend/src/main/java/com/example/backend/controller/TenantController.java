package com.example.backend.controller;

import com.example.backend.model.Tenant;
import com.example.backend.model.TenantDao;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TenantController
 */
@RestController
public class TenantController {

    @Autowired
    private TenantDao tenantDao;

    @PostMapping("delete_tenant")
    public String deleteTenant(@RequestBody String jsonString){
        String id = new JSONObject(jsonString).getString("id");
        tenantDao.deleteTenant(id);
        return "success";
    }

    @PostMapping("update_tenant_name")
    public String updateTenantName(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        if(tenantDao.isValidName(name)){
            tenantDao.updateName(id, name);
            return "success";
        } else {
            return "failed";
        }
    }

    @PostMapping("update_tenant_password")
    public String updateTenantPassword(@RequestBody String jsonString){
        JSONObject jsonObject = new JSONObject(jsonString);
        String id = jsonObject.getString("id");
        String password = jsonObject.getString("password");
        tenantDao.updatePassword(id, password);
        return "success";
    }

    @GetMapping("get_all_tenants")
    public List<Tenant> getAllTenants(){
        return tenantDao.getAllTenant();
    }

    @GetMapping("get_all_tenants_blurry")
    public List<Tenant> getAllTenantsBlurry(HttpServletRequest request){
        String name = request.getParameter("name");
        return tenantDao.getAllTenantsBlurry(name);
    }

}