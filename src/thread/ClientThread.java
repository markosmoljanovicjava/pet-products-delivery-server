/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import controller.Controller;
import domain.Contract;
import domain.Customer;
import domain.Manufacturer;
import domain.Product;
import domain.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import logic.SystemOperationSaveProduct;
import logic.AbstractSystemOperation;
import logic.SystemOperationDeleteProduct;
import logic.SystemOperationGetAllCustomers;
import logic.SystemOperationGetAllProducts;
import logic.SystemOperationGetAllProductsForManufacturer;
import logic.SystemOperationLogin;
import logic.SystemOperationSaveContract;
import logic.SystemOperationUpdateProduct;
import logic.SystsemOperationGetAllManufacturers;
import transfer.RequestObject;
import transfer.ResponseObject;
import util.Keys;
import util.Operation;
import util.ResponseStatus;

/**
 *
 * @author marko
 */
public class ClientThread extends Thread {

    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;

    private final Map<Integer, Object> map;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        map = new HashMap();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                RequestObject requestObject = (RequestObject) objectInputStream.readObject();
                ResponseObject responseObject = handleRequest(requestObject);
                objectOutputStream.writeObject(responseObject);
            } catch (IOException | ClassNotFoundException ex) {
                try {
                    socket.close();
                    System.out.println(String.format("%s disconnected!", map.get(Keys.USER)));
                    Controller.getInstance().getClients().remove(this);
                } catch (IOException ex1) {
                    ex1.printStackTrace();
                }
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public Map<Integer, Object> getMap() {
        return map;
    }

    private ResponseObject handleRequest(RequestObject requestObject) {
        int operation = requestObject.getOperation();
        switch (operation) {
            case Operation.LOGIN:
                return login((User) requestObject.getData());
            case Operation.SAVE_PRODUCT:
                return saveProduct((Product) requestObject.getData());
            case Operation.GET_ALL_MANUFACTURERS:
                return getAllManufacturers();
            case Operation.UPDATE_PRODUCT:
                return updateProducts((Product) requestObject.getData());
            case Operation.GET_ALL_PRODUCTS:
                return getAllProducts();
            case Operation.DELETE_PRODUCT:
                return deleteProduct((Product) requestObject.getData());
            case Operation.GET_ALL_CUSTOMERS:
                return getAllCustomers();
            case Operation.GET_ALL_PRODUCST_FOR_MANUFACTURER:
                return getAllProductsForManufacturer((Product) requestObject.getData());
            case Operation.SAVE_CONTRACT:
                return saveContract((Contract) requestObject.getData());
        }
        return null;
    }

    private ResponseObject login(User user) {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationLogin(user);
            so.execute();
            User user1 = (User) so.getDomainObject();
            responseObject.setData(user1);
            responseObject.setStatus(ResponseStatus.SUCCESS);
            map.put(Keys.USER, user1);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject saveProduct(Product product) {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationSaveProduct(product);
            so.execute();
            responseObject.setData(so.getDomainObject());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject getAllManufacturers() {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystsemOperationGetAllManufacturers(new Manufacturer());
            so.execute();
            responseObject.setData(so.getDomainObjects());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject updateProducts(Product product) {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationUpdateProduct(product);
            so.execute();
            responseObject.setData(so.getDomainObject());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject getAllProducts() {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationGetAllProducts(new Product());
            so.execute();
            responseObject.setData(so.getDomainObjects());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject deleteProduct(Product product) {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationDeleteProduct(product);
            so.execute();
            responseObject.setData(so.getDomainObject());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject getAllCustomers() {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationGetAllCustomers(new Customer());
            so.execute();
            responseObject.setData(so.getDomainObjects());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject getAllProductsForManufacturer(Product product) {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationGetAllProductsForManufacturer(product);
            so.execute();
            responseObject.setData(so.getDomainObjects());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject saveContract(Contract contract) {
        ResponseObject responseObject = new ResponseObject();
        try {
            AbstractSystemOperation so = new SystemOperationSaveContract(contract);
            so.execute();
            responseObject.setData(so.getDomainObject());
            responseObject.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception ex) {
            ex.printStackTrace();
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

}
