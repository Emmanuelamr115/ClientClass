/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cliente;

/**
 *
 * @author Emmanuel
 */

import java.io.*;
import java.net.*;

public class Cliente {
    private Socket socket; // Conexión al servidor
    private BufferedReader entrada; // Para recibir mensajes del servidor
    private PrintWriter salida; // Para enviar mensajes al servidor

    // Método para conectar al servidor
    public void conectarAlServidor(String host, int puerto) {
        try {
            socket = new Socket(host, puerto);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Conectado al servidor en " + host + ":" + puerto);

            // Hilo para escuchar mensajes del servidor
            new Thread(new HiloEscucha(entrada)).start();

            // Enviar mensajes al servidor
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            String mensaje;
            while ((mensaje = teclado.readLine()) != null) {
                salida.println(mensaje);
            }
        } catch (IOException e) {
            System.err.println("Error al conectar al servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) { //Clase main de cliente
        Cliente cliente = new Cliente();
        cliente.conectarAlServidor("127.0.0.1", 12345);
    }
}
