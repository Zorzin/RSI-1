import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

public class MyServerMain {

    public static void main(String[] args) {
        try
        {
            //System.setProperty("java.rmi.server.hostname", "192.168.1.112");
            System.setProperty("java.security.policy", "security.policy");

            if (System.getSecurityManager() == null)
            {
                System.setSecurityManager(new SecurityManager());
            }

            System.setProperty("java.rmi.server.codebase","file:C:/Users/s.bakunowicz/RSIProjects/RMIServer/out/production/Server/");
            //System.setProperty("java.rmi.server.codebase", "http://192.168.1.102/szymon/");
            System.out.println("Codebase: " + System.getProperty("java.rmi.server.codebase"));

            LocateRegistry.createRegistry(1099);
            MyServerImpl obj1 = new MyServerImpl();
            Naming.rebind("//localhost/ABC", obj1);
            System.out.println("Enter your name and press Enter:");
            Scanner in = new Scanner(System.in);
            String name = in.nextLine();
            obj1.setServerName(name);

            while (true)
            {
                in = new Scanner(System.in);
                String message = in.nextLine();
                if (message != null && !message.isEmpty()){
                    obj1.sendServerMessage(message);
                }
            }

        } catch (RemoteException | MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

}
