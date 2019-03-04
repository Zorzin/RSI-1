import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyServerImpl extends UnicastRemoteObject implements MyServerInt {
    int i = 0;
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

            /*int i=0;
            for(;i<text.length(); i++) {
                if( Character.isDigit(text.charAt(i)) || text.charAt(i) == '.' ) {
                    aString += text.charAt(i);
                } else {
                    break;
                }
            }
            for(;i<text.length(); i++) {
                if( text.charAt(i) == '+' || text.charAt(i) == '-' || text.charAt(i) == '*' || text.charAt(i) == '/') {
                    operation += text.charAt(i);
                } else if( Character.isDigit(text.charAt(i)) ) {
                    break;
                }
            }
            for(;i<text.length(); i++) {
                if( Character.isDigit(text.charAt(i)) || text.charAt(i) == '.' ) {
                    bString += text.charAt(i);
                } else {
                    break;
                }
            }*/
            //System.out.println("Value in a: " + as);
            //System.out.println("Value in b: " + bs);
            //System.out.println("Value in op: " + op);
            /*ouble a = Double.parseDouble(aString);
            double b = Double.parseDouble(bString);
            switch (operation) {
                case "+":
                    result = String.valueOf(a + b);
                    break;
                case "-":
                    result = String.valueOf(a - b);
                    break;
                case "*":
                    result = String.valueOf(a * b);
                    break;
                case "/":
                    result = String.valueOf(b == 0 ? 0 : a/b);
                    break;
                default:
                    break;
            }*/
        } else {
            result = "invalid format";
        }
        System.out.println("Sended to client:  " + result);
        return result;
    }
}