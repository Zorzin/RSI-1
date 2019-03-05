import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface MyServerInt extends Remote{
    String getDescription(String text) throws RemoteException;
    String calculator(String text) throws RemoteException;
    List<Product> getProducts() throws RemoteException;
    Product getProductByName(String name) throws RemoteException;
}