import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class MessageChecker extends Thread {

    public void run(){
        try {
            String message;
            while (true){
                MyServerInt myRemoteObject = (MyServerInt) Naming.lookup("//192.168.1.112/ABC");
                message = myRemoteObject.getMessage();
                if (message != null){
                    System.out.printf(message + "\n");
                }
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
