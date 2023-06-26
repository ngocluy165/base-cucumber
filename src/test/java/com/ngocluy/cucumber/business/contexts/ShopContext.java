package com.ngocluy.cucumber.business.contexts;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ScenarioScope
@Component
public class ShopContext {
    private final Map<String, CachedShop> shops = new ConcurrentHashMap<>();
    private String lastCreateShopId;
    public void add(CachedShop shop) {
        shops.put(shop.getShopId(), shop);
        this.lastCreateShopId = shop.getShopId();
    }
    public Collection<CachedShop> getShops() {
        return shops.values();
    }

    public Set<String> getShopIds() {
        return shops.keySet();
    }

    public CachedShop getShopById(String id) {
        return shops.get(id);
    }

    public CachedShop removeShopById(String id) {
        return shops.remove(id);
    }

    public String getLastCreateShopId() {
        return lastCreateShopId;
    }
}
