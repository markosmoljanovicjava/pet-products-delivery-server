/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import ui.view.ViewLoggedUsers;

/**
 *
 * @author marko
 */
public class ControllerLoggedUsers {

    private final ViewLoggedUsers viewLoggedUsers;

    public ControllerLoggedUsers(ViewLoggedUsers viewLoggedUsers) {
        this.viewLoggedUsers = viewLoggedUsers;

        init();
    }

    private void init() {
        viewLoggedUsers.setLocationRelativeTo(null);
    }

    void open() {
        viewLoggedUsers.setVisible(true);
    }

}
