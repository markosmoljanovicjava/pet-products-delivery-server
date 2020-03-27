/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import domain.Contract;
import domain.ContractItem;

/**
 *
 * @author marko
 */
public class SystemOperationSaveContract extends AbstractSystemOperation {

    public SystemOperationSaveContract(Contract contract) {
        domainObject = contract;
    }

    @Override
    protected void operation() throws Exception {
        domainObject = databaseBroker.insert(domainObject);
        Contract contract = (Contract) domainObject;
        for (ContractItem contractItem : contract.getContractItems()) {
            databaseBroker.insert(contractItem);
        }
    }

}
