/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import database.DatabaseBroker;
import domain.DomainObject;
import validator.Validator;

/**
 *
 * @author marko
 */
public abstract class AbstractSystemOperation {

    Validator validator;
    DatabaseBroker databaseBroker;
    DomainObject domainObject;

    public AbstractSystemOperation() {
        databaseBroker = new DatabaseBroker();
    }

    protected void checkPreconditions() throws Exception {
        if (validator != null) {
            validator.validate(domainObject);
        }
    }

    protected void connectStorage() throws Exception {
        databaseBroker.connect();
    }

    protected void disconnectStorage() throws Exception {
        databaseBroker.disconnect();
    }

    protected abstract void operation() throws Exception;

    public void execute() throws Exception {
        checkPreconditions();
        connectStorage();
        try {
            operation();
            databaseBroker.commit();
        } catch (Exception ex) {
            databaseBroker.rollback();
            throw ex;
        } finally {
            disconnectStorage();
        }
    }

    public DomainObject getDomainObject() {
        return domainObject;
    }

}
