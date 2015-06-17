/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudmvc;
import modelo.*;
import vista.*;
import controlador.*;
/**
 *
 * @author andres
 */
public class CrudMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFCrud vistaC = new JFCrud();
        PersonaDAO modeloC = new PersonaDAO();
        ControdalorCrud controlaC = new ControdalorCrud(vistaC,modeloC);
        
        vistaC.setVisible(true);
        vistaC.setLocationRelativeTo(null);
    }
    
}
