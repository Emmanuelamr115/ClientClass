//Clase Modelo_ChatCliente

package com.mycompany.Controlador_ChatCliente;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @Author Equipo1
 */

/**
 * La clase Modelo_ChatCliente maneja la lógica de datos del chat,
 * incluyendo la conexión al servidor y la gestión de usuarios conectados.
 */

public class Modelo_ChatCliente {
    private String serverAddress;
    private int serverPort;
    private BufferedReader in;
    private PrintWriter out;
    private List<String> connectedUsers;

    /**
     * Constructor que inicializa el modelo con la dirección del servidor y el puerto dados.
     *
     * @param serverAddress La dirección del servidor
     * @param serverPort El puerto del servidor
     */
    public Modelo_ChatCliente(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.connectedUsers = new ArrayList<>();
    }

    /**
     * Conecta al cliente con el servidor.
     *
     * @throws IOException Si ocurre un error de E/S
     */
    public void connectToServer() throws IOException {
        Socket socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Obtiene el flujo de entrada del servidor.
     *
     * @return El flujo de entrada
     */
    public BufferedReader getInputStream() {
        return in;
    }

    /**
     * Obtiene el flujo de salida al servidor.
     *
     * @return El flujo de salida
     */
    public PrintWriter getOutputStream() {
        return out;
    }

    /**
     * Añade un usuario a la lista de usuarios conectados.
     *
     * @param user El nombre del usuario
     */
    public void addUser(String user) {
        connectedUsers.add(user);
    }

    /**
     * Elimina un usuario de la lista de usuarios conectados.
     *
     * @param user El nombre del usuario
     */
    public void removeUser(String user) {
        connectedUsers.remove(user);
    }

    /**
     * Obtiene la lista de usuarios conectados.
     *
     * @return La lista de usuarios conectados
     */
    public List<String> getConnectedUsers() {
        return connectedUsers;
    }
}