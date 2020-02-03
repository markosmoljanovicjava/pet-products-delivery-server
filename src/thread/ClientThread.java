/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import domain.Product;
import domain.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import logic.SOSaveProduct;
import logic.SystemOperation;
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

    private User loginUser;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
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
        }
        return null;
    }

    private ResponseObject login(User user) {
        ResponseObject responseObject = new ResponseObject();
        try {
            User user1 = new User("Marko", "Smoljanovic");
            responseObject.setData(user1);
            responseObject.setStatus(ResponseStatus.SUCCESS);
            loginUser = user;
        } catch (Exception ex) {
            responseObject.setStatus(ResponseStatus.ERROR);
            responseObject.setErrorMessage(ex.getMessage());
        }
        return responseObject;
    }

    private ResponseObject saveProduct(Product product) {
        ResponseObject responseObject = new ResponseObject();
        try {
            SystemOperation so = new SOSaveProduct(product);
            so.execute();
            Product product1 = (Product) so.getDomainObject();
            responseObject.setStatus(ResponseStatus.SUCCESS);
            responseObject.setData(product1);

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

    public User getLoginUser() {
        return loginUser;
    }

}
