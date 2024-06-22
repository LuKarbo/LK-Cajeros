package Views;

import DB.DBCajero;
import Models.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {

    private JPanel mainPanel;
    private JLabel userName;
    private JTextField userNameField;
    private JPasswordField userPasswordField;
    private JLabel userPassword;
    private JButton loginButton;
    private JButton goToRegisterButton;
    private JLabel alertMSG;

    public Login(){
        alertMSG.setVisible(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Login Funciona");
                if(userNameField.getText() != null && userPasswordField.getPassword() != null){
                    String pass = new String(userPasswordField.getPassword());
                    Cliente ususario = null;
                    try {
                        ususario = new Cliente();
                        if(ususario.login(userNameField.getText(),pass))
                        {
                            alertMSG.setVisible(false);
                            // ir al home
                            // System.out.println("Ingresar al home");
                            // System.out.println("Nombre: " + ususario.getname());
                            // System.out.println("Saldo: " + ususario.getSaldo());
                            // System.out.println("LVL Permisos: " + ususario.getId_permisos());
                            Home home = new Home(ususario);
                            setVisible(false);
                            home.setVisible(true);
                        }
                        else{
                            alertMSG.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            }
        });

        goToRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Go to Register Funciona");
                Register register = new Register();
                setVisible(false);
                register.setVisible(true);
            }
        });

        setTitle("User Login");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
