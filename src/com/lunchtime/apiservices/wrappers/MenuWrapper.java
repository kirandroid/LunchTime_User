package com.lunchtime.apiservices.wrappers;

import com.lunchtime.apiservices.models.Menu;

public class MenuWrapper {
    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public MenuWrapper(Menu menu) {
        this.menu = menu;
    }
}
