package com.lunchtime.network.apiObjects.wrappers;

import com.lunchtime.network.apiObjects.models.Menu;

public class MenuWrapper {
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public MenuWrapper(Menu menu) {
        this.menu = menu;
    }
}
