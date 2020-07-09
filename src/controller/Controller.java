/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.User;
import java.util.ArrayList;
import java.util.List;
import thread.ClientThread;
import util.Keys;

/**
 *
 * @author marko
 */
public class Controller {

    private static Controller instance;
    private final List<ClientThread> clients;

    private Controller() {
        clients = new ArrayList<>();
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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        for (ClientThread client : Controller.getInstance().getClients()) {
            User user = (User) client.getMap().get(Keys.USER);
            if (user != null) {
                users.add(user);
            }
        }

        return users;
    }
}
