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
import java.util.Properties;
import javax.swing.JOptionPane;
import ui.view.ViewConfigDatabase;

/**
 *
 * @author marko
 */
public class ControllerConfigDatabase {

    private final ViewConfigDatabase viewConfigDatabase;

    public ControllerConfigDatabase(ViewConfigDatabase viewConfigDB) {
        this.viewConfigDatabase = viewConfigDB;

        init();

        addListeners();
    }

    private void init() {
        viewConfigDatabase.setLocationRelativeTo(null);

        fillForm();

    }

    void open() {
        viewConfigDatabase.setVisible(true);
    }

    private void addListeners() {
        viewConfigDatabase.getjButtonSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProperties();
            }
        });
    }

    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("db.properties")) {
            Properties properties = new Properties();

            String driver = viewConfigDatabase.getjTextFieldDriver().getText();
            String url = viewConfigDatabase.getjTextFieldURL().getText();
            String user = viewConfigDatabase.getjTextFieldUser().getText();
            String password = viewConfigDatabase.getjTextFieldPassword().getText();

            properties.setProperty("driver", driver);
            properties.setProperty("url", url);
            properties.setProperty("user", user);
            properties.setProperty("password", password);

            properties.store(fileOutputStream, null);
            JOptionPane.showMessageDialog(null, "Saved Successfully!");
            viewConfigDatabase.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void fillForm() {
        try (FileInputStream fileInputStream = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);

            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            viewConfigDatabase.getjTextFieldDriver().setText(driver);
            viewConfigDatabase.getjTextFieldURL().setText(url);
            viewConfigDatabase.getjTextFieldUser().setText(user);
            viewConfigDatabase.getjTextFieldPassword().setText(password);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
