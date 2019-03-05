import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        System.setProperty("java.security.policy", "security.policy");
        System.setSecurityManager(new SecurityManager());
        try {
            MyServerInt myRemoteObject = (MyServerInt) Naming.lookup("//localhost/ABC");
            String text = "Hallo :-)";
            String result = myRemoteObject.getDescription(text);
            System.out.println("Wysłano do servera: " + text);
            System.out.println("Otrzymana z serwera odpowiedź: " + result);
            Scanner in = new Scanner(System.in);

            while (true){
                System.out.printf("c - calculator, ch - chat, p - all products from db, pm - product from db by name \n");
                System.out.printf("Wybierz program:  \n");
                String command = in.nextLine();
                switch (command){
                    case "c":
                        Calculator(myRemoteObject);
                        break;
                    case "ch":
                        Chat(myRemoteObject);
                        break;
                    case "p":
                        AllProducts(myRemoteObject);
                        break;
                    case "pm":
                        ProductByName(myRemoteObject);
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ProductByName(MyServerInt myRemoteObject) throws RemoteException, SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("Podaj nazwe produktu:");
        String text = in.nextLine();
        Product result = myRemoteObject.getProductByName(text);
        if (result != null){
            System.out.println("Znaleziono produkt:");
            System.out.println(result.toString());
        }
        else{
            System.out.println("Brak produktu o podanej nazwie");
        }
    }

    private static void AllProducts(MyServerInt myRemoteObject) throws RemoteException, SQLException {
        List<Product> result = myRemoteObject.getProducts();
        if (!result.isEmpty()){
            System.out.println("Znaleziono produkty:");
            for (Product product : result){
                System.out.println(product.toString());
            }
        }
        else{
            System.out.println("Brak produktów");
        }
    }

    private static void Chat(MyServerInt myRemoteObject) {

    }

    private static void Calculator(MyServerInt myRemoteObject) throws RemoteException {
        Scanner in = new Scanner(System.in);
        System.out.printf("Wprowadź działanie:  ");
        String text = in.nextLine();
        String result = myRemoteObject.calculator(text);
        System.out.println("Wynik: " + result);
    }
}