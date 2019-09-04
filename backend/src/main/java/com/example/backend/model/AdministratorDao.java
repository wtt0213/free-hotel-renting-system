package com.example.backend.model;

import java.util.List;

public interface AdministratorDao {

    Administrator getAdministratorByName(String name);

    Administrator getAdministratorById(String id);

    List<Administrator> getAllAdministrator();
}
