/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.components;

import domain.User;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author marko
 */
public class UserTableModel extends AbstractTableModel {

    private List<User> users;
    private final String columnsNames[] = {"First Name", "Last Name", "Username"};

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }

    @Override
    public String getColumnName(int i) {
        return columnsNames[i];
    }

    @Override
    public Object getValueAt(int i, int j) {
        User user = users.get(i);
        switch (j) {
            case 0:
                return user.getFirstName();
            case 1:
                return user.getLastName();
            case 2:
                return user.getUsername();
            default:
                return "n/a";
        }
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void refreash() {
        fireTableDataChanged();
    }

}
