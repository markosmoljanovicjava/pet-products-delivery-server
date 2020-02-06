/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.User;

/**
 *
 * @author marko
 */
public class SystemOperationLogin extends SystemOperation {

    public SystemOperationLogin(User user) {
        domainObject = user;
    }

    @Override
    protected void operation() throws Exception {
        databaseBroker.equals(domainObject);
    }

}
