/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj.challenge1.controladores;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.search.*;
import javax.swing.JOptionPane;
import prj.challenge1.modelos.ModelEMail;
import prj.challenge1.entidades.Email;
import prj.challenge1.vistas.VistaEmails;
import prj.challenge1.vistas.VistaPrincipal;

public class LectorEmail 
{
    private VistaPrincipal principal;
    private ModelEMail modelE;
    private VistaEmails vEmails;
    
    public LectorEmail ()
    {            
        modelE = new ModelEMail("test_challenge", "root", "");
        
        principal = new VistaPrincipal(this);
        vEmails = new VistaEmails(this);
        initVistaPrincipal();
    }
    
    public void initVistaPrincipal()
    {
        principal.setVisible(true);
    }
    
    public void closeVistaPrincipal()
    {
        principal.dispose();
    }
        
    public void initVistaEmails()
    {
        vEmails.setVisible(true);
    }
    
    public void closeVistaEmails()
    {
        vEmails.dispose();
    }
    
    public Message[] searchEmails(String user, String password, String palabra)
    {
        Properties prop = new Properties();
        prop.setProperty("mail.store.protocol", "imaps");
        
        
        Session session = Session.getInstance(prop);
        Store emailStore;
        
        Message[] msgs = null;
        try {
            emailStore = session.getStore();
            emailStore.connect("imap.gmail.com",user, password);
            Folder inbox = emailStore.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            SearchTerm st = new OrTerm(new BodyTerm(palabra),new SubjectTerm(palabra));
            msgs = inbox.search(st);
            
            //inbox.close(true);
            //emailStore.close();
        } catch (NoSuchProviderException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return msgs;
    }
    
    public ArrayList<Email> getAllEmails()
    {
        return modelE.getAllEmails();
    }
    
    public void insertEmails(Email e)
    {        
        if (!modelE.existe(e)) 
            modelE.insertEmail(e);
    }
    
    public void mostrarEmails(String str)
    {
        vEmails.getTxtAreaEmail().append(str);
    }
    
    public boolean validarCampos(String user, String pass, String palabra)
    {
        if (user.isEmpty()) {
            JOptionPane.showMessageDialog(principal, "Debe ingresar un email");
            return false;
        }
        
        try {
            InternetAddress address = new InternetAddress(user);
            address.validate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(principal, "Debe ingresar un email valido");
            return false;
        }
        
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(principal, "Debe ingresar un contraseña");
            return false;
        }
        
        try 
        {
            Properties prop = new Properties();
            prop.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getInstance(prop);
            Store store = session.getStore();
            store.connect("imap.gmail.com",user, pass);
            
            store.close();            
        } catch (MessagingException me) 
        {
            JOptionPane.showMessageDialog(principal, "Verififique su email y/o contraseña");
            return false;
        }
        
        if (palabra.isEmpty()) {
            JOptionPane.showMessageDialog(principal, "Debe ingresar una palabra");
            return false;
        }
        return true;
    }
    
}
