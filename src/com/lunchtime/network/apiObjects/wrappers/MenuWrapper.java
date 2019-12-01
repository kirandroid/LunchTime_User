package com.lunchtime.network.apiObjects.wrappers;

import com.lunchtime.network.apiObjects.models.Menu;

import java.util.List;

public class MenuWrapper {
    private List<Menu> menu;

    public List<Menu> getMenu() {
        return menu;
    }

    public MenuWrapper(List<Menu> menu) {
        this.menu = menu;
    }
}
