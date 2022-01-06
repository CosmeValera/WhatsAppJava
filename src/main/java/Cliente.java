import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
}

class ClienteEscritorBase {
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
        while(true) {
            dos.writeUTF(in.nextLine());
        }
    }
}

class ClienteReceptorBase {
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

class ClienteEscritor1 {
    public static void main(String[] args) throws IOException{
        ClienteEscritorBase.main(new String[0]);
    }
}

class ClienteReceptor1 {
    public static void main(String[] args) throws IOException {
        ClienteReceptorBase.main(new String[0]);
    }
}

class ClienteEscritor2 {
    public static void main(String[] args) throws IOException{
        ClienteEscritorBase.main(new String[0]);
    }
}

class ClienteReceptor2 {
    public static void main(String[] args) throws IOException {
        ClienteReceptorBase.main(new String[0]);
    }
}
