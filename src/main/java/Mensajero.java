import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Mensajero implements Runnable {

    private final ServidorGeneral servidorGeneral;
    private final Socket socket;
    private String nombre;

    public Mensajero(Socket socket, ServidorGeneral servidorGeneral) {
        this.servidorGeneral = servidorGeneral;
        this.socket = socket;
    }

    public String getNombre() {
        return nombre;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            //1o cogemos el nombre
            String nombre = dis.readUTF();
            this.nombre = nombre;
            servidorGeneral.mandarMensajeConexion(nombre);

            //2o bucle recibiendo menasjes
            while (true) {
                servidorGeneral.mandarMensajeATodos(dis.readUTF(), nombre);
            }
        } catch (IOException ignored) {
        } finally {
            //3o Mandamos mensaje de salida y cerramos conexion
            try {
                servidorGeneral.mandarMensajeSalida(nombre);
            } catch (IOException ignored) {
            }
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
