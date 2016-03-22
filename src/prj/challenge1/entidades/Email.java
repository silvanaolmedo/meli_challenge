/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prj.challenge1.entidades;

public class Email 
{
    private String fecha, from, subject;
    
    public Email()
    {
        
    }
    
    public Email(String fecha, String from, String subject)
    {
        this.fecha = fecha;
        this.from = from;
        this.subject = subject;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Fecha: "+fecha+"\nFrom: "+from+"\nSubject: "+subject+"\n";
    }
}
