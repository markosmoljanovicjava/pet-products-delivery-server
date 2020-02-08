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
public class SystemOperationUpdateProduct extends AbstractSystemOperation {

    public SystemOperationUpdateProduct(Product product) {
        domainObject = product;
    }

    @Override
    protected void operation() throws Exception {
        databaseBroker.update(domainObject);
    }

}
