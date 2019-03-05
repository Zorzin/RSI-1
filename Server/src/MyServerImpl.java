import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyServerImpl extends UnicastRemoteObject implements MyServerInt {
    int i = 0;
    String serverName;
    String clientName;

    Queue<String> messageQueue = new LinkedList<>();

    protected MyServerImpl() throws RemoteException {
        super();
    }

    @Override
    public String getDescription(String text) throws RemoteException {
        i++;
        System.out.println("MyServerImpl.getDescription: " + text + " " + i);
        return "getDescription: " + text + " " + i;
    }

    @Override
    public String calculator(String text) throws RemoteException {
        System.out.println("Recieved from client: " + text);
        String result = "error";

        if( text.matches("^([\\d]+([\\.]{1}[\\d]+)?)[ ]*[+-\\/\\*]{1}[ ]*([\\d]+([\\.]{1}[\\d]+)?)[ ]*$") ){
            String[] splitted = text.split("[+*/-]");

            double a = Double.parseDouble(splitted[0]);
            double b = Double.parseDouble(splitted[1]);

            char operation = text.charAt(splitted[0].length());

            switch (operation){
                case '+':
                    result = String.valueOf(a+b);
                    break;
                case '-':
                    result = String.valueOf(a-b);
                    break;
                case '*':
                    result = String.valueOf(a*b);
                    break;
                case '/':
                    result = String.valueOf(a/b);
                    break;
                default:
                    break;
            }
        } else {
            result = "invalid format";
        }
        System.out.println("Sended to client:  " + result);
        return result;
    }

    @Override
    public List<Product> getProducts() throws RemoteException {
        List<Product> products = new ArrayList<>();

        String dbUrl = "jdbc:sqlite:C:/Users/s.bakunowicz/RSIProjects/RMI/rmidb.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Products;");

            while ((resultSet.next())){
                Product product = new Product();
                product.id = resultSet.getInt("Id");
                product.name = resultSet.getString("Name");
                product.price =  resultSet.getDouble("Price");
                products.add(product);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProductByName(String name) throws RemoteException{
        Product product = null;

        String dbUrl = "jdbc:sqlite:C:/Users/s.bakunowicz/RSIProjects/RMI/rmidb.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Products WHERE Name LIKE '%" + name + "%' LIMIT 1");

            while ( resultSet.next() ) {
                product = new Product();
                product.id = resultSet.getInt("id");
                product.name = resultSet.getString("name");
                product.price =  resultSet.getDouble("price");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public void setServerName(String name) throws RemoteException {
        this.serverName = name;
        if (this.serverName != null && this.clientName != null){
            setInitMessage();
        }
    }

    public void setClientName(String name) throws RemoteException {
        this.clientName = name;
        if (this.serverName != null && this.clientName != null){
            setInitMessage();
        }
    }

    public void sendClientMessage(String message) throws RemoteException {
        System.out.println("[" + clientName + "] " + message);
    }

    public void sendServerMessage(String message) throws RemoteException {
        messageQueue.add("[" + serverName + "] " +message);
        System.out.println(message);
    }

    public String getMessage() throws RemoteException {
        return messageQueue.poll();
    }

    private void setInitMessage(){
        String message = "[Server] Chat remote system is ready";
        messageQueue.add(message);
        System.out.println(message);
    }
}