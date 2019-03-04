import java.rmi.Naming;
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
                System.out.printf("Wprowadź działanie:  ");
                text = in.nextLine();
                result = myRemoteObject.calculator(text);
                System.out.println("Wynik: " + result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}