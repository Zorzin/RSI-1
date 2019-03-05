import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface MyServerInt extends Remote{
    String getDescription(String text) throws RemoteException;
    String calculator(String text) throws RemoteException;
    List<Product> getProducts() throws RemoteException;
    Product getProductByName(String name) throws RemoteException;
    void setServerName(String name) throws RemoteException;
    void setClientName(String name) throws RemoteException;
    void sendClientMessage(String message) throws RemoteException;
    void sendServerMessage(String message) throws RemoteException;
    String getMessage() throws RemoteException;
}