package com.example.backend.model;

import java.util.List;

public interface TenantDao {

    /**
     * 保存租客
     * @param tenant
     */
    void saveTenant(Tenant tenant);

    /**
     * 判断租客使用的name是否重复
     * @param newName
     * @return
     */
    boolean isValidName(String newName);

    /**
     * 判断租客使用的email是否合法 邮箱不能重复
     * @param email
     * @return
     */
    boolean isValidEmail(String email);

    /**
     * 根据用户名返回该用户的登录密码
     * @param name 用户名
     * @return 如果该用户名存在，那么返回密码，一个字符串；否则返回null
     */
    String getPasswordByName(String name);

    /**
     * 根据邮箱返回该用户的登录密码
     * @param email 邮箱
     * @return 如果该手机号存在，那么返回密码，一个字符串；否则返回null
     */
    String getPasswordByEmail(String email);

    /**
     * 根据用户名返回一个用户
     * @param name 用户名
     * @return 返回租客对象
     */
    Tenant getTenantByName(String name);

    /**
     * 根据id返回一个用户
     * @param id 用户名
     * @return 返回租客对象
     */
    Tenant getTenantById(String id);

    /**
     * 根据email返回一个用户
     * @param email 用户名
     * @return 返回租客对象
     */
    Tenant getTenantByEmail(String email);

    /**
     * 根据id找到租客，并激活他的账户
     * @param id 用户的id
     */
    void activeTenant(String id);

    void deleteTenant(String id);

    void updatePassword(String id, String password);

    void updateName(String id, String name);

    void updateEmail(String id, String email);

    /**
     *   返回当前系统内部所有的用户的相关信息
     *   以list的形式
     */
    List<Tenant> getAllTenant();

    List<Tenant> getAllTenantsBlurry(String name);
}
