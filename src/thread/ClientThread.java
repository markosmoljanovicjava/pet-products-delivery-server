/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

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
import logic.SystemOperationLogin;
import logic.SystsemOperationGetAllManufacturers;
import transfer.RequestObject;
import transfer.ResponseObject;
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

    private final Map<String, Object> map;

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
                System.out.println(ex.getMessage());
            }
        }
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
            map.put("user", user1);
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

    public Socket getSocket() {
        return socket;
    }

    public Map<String, Object> getMap() {
        return map;
    }

}
