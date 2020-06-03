/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import javax.swing.JOptionPane;
import ui.view.ViewConfigCommunication;

/**
 *
 * @author marko
 */
public class ControllerConfigCommunication {

    private final ViewConfigCommunication viewConfigCommunication;

    public ControllerConfigCommunication(ViewConfigCommunication viewConfigCommunication) {
        this.viewConfigCommunication = viewConfigCommunication;

        init();
    }

    private void init() {
        viewConfigCommunication.setLocationRelativeTo(null);

        fillForm();

        addListeners();
    }

    private void fillForm() {
        try (FileInputStream input = new FileInputStream("socket.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            viewConfigCommunication.getjTextFieldPort().setText(properties.getProperty("port"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void open() {
        viewConfigCommunication.setVisible(true);
    }

    private void addListeners() {
        viewConfigCommunication.getjButtonSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileOutputStream output = new FileOutputStream("socket.properties")) {
                    Properties properties = new Properties();

                    String port = viewConfigCommunication.getjTextFieldPort().getText();
                    properties.setProperty("port", port);
                    properties.store(output, null);

                    JOptionPane.showMessageDialog(null, "Saved successfully!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
