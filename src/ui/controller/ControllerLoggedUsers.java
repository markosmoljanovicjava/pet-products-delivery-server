/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import controller.Controller;
import ui.components.UserTableModel;
import ui.view.ViewLoggedUsers;
import util.Keys;

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

        viewLoggedUsers.getjTableUsers().setModel(new UserTableModel(Controller.getInstance().getAllUsers()));
        Controller.getInstance().getMap().put(Keys.USERS_TABLE_MODEL, viewLoggedUsers.getjTableUsers().getModel());
    }

    void open() {
        viewLoggedUsers.setVisible(true);
    }

}
