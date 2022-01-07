package cliente;

import server.ServidorGeneral;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteReceptorBase {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", ServidorGeneral.PUERTO);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        while(true) {
            String mensaje = dis.readUTF();
            System.out.println(mensaje);
        }
    }
}