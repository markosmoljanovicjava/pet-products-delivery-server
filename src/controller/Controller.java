/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import thread.ClientThread;

/**
 *
 * @author marko
 */
public class Controller {

    private static Controller instance;

    private final List<ClientThread> clients;

    private Controller() {
        clients = new ArrayList();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public List<ClientThread> getClients() {
        return clients;
    }
}
