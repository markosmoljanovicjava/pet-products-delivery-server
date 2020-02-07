/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Manufacturer;

/**
 *
 * @author marko
 */
public class SystsemOperationGetAllManufacturers extends AbstractSystemOperation {

    public SystsemOperationGetAllManufacturers(Manufacturer manufacturer) {
        domainObject = manufacturer;
    }

    @Override
    protected void operation() throws Exception {
        domainObjects = databaseBroker.getAll(domainObject);
    }

}
