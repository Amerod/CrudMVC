/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import modelo.*;
import vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author andres
 */
public class ControdalorCrud implements ActionListener,KeyListener{
    
    JFCrud vistaCRUD = new JFCrud();
    PersonaDAO modeloCRUD = new PersonaDAO();
    
    public ControdalorCrud(JFCrud vistaCRUD,PersonaDAO modeloCRUD){
        this.modeloCRUD = modeloCRUD;
        this.vistaCRUD = vistaCRUD;
        this.vistaCRUD.btnRegistrar.addActionListener(this);
        this.vistaCRUD.btnListar.addActionListener(this);
        this.vistaCRUD.btnEditar.addActionListener(this);
        this.vistaCRUD.btnEliminar.addActionListener(this);
        this.vistaCRUD.btnGEdit.addActionListener(this);
        this.vistaCRUD.txtDni.addKeyListener(this);
        this.vistaCRUD.txtApellidos.addKeyListener(this);
        this.vistaCRUD.txtNombres.addKeyListener(this);
        this.vistaCRUD.txtLugar.addKeyListener(this);
        this.vistaCRUD.txtBusqueda.addKeyListener(this);
        
    }
    
    public void InicializarCRUD(){
        
    }
    
    public void LlenarTabla(JTable tablaD){
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        
        modeloT.addColumn("DNI");
        modeloT.addColumn("APELLIDOS");
        modeloT.addColumn("NOMBRES");
        modeloT.addColumn("FECHA N.");
        modeloT.addColumn("LUGAR N.");
        
        Object[] columna = new Object[5];
        
        int numRegistros = modeloCRUD.listPersona().size();
        for (int i = 0; i < numRegistros; i++) {
            columna[0] = modeloCRUD.listPersona().get(i).getDni();
            columna[1] = modeloCRUD.listPersona().get(i).getApellidos();
            columna[2] = modeloCRUD.listPersona().get(i).getNombres();
            columna[3] = modeloCRUD.listPersona().get(i).getFechaN();
            columna[4] = modeloCRUD.listPersona().get(i).getLugarN();
            modeloT.addRow(columna);
        }
    }
    
    public void LimpiarElementos(){
        vistaCRUD.txtDni.setText("");
        vistaCRUD.txtDni.setEditable(true);
        vistaCRUD.txtApellidos.setText("");
        vistaCRUD.txtNombres.setText("");
        vistaCRUD.txtLugar.setText("");
        vistaCRUD.jdFecha.setDate(null);
        
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == vistaCRUD.btnRegistrar){
            String dni = vistaCRUD.txtDni.getText();
            String apellidos = vistaCRUD.txtApellidos.getText();
            String nombres = vistaCRUD.txtNombres.getText();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formatoFecha.format(vistaCRUD.jdFecha.getDate());
            String lugar = vistaCRUD.txtLugar.getText();
            
            String rptaRegistro = modeloCRUD.insertPersona(dni, apellidos, nombres, fecha, lugar);
            
            if (rptaRegistro!=null) {
                JOptionPane.showMessageDialog(null, rptaRegistro);
            }else{
                JOptionPane.showMessageDialog(null, "Registro Erroneo.");
            }
        }
        if(e.getSource() == vistaCRUD.btnListar){
                LlenarTabla(vistaCRUD.jtDatos);
        }
        
        if(e.getSource() == vistaCRUD.btnEditar){
            int filaEditar = vistaCRUD.jtDatos.getSelectedRow();
            int numFS = vistaCRUD.jtDatos.getSelectedColumnCount();
            if(filaEditar>=0 && numFS == 1){
                vistaCRUD.txtDni.setText(String.valueOf(vistaCRUD.jtDatos.getValueAt(filaEditar,0)));
                vistaCRUD.btnGEdit.setEnabled(true);
                vistaCRUD.txtDni.setEditable(false);
                vistaCRUD.btnRegistrar.setEnabled(false);
                vistaCRUD.btnEditar.setEnabled(false);
                vistaCRUD.btnEliminar.setEnabled(false);
                vistaCRUD.txtBusqueda.setEnabled(false);
            }else{
                JOptionPane.showMessageDialog(null, "Debes seleccionar al menos una fila.");
            }
        }
        
        if((e.getSource()) == vistaCRUD.btnGEdit){
            String dni = vistaCRUD.txtDni.getText();
            String apellidos = vistaCRUD.txtApellidos.getText();
            String nombres = vistaCRUD.txtNombres.getText();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formatoFecha.format(vistaCRUD.jdFecha.getDate());
            String lugar = vistaCRUD.txtLugar.getText();
            
            int rptaEdit = modeloCRUD.editarPersona(dni, apellidos, nombres, fecha, lugar);
            if (rptaEdit>0){
                JOptionPane.showMessageDialog(null, "Edicion Exiosa.");
            }else{
                JOptionPane.showMessageDialog(null, "No se ha modificado.");
            }
            LimpiarElementos();
            vistaCRUD.btnRegistrar.setEnabled(true);
            vistaCRUD.btnEditar.setEnabled(true);
            vistaCRUD.btnEliminar.setEnabled(true);
            vistaCRUD.txtBusqueda.setEnabled(true);
            vistaCRUD.btnGEdit.setEnabled(false);
        }
        
        if(e.getSource() == vistaCRUD.btnEliminar){
            int filaInicio = vistaCRUD.jtDatos.getSelectedRow();
            int numFS = vistaCRUD.jtDatos.getSelectedColumnCount();
            ArrayList<String> listaDni = new ArrayList();
            String dni = "";
            if (filaInicio>0){
                for (int i = 0; i < numFS; i++) {
                    dni = String.valueOf(vistaCRUD.jtDatos.getValueAt(i+filaInicio, 0));
                    listaDni.add(dni);
                }
                for (int i = 0; i < numFS; i++) {
                    int rptaUsuario = JOptionPane.showConfirmDialog(null, "Quiere eliminar el registro con "+dni+"?");
                    if (rptaUsuario==0){
                        modeloCRUD.eliminarPersona(dni);
                    }
                }
                LlenarTabla(vistaCRUD.jtDatos);
            }else{
                JOptionPane.showMessageDialog(null, "Selecciona al menos una fila.");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == vistaCRUD.txtBusqueda){
        String apellidos = vistaCRUD.txtBusqueda.getText();
        DefaultTableModel modeloT = new DefaultTableModel();
        vistaCRUD.jtDatos.setModel(modeloT);
        
        modeloT.addColumn("DNI");
        modeloT.addColumn("APELLIDOS");
        modeloT.addColumn("NOMBRES");
        modeloT.addColumn("FECHA N.");
        modeloT.addColumn("LUGAR N.");
        
        Object[] columna = new Object[5];
        
        int numRegistros = modeloCRUD.buscarPersonaXApellidos(apellidos).size();
        for (int i = 0; i < numRegistros; i++) {
            columna[0] = modeloCRUD.buscarPersonaXApellidos(apellidos).get(i).getDni();
            columna[1] = modeloCRUD.buscarPersonaXApellidos(apellidos).get(i).getApellidos();
            columna[2] = modeloCRUD.buscarPersonaXApellidos(apellidos).get(i).getNombres();
            columna[3] = modeloCRUD.buscarPersonaXApellidos(apellidos).get(i).getFechaN();
            columna[4] = modeloCRUD.buscarPersonaXApellidos(apellidos).get(i).getLugarN();
            modeloT.addRow(columna);
        }
        }
    }
}
