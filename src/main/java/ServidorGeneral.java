import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorGeneral {
    public final static int PUERTO = 50000;

    private ExecutorService pool;
    private ServerSocket socket;
    private List<Mensajero> mensajeros;

    public ServidorGeneral() throws IOException {
        socket = new ServerSocket(PUERTO);
        pool = Executors.newCachedThreadPool();
        mensajeros = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        ServidorGeneral servidor = new ServidorGeneral();
        servidor.arranca();
    }

    public static boolean isTCPPortAvailable(int port) {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }
        return false;
    }

    private void arranca() throws IOException {
        while (true) {
            Socket socketAccept = socket.accept();
            Mensajero mensajero = new Mensajero(socketAccept, this);
            mensajeros.add(mensajero);
            pool.execute(mensajero);
        }
    }

    public void mandarMensajeATodos(String mensaje, String nombre) throws IOException {
        if (mensaje == null || mensaje.equalsIgnoreCase("")) return;
        mandarMensaje(nombre, mensaje);
    }

    public void mandarMensajeConexion(String nombre) throws IOException {
        mandarMensaje(nombre, "CONEXION");
    }

    public void mandarMensajeSalida(String nombre) throws IOException {
        Mensajero mensajeroActual = null;
        for (Mensajero mensajero : mensajeros) {
            mensajeroActual = mensajero;
            if (isTCPPortAvailable(mensajero.getSocket().getPort())) {
                //Si esta disponible es pq se ha cancelado la conexion
                break;
            }
        }
        mensajeros.remove(mensajeroActual);

        //Para que no se muestre por pantalla si se va un Clientereceptor
        if (mensajeroActual == null || mensajeroActual.getNombre() == null || mensajeroActual.getNombre().equalsIgnoreCase("")) {
            return;
        }

        mandarMensaje(nombre, "HA SALIDO");
    }

    private void mandarMensaje(String nombre, String mensaje) throws IOException {
        for (Mensajero mensajero : mensajeros) {
            Socket socket = mensajero.getSocket();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(nombre + ": " + mensaje);
        }
        System.out.println(nombre + ": " + mensaje);
    }
}
