/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finalyear.networkservicediscovery.ui;

import com.finalyear.networkservicediscovery.pojos.Contact;
import com.finalyear.networkservicediscovery.services.SocketService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

import static com.finalyear.networkservicediscovery.Main.SOCKET_SERVICE;

/**
 * @author KayO
 */
public class ChatUIScreen extends javax.swing.JFrame {

    /**
     * Creates new form ChatClient
     */
    static Socket socket;
    static DataInputStream din;
    static DataOutputStream dout;

    private String msgIn = "";

    private boolean isServer;
    private boolean received = false;
    private boolean otherIsOnline = false;
    private Contact contact;
    private boolean identified = false;

    public Contact getContact() {
        return contact;
    }

    private int port;

    boolean initialized = false;

    public ChatUIScreen(boolean isServer) {
        System.out.println("ChatUIScreen Constructor called here");
        initComponents();
        this.isServer = isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
        System.out.println("ChatUIScreen: setServer(true) called: isServer = " + isServer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taClientOutput = new javax.swing.JTextArea();
        tfClientInput = new javax.swing.JTextField();
        btClientSend = new javax.swing.JButton();
        btFileSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client");

        taClientOutput.setEditable(false);
        taClientOutput.setColumns(20);
        taClientOutput.setRows(5);
        jScrollPane1.setViewportView(taClientOutput);

        tfClientInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfClientInputActionPerformed(evt);
            }
        });
        tfClientInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfClientInputKeyTyped(evt);
            }
        });

        btClientSend.setText("Send");
        btClientSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btClientSendActionPerformed(evt);
            }
        });

        btFileSend.setText("Send File");
        btFileSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFileSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(tfClientInput, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(btClientSend, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                                        .addComponent(btFileSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfClientInput)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btClientSend, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btFileSend, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btFileSendActionPerformed(java.awt.event.ActionEvent evt) {
        //get File path from FileChooser dialog and send through socket
        JFileChooser fileChooser = new JFileChooser();
        //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected File: " + selectedFile.getAbsolutePath());
            SOCKET_SERVICE.setComplete(false);
            SOCKET_SERVICE.sendFile(selectedFile.getAbsolutePath());
        }
    }

    private void tfClientInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfClientInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfClientInputActionPerformed

    private void btClientSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btClientSendActionPerformed
        // TODO add your handling code here:
        String msgOut = tfClientInput.getText().trim();
        /*try {
            dout.writeUTF(msgOut);//send the server's message
            tfClientInput.setText("");
            tfClientInput.requestFocus();
        } catch (IOException ex) {
            //Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        sendMessage(msgOut);
    }//GEN-LAST:event_btClientSendActionPerformed

    private void tfClientInputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfClientInputKeyTyped
        // TODO add your handling code here:
        if (evt.getKeyChar() == '\n') {
            String msgOut = tfClientInput.getText().trim();
           /*try {
            dout.writeUTF(msgOut);//send the server's message
            tfClientInput.setText("");
            tfClientInput.requestFocus();
        } catch (IOException ex) {
            //Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
            sendMessage(msgOut);
        }

    }//GEN-LAST:event_tfClientInputKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btClientSend;
    private javax.swing.JButton btFileSend;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea taClientOutput;
    private javax.swing.JTextField tfClientInput;
    // End of variables declaration//GEN-END:variables

    /*public void setService(SocketService socketService) {
        this.socketService = socketService;
    }*/

    public boolean showChatMessage(String s, boolean isReceived) {
        received = isReceived;
        //chatArrayAdapter.add(new ChatMessage(received, s));
        //if (!isReceived) {//outgoing message
        taClientOutput.setText(taClientOutput.getText() + "\n" + s);
        tfClientInput.setText("");
        //}

        return true;
    }

    public void connectToFilePort(String ip, Integer port, String fileName) {
        //check if we're on Marshmallow or higher

        ClientRxThread clientRxThread
                = new ClientRxThread(ip, port, fileName);

        clientRxThread.start();

    }

    //function to send message
    public void sendMessage(String msgOut) {
        //If server, interact with service, else do dout.writeUTF()
        if (isServer) {
            //interact with service
            if (SOCKET_SERVICE.sendMessage(msgOut)) {
                //message sent successfully
                //store messages in database over here
                //storeMessage("Server:\t" + msgOut, contact, false);//params: message, other party, reception state
                //show message in chat screen
                showChatMessage("Server:\t" + msgOut, false);
            } else {
                System.out.println("Error sending message");
            }
        } else {
            System.out.println("isServer = " + isServer);
            try {
                if (dout != null) {
                    dout.writeUTF(msgOut);//send message
                    //store messages in database over here
                    //storeMessage("Client:\t" + msgOut, contact, false);//params: message, other party, reception state
                    showChatMessage("Client:\t" + msgOut, false);
                } else {
                    System.out.println("ChatUIScreen: dout is null, no socket connection");
                }

                tfClientInput.requestFocus();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void main(String identity, InetAddress ip, int port) {
        System.out.println("ChatUIScreen Main called here");
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatUIScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatUIScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatUIScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatUIScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("Show Chat UI here");
                new ChatUIScreen(true).setVisible(true);
                initialized = true;
            }
        });

        if (!isServer) {
            try {
                System.out.println("beginning of try block");
                socket = new Socket(ip.toString().replace("/", "").trim(), port);//local ip since host and client are on the same computer
                din = new DataInputStream(socket.getInputStream());
                dout = new DataOutputStream(socket.getOutputStream());
                String msgIn = "";

                if (!identified)
                    identify(identity);

                new ListenThread(socket, din, dout, msgIn, ip).start();

            } catch (IOException ex) {
                System.out.println("ChatUIScreen main(): " + ex.toString());
                //Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void identify(String identity) {
        //tell the server who you are so they can save your messages appropriately
        sendMessage("##identity:" + identity);
        identified = true;
    }

    private class ClientRxThread extends Thread {

        String dstAddress;
        int dstPort;
        String fileName;

        ClientRxThread(String address, int port, String fileName) {
            dstAddress = address;
            dstPort = port;
            this.fileName = fileName;
        }

        @Override
        public void run() {
            Socket tempSocket = null;

            try {
                tempSocket = new Socket(dstAddress, dstPort);
                String completePath = "G:\\project files\\servcie discovery desktop\\NSDChat_today\\file_transfers\\" + "Wi-Files";
                //make directory for our incoming files
                //note that a File object can be either an actual file or a directory
                if (isImage(fileName)) {
                    //file is an image
                    completePath += "\\Wi-Files Images";
                } else if (isVideo(fileName)) {
                    completePath += "\\Wi-Files Videos";
                } else if (isAudio(fileName)) {
                    completePath += "\\Wi-Files Audios";
                } else {
                    completePath += "\\Wi-Files";
                }
                File wifilesDirectory = new File(completePath);
                wifilesDirectory.mkdirs();
                File file = new File(
                        wifilesDirectory,
                        fileName);

                ObjectInputStream ois = new ObjectInputStream(tempSocket.getInputStream());
                byte[] bytes;
                FileOutputStream fos = null;
                try {
                    bytes = (byte[]) ois.readObject();
                    fos = new FileOutputStream(file);
                    fos.write(bytes);
                    //moved fos.close here //TODO check if problem is solved by moving content of finally block here
                    fos.close();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("exception reading FOS: " + e.toString());
                }
                /*finally {
                    if (fos != null) {
                        fos.close();

                    }

                }*/

                tempSocket.close();

                System.out.println("Transfer Finished");
                sendMessage("##transfer_complete");

            } catch (IOException e) {

                e.printStackTrace();

                final String eMsg = "Something wrong: " + e.getMessage();
                System.out.println("IOException: " + e.toString());

                System.out.println(eMsg);
                sendMessage(eMsg);

            } finally {
                if (tempSocket != null) {
                    try {
                        tempSocket.close();
                        System.out.println("closeSocket: tempSocket closed");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("closeSocket: tempSocket could NOT be closed");
                    }
                }
            }
        }

        private boolean isAudio(String fileName) {
            return fileName.endsWith(".mp3") || fileName.endsWith(".aac") || fileName.endsWith(".m4a") || fileName.endsWith(".amr");
        }

        private boolean isVideo(String fileName) {
            return fileName.endsWith(".mp4") || fileName.endsWith(".3gp") || fileName.endsWith(".mkv") || fileName.endsWith(".webm") || fileName.endsWith(".avi");
        }

        private boolean isImage(String fileName) {
            return fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif") || fileName.endsWith(".JPG") || fileName.endsWith(".PNG") || fileName.endsWith(".GIF") || fileName.endsWith(".jpeg");
        }


    }

    private class ListenThread extends Thread {
        Socket socket;
        DataInputStream din;
        DataOutputStream dout;
        String msgIn;
        InetAddress ip;

        public ListenThread(Socket socket, DataInputStream din, DataOutputStream dout, String msgIn, InetAddress ip) {
            this.socket = socket;
            this.din = din;
            this.dout = dout;
            this.msgIn = msgIn;
            this.ip = ip;
        }


        @Override
        public void run() {
            super.run();
            int count = 0;
            try {
                while (!msgIn.equals("##exit")) {
                    System.out.println("while loop running: loop " + ++count);
                    if (msgIn.contains("##port:")) {
                        //extract new port number and connect to new socket
                        connectToFilePort(
                                ip.toString().replace("/", "").trim(),
                                Integer.valueOf(msgIn.substring(msgIn.indexOf(':') + 1, msgIn.indexOf('/'))),
                                msgIn.substring(msgIn.lastIndexOf('/') + 1));
                    } else if (msgIn.contains("##transfer_complete")) {
                        SOCKET_SERVICE.setComplete(true);//set complete to terminate infinite loop
                    }
                    System.out.println("About to read in message");
                    msgIn = din.readUTF();//get new incoming message
                    System.out.println("Just read message: " + msgIn);
                    //display messages from client
                    taClientOutput.setText(taClientOutput.getText().trim() + "\nServer:\t" + msgIn);
                    System.out.println("should display message to screen by now");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }

        }
    }
}