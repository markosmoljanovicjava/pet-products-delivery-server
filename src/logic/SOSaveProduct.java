/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Product;

/**
 *
 * @author marko
 */
public class SOSaveProduct extends SystemOperation {

    public SOSaveProduct(Product product) {
        domainObject = product;
        // validator = new ValidatorProduct();
    }

    @Override
    protected void operation() throws Exception {
        databaseBroker.save(domainObject);
    }

}
