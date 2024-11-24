// Clase Ventana_ChatCliente


package com.mycompany.Controlador_ChatCliente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @Author Equipo1
 */

/**
 * La clase Ventana_ChatCliente maneja la interfaz gráfica de usuario (GUI) para un Chat.
 */

public class Ventana_ChatCliente {

    private JFrame frame;
    private JTextField textField;
    private JTextArea messageArea;
    private DefaultListModel<String> userListModel;
    private JList<String> userList;
    private String selectedUser;
    private Map<String, String> userImages;
    private JLabel messageLabel;
    private JButton emojiButton;
    private JButton logoutButton;

    /**
     * Constructor que inicializa la vista del cliente de chat.
     */
    public Ventana_ChatCliente() {
        frame = new JFrame("Cliente de Chat");
        frame.setLayout(new BorderLayout(10, 10));

        // Configuración del campo de texto y etiqueta
        messageLabel = new JLabel("Escriba su mensaje:");
        textField = new JTextField(30);
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        textField.setEditable(false);

        // Botón de emojis
        emojiButton = new JButton("😊");
        emojiButton.setMargin(new Insets(5, 5, 5, 5));
        emojiButton.addActionListener(e -> showEmojiMenu());

        // Botón de cerrar sesión
        logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setMargin(new Insets(5, 5, 5, 5));
        logoutButton.addActionListener(e -> handleLogout());

        // Panel de entrada de texto
        JPanel inputPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(emojiButton);
        buttonPanel.add(logoutButton);
        inputPanel.add(messageLabel, BorderLayout.NORTH);
        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        // Configuración del área de mensajes
        messageArea = new JTextArea(8, 40);
        messageArea.setBorder(BorderFactory.createCompoundBorder(
                messageArea.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        // Configuración del modelo de lista de usuarios
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userImages = new HashMap<>();

        // Configuración del panel de lista de usuarios
        JPanel userListPanel = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("Usuarios");
        userLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        userListPanel.add(userLabel, BorderLayout.NORTH);
        userListPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
        userListPanel.setPreferredSize(new Dimension(200, 0));

        // Configuración del renderer de celdas para mostrar imágenes de usuario
        userList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String username = (String) value;
                String imagePath = userImages.get(username);

                if (imagePath != null) {
                    try {
                        BufferedImage originalImage = ImageIO.read(new File(imagePath));
                        Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                        BufferedImage roundedImage = makeRoundedCorner(scaledImage, 50);
                        label.setIcon(new ImageIcon(roundedImage));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                label.setText(username); // Mostrar el nombre del usuario

                return label;
            }
        });

        // Listener para la selección de usuario
        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedUser = userList.getSelectedValue();
            }
        });

        // Añadir componentes al frame
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.getContentPane().add(userListPanel, BorderLayout.EAST);
        frame.pack();
    }

    /**
     * Muestra un menú de selección de emojis.
     */
    private void showEmojiMenu() {
        JPopupMenu emojiMenu = new JPopupMenu();
        String[] emojis = {"😊", "😂", "😍", "😢", "👍", "🙌"};
        for (String emoji : emojis) {
            JMenuItem emojiItem = new JMenuItem(emoji);
            emojiItem.addActionListener(e -> textField.setText(textField.getText() + emoji));
            emojiMenu.add(emojiItem);
        }
        emojiMenu.show(emojiButton, emojiButton.getWidth(), emojiButton.getHeight());
    }

    /**
     * Maneja la acción de cerrar sesión.
     */
    private void handleLogout() {
        // Aquí puedes agregar el código necesario para manejar el cierre de sesión
        JOptionPane.showMessageDialog(frame, "Cerrando sesión...");
        // Por ejemplo, cerrar la ventana actual:
        frame.dispose();
        // También puedes realizar otras acciones necesarias para el cierre de sesión
    }

    /**
     * Método para hacer las esquinas de una imagen redondeadas.
     *
     * @param image La imagen original.
     * @param diameter El diámetro del círculo de salida.
     * @return Una imagen con esquinas redondeadas.
     */
    private BufferedImage makeRoundedCorner(Image image, int diameter) {
        BufferedImage output = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        // Hacer la imagen circular
        g2.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
        g2.drawImage(image, 0, 0, diameter, diameter, null);
        g2.dispose();

        return output;
    }

    /**
     * Devuelve el frame de la vista del cliente.
     *
     * @return El JFrame de la vista del cliente.
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * Devuelve el texto del campo de texto.
     *
     * @return El texto del campo de texto.
     */
    public String getTextFieldText() {
        return textField.getText();
    }

    /**
     * Limpia el campo de texto.
     */
    public void clearTextField() {
        textField.setText("");
    }

    /**
     * Añade un mensaje al área de mensajes.
     *
     * @param message El mensaje a añadir.
     */
    public void appendMessage(String message) {
        String timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
        messageArea.append("[" + timeStamp + "] " + message + "\n");
    }

    /**
     * Añade un ActionListener al campo de texto.
     *
     * @param listener El ActionListener a añadir.
     */
    public void addTextFieldActionListener(ActionListener listener) {
        textField.addActionListener(listener);
    }

    /**
     * Establece el estado de conexión en el título de la ventana.
     *
     * @param status El estado de conexión.
     */
    public void setConnectionStatus(String status) {
        frame.setTitle("Cliente de Chat - " + status);
    }

    /**
     * Establece si el campo de texto es editable.
     *
     * @param editable true si el campo de texto debe ser editable, false en
     * caso contrario.
     */
    public void setTextFieldEditable(boolean editable) {
        textField.setEditable(editable);
    }

    /**
     * Devuelve el usuario seleccionado en la lista de usuarios.
     *
     * @return El nombre del usuario seleccionado.
     */
    public String getSelectedUser() {
        return selectedUser;
    }

    /**
     * Añade un usuario a la lista de usuarios.
     *
     * @param user El nombre del usuario a añadir.
     * @param imagePath La ruta de la imagen del usuario.
     */
    public void addUser(String user, String imagePath) {
        userListModel.addElement(user);
        if (imagePath != null) {
            userImages.put(user, imagePath);
        }
    }

    /**
     * Limpia la lista de usuarios y las imágenes de usuario.
     */
    public void clearUsers() {
        userListModel.clear();
        userImages.clear();
    }

    /**
     * Muestra la ventana del cliente de chat.
     */
    public void show() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Permite al usuario seleccionar una imagen utilizando un JFileChooser.
     *
     * @return La ruta de la imagen seleccionada, o null si no se seleccionó
     * ninguna imagen.
     */
    public String selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }
}
