package cliente;

import server.ServidorGeneral;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClienteEscritorBase {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", ServidorGeneral.PUERTO);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String nombre;
        do {
            System.out.print("Introduce tu nombre: ");
            nombre = in.next();
            if (nombre.equalsIgnoreCase("")) {
                System.out.println("Nombre invalido!!");
            }
        } while (nombre.equalsIgnoreCase(""));
        //1o mandamos el nombre
        dos.writeUTF(nombre);

        //2o mandamos mensajes
        while (true) {
            dos.writeUTF(in.nextLine());
        }
    }
}
