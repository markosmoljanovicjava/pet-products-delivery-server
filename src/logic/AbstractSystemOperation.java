/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import database.DatabaseBroker;
import domain.DomainObject;
import java.util.List;
import validator.Validator;

/**
 *
 * @author marko
 */
public abstract class AbstractSystemOperation {

    protected Validator validator;
    protected final DatabaseBroker databaseBroker;
    protected DomainObject domainObject;
    protected List<DomainObject> domainObjects;

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

    public List<DomainObject> getDomainObjects() {
        return domainObjects;
    }

}
