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
public class SystemOperationGetAllCustomers extends AbstractSystemOperation{

    public SystemOperationGetAllCustomers(Customer customer) {
        domainObject = customer;
    }

    @Override
    protected void operation() throws Exception {
        domainObjects = databaseBroker.getAll(domainObject);
    }
    
}
