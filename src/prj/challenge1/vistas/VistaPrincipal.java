package prj.challenge1.vistas;


import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.swing.JOptionPane;
import prj.challenge1.entidades.Email;
import prj.challenge1.controladores.LectorEmail;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


/**
 *
 * @author Silvana
 */
public class VistaPrincipal extends javax.swing.JFrame {

    private VistaEmails vistaEmail;
    private LectorEmail lector;
    
    public VistaPrincipal(LectorEmail lector) {
        this.lector = lector;
         
        initComponents();
        setIconBtnBuscar();
    }
    
    public void setIconBtnBuscar()
    {
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/prj/challenge1/imagenes/search-icon.png")));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPalabra = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblEmail.setText("Email");

        lblPassword.setText("Contraseña");

        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblEmail)
                    .addComponent(lblPassword))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPalabra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        String user = txtEmail.getText();
        String password = String.copyValueOf(txtPassword.getPassword());
        String palabra = txtPalabra.getText();
       
        if (lector.validarCampos(user, password, palabra)) 
        {
            Message[] msgs = lector.searchEmails(user, password, palabra);
        
            ArrayList<Email> emails = new ArrayList();
            
            if (msgs.length > 0) 
            {
                for (int i = 0; i < msgs.length; i++) 
                    {
                        Email e = new Email();
                        Message msg = msgs[i];
                        try 
                        {                        
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            e.setFecha(dateFormat.format(msg.getReceivedDate()));
                            Address[] froms = msg.getFrom();
                            e.setFrom(MimeUtility.decodeText(froms[0].toString()));
                            e.setSubject(msg.getSubject());

                            if(!emails.contains(e))
                            {
                                emails.add(e);
                            }

                        } catch (MessagingException ex) {
                            ex.printStackTrace();
                        } catch (UnsupportedEncodingException ex) {
                            ex.printStackTrace();
                        }                    
                    }
                
                for (int i = 0; i < emails.size(); i++) 
                {
                   lector.insertEmails(emails.get(i));
                   lector.mostrarEmails(emails.get(i).toString());
                   lector.mostrarEmails("\n");

                }
                lector.closeVistaPrincipal();
                lector.initVistaEmails();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "No hay coincidencias");
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

   
    public VistaEmails getVistaEmail() {
        return vistaEmail;
    }

    public void setVistaE(VistaEmails vistaE) {
        this.vistaEmail = vistaE;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtPalabra;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
