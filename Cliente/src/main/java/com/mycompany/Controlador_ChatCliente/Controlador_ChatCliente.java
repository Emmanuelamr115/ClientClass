//Clase Controlador_ChatCliente

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package com.mycompany.Controlador_ChatCliente;
 import javax.swing.*;
 import java.io.*;
 
 /**
  *
  * @Author Equipo1
  */
 
 /**
  * La clase Controlador_ChatCliente maneja la lógica de control para el chat del cliente,
  * gestionando la interacción entre el modelo y la vista.
  */
 
 public class Controlador_ChatCliente {
     private Modelo_ChatCliente model;
     private Ventana_ChatCliente view;
 
     /**
      * Constructor que inicializa el controlador con el modelo y la vista dados.
      *
      * @param model El modelo del cliente de chat
      * @param view La vista del cliente de chat
      */
     public Controlador_ChatCliente(Modelo_ChatCliente model, Ventana_ChatCliente view) {
         this.model = model;
         this.view = view;
 
         view.addTextFieldActionListener(e -> {
             String text = view.getTextFieldText();
             if (view.getSelectedUser() != null) {
                 model.getOutputStream().println("@" + view.getSelectedUser() + " " + text);
             } else {
                 model.getOutputStream().println(text);
             }
             view.clearTextField();
         });
     }
 
     /**
      * Conecta el cliente al servidor, actualiza la vista y escucha los mensajes del servidor.
      */
     public void connect() {
         try {
             model.connectToServer();
             view.setConnectionStatus("Conectado");
             view.setTextFieldEditable(true);
 
             new Thread(() -> {
                 try {
                     String line;
                     while ((line = model.getInputStream().readLine()) != null) {
                         if (line.startsWith("MESSAGE")) {
                             view.appendMessage(line.substring(8));
                         } else if (line.startsWith("USER_LIST")) {
                             String[] users = line.substring(10).split(" ");
                             view.clearUsers();
                             for (int i = 0; i < users.length; i++) {
                                 String username = users[i];
                                 String imagePath = null;
                                 if (i + 1 < users.length) {
                                     imagePath = users[i + 1];
                                     i++; // Saltar la siguiente iteración
                                 }
                                 view.addUser(username, imagePath);
                             }
                         } else if (line.startsWith("NAME_ACCEPTED")) {
                             view.setTextFieldEditable(true);
                         } else if (line.startsWith("NAME_EXISTS")) {
                             requestName();
                         }
                     }
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }).start();
 
             requestName();
         } catch (IOException e) {
             e.printStackTrace();
             view.setConnectionStatus("Fallo al conectar");
         }
     }
 
     /**
      * Solicita al usuario un nombre y una imagen opcional.
      */
     private void requestName() {
         String name = JOptionPane.showInputDialog(view.getFrame(), "Elige un nombre de usuario:", "Selección de nombre de usuario", JOptionPane.PLAIN_MESSAGE);
         if (name != null) {
             String imagePath = view.selectImage();
             if (imagePath != null) {
                 model.getOutputStream().println(name + " " + imagePath);
             } else {
                 model.getOutputStream().println(name);
             }
         }
     }
 
     /**
      * Método main para iniciar el cliente de chat.
      *
      * @param args Argumentos de línea de comandos
      */
     public static void main(String[] args) {
         String serverAddress = JOptionPane.showInputDialog("Introduce la dirección IP del servidor:");
         int serverPort = Integer.parseInt(JOptionPane.showInputDialog("Introduce el puerto:"));
 
         Modelo_ChatCliente model = new Modelo_ChatCliente(serverAddress, serverPort);
         Ventana_ChatCliente view = new Ventana_ChatCliente();
         Controlador_ChatCliente controller = new Controlador_ChatCliente(model, view);
 
         view.show();
         controller.connect();
     }
 }
 
 
 
 