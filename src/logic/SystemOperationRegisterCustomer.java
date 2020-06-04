/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Customer;

/**
 *
 * @author marko
 */
public class SystemOperationRegisterCustomer extends AbstractSystemOperation{

    public SystemOperationRegisterCustomer(Customer customer) {
        domainObject = customer;
    }
    
    @Override
    protected void operation() throws Exception {
        databaseBroker.insert(domainObject);
    }
    
}
