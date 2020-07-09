/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import thread.ServerThread;
import ui.view.ViewConfigCommunication;
import ui.view.ViewConfigDatabase;
import ui.view.ViewLoggedUsers;
import ui.view.ViewMain;

/**
 *
 * @author marko
 */
public class ControllerMain {

    private final ViewMain viewMain;
    private ServerThread serverThread;

    public ControllerMain(ViewMain viewMain) {
        this.viewMain = viewMain;

        init();

        addListeners();
    }

    public void open() {
        viewMain.setVisible(true);
    }

    private void init() {
        viewMain.setLocationRelativeTo(null);
        viewMain.getjMenuItemStop().setEnabled(false);
    }

    private void addListeners() {
        viewMain.getjMenuItemStart().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        viewMain.getjMenuItemStop().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        viewMain.getjMenuItemDatabase().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerConfigDatabase controllerConfigDatabase
                        = new ControllerConfigDatabase(new ViewConfigDatabase(viewMain, true));
                controllerConfigDatabase.open();
            }
        });
        viewMain.getjMenuItemCommunication().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerConfigCommunication controllerConfigCommunication
                        = new ControllerConfigCommunication(new ViewConfigCommunication(viewMain, true));
                controllerConfigCommunication.open();
            }
        });
        viewMain.getjMenuItemLoggedUsers().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControllerLoggedUsers controllerLoggedUsers
                        = new ControllerLoggedUsers(new ViewLoggedUsers(viewMain, true));
                controllerLoggedUsers.open();
            }
        });
    }

    private void start() {
        if (serverThread == null || !serverThread.isAlive()) {
            try {
                serverThread = new ServerThread();
                serverThread.start();

                viewMain.getjMenuItemStart().setEnabled(false);
                viewMain.getjMenuItemStop().setEnabled(true);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.err.println("Server thread is started");
        }
    }

    private void stop() {
        try {
            if (serverThread.getServerSocket() != null && serverThread.getServerSocket().isBound()) {
                serverThread.getServerSocket().close();
                viewMain.getjMenuItemStart().setEnabled(true);
                viewMain.getjMenuItemStop().setEnabled(false);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
