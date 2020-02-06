/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator.impl;

import domain.Product;
import validator.Validator;

/**
 *
 * @author marko
 */
public class ValidatorProduct implements Validator {

    @Override
    public void validate(Object object) throws Exception {
        try {
            Product product = (Product) object;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
