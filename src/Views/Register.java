package Views;

import Models.Cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register extends JFrame {
    private JPanel mainPanel;
    private JPasswordField userPasswordField;
    private JPasswordField userConfirmPasswordField;
    private JLabel userName;
    private JLabel userPassword;
    private JLabel userConfirmPassword;
    private JTextField userNameField;
    private JButton registerButton;
    private JButton goToLoginButton;
    private JLabel alerPassInc;
    private JLabel alertUserName;

    public Register(){
        alerPassInc.setVisible(false);
        alertUserName.setVisible(false);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass_1 = new String(userPasswordField.getPassword());
                String pass_2 = new String(userConfirmPasswordField.getPassword());
                if(pass_1.equals(pass_2)){
                    // System.out.println("Register Funciona");
                    alerPassInc.setVisible(false);
                    String pass = new String(userPasswordField.getPassword());
                    Cliente usuario = null;
                    try {
                        usuario = new Cliente();
                        // System.out.println("user");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    // System.out.println("consulta1");
                    try {
                        if(usuario.register(userNameField.getText(),pass)){
                            // como pudo crear el usuario lo mandamos al Login para que ingrese
                            // System.out.println("consulta2");
                            Login login = new Login();
                            setVisible(false);
                            login.setVisible(true);
                        }
                        else{
                            alertUserName.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else{
                    alerPassInc.setVisible(true);
                }
            }
        });

        goToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Go to Login Funciona");
                Login login = new Login();
                setVisible(false);
                login.setVisible(true);
            }
        });

        setTitle("User Register");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}
