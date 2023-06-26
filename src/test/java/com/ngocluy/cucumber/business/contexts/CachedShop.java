package com.ngocluy.cucumber.business.contexts;

import java.util.Objects;

public class CachedShop {
    private final String shopId;
    private final String username;

    public CachedShop(String shopId, String username) {
        this.shopId = shopId;
        this.username = username;
    }

    public String getShopId() {
        return shopId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CachedShop that = (CachedShop) o;
        return Objects.equals(shopId, that.shopId) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopId, username);
    }
}
