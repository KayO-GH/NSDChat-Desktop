/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalyear.networkservicediscovery.ui;

import com.finalyear.networkservicediscovery.utils.SqliteConnection;

import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.*;


/**
 *
 * @author Vanessa
 */

public class RegisterScreen extends JFrame {

    /**
     * Creates new form NewJFrame
     */
    
     Connection connection = null;
     

    public RegisterScreen() {
        initComponents();
        connection= SqliteConnection.dbConnector();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        UsernameTextField = new JTextField();
        Username = new JLabel();
        SubmitButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        UsernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameTextFieldActionPerformed(evt);
            }
        });

        Username.setText("Username:");

        SubmitButton.setText("SUBMIT");
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Username, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(UsernameTextField)
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 212, Short.MAX_VALUE)
                .addComponent(SubmitButton)
                .addGap(119, 119, 119))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(Username, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                    .addComponent(UsernameTextField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addComponent(SubmitButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UsernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameTextFieldActionPerformed

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "INSERT INTO app_info_tbl (t_identity, i_first_run_state) values (?,0)";
            PreparedStatement pst;
            pst = connection.prepareStatement(sql);
            pst.setString(1,UsernameTextField.getText());

            pst.execute();

            //check to see if data was added
            //start
            sql = "SELECT * FROM app_info_tbl LIMIT 1";
            pst = connection.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                //insert was successful
                System.out.println("Inserting new service name successful");
                JOptionPane.showMessageDialog(null, "Saved");
                //start socket service and open the discovery window from here
                String identity = rs.getString("t_identity");
                System.out.println(identity);

                //SOCKET_SERVICE.start();

                //ChatUIScreen will be called from the discovery screen , not the main function
                //ChatUIScreen uiScreen = new ChatUIScreen();
                //SOCKET_SERVICE.setServerUIActivity(uiScreen);
                //uiScreen.setService(SOCKET_SERVICE);
                //pass this identity to the remaining windows
                this.dispose();

                DiscoveryScreen discoveryScreen = new DiscoveryScreen(identity);
                //discoveryScreen.setSocketService(SOCKET_SERVICE);
                //discoveryScreen.main();

            }else{
                System.out.println("Inserting new service name FAILED");
            }
            //end
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_SubmitButtonActionPerformed


    public void main() {
        System.out.println("RegisterScreen main() called");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisterScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton SubmitButton;
    private JLabel Username;
    private JTextField UsernameTextField;
    // End of variables declaration//GEN-END:variables

    private String printStackTrace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
