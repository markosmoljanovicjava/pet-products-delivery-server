/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Product;
import validator.impl.ValidatorProduct;

/**
 *
 * @author marko
 */
public class SystemOperationSaveProduct extends SystemOperation {

    public SystemOperationSaveProduct(Product product) {
        domainObject = product;
        validator = new ValidatorProduct();
    }

    @Override
    protected void operation() throws Exception {
        databaseBroker.insert(domainObject);
    }

}
