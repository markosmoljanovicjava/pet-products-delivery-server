/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ui.controller.ControllerMain;
import ui.view.ViewMain;

/**
 *
 * @author marko
 */
public class Main {

    public static void main(String[] args) {
        ControllerMain controllerMain = new ControllerMain(new ViewMain());
        controllerMain.open();
        
    }
}
