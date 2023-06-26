package com.ngocluy.cucumber.teardown.hooks;

import com.ngocluy.cucumber.business.contexts.ShopContext;
import com.ngocluy.cucumber.business.services.ShopService;
import com.ngocluy.cucumber.common.contexts.RestApiContext;
import com.ngocluy.cucumber.login.services.AdminUserService;
import com.ngocluy.cucumber.login.contexts.JWTLoginContext;
import com.ngocluy.cucumber.login.contexts.RegisterContext;
import com.ngocluy.cucumber.login.services.LoginService;
import com.ngocluy.cucumber.login.services.RegisterService;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class CleanRegisterHook {
    @Autowired
    private RegisterContext registerContext;
    @Autowired
    private JWTLoginContext jwtLoginContext;
    @Autowired
    private RestApiContext apiContext;
    @Autowired
    private RegisterService registerService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopContext shopContext;
    @Value("${service.admin.username}") String adminUsername;
    @Value("${service.admin.password}") String adminPassword;

    public void tearDownShops() throws Exception {
        if (shopContext.getShopIds().isEmpty()) {
            return;
        }
        for (String shopId: shopContext.getShopIds()) {
            shopService.deleteShop(shopId, apiContext, jwtLoginContext, shopContext);
        }
    }
    @After
    public void deleteUser() throws Exception {
        if (registerContext.getRegisterUserIds().isEmpty()) {
            return;
        }
       System.out.println("********Delete User" + registerContext.getRegisterUserIds());

       loginService.login(adminUsername, adminPassword, apiContext, jwtLoginContext);
       for (String userId: registerContext.getRegisterUserIds()) {
           adminUserService.deleteUser(userId, apiContext, jwtLoginContext);
       }
        tearDownShops();
    }

}
