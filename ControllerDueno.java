/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import helpers.Helpers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.Dueno;
import modelo.DuenoDAO;

import vista.frmDueno;

/**
 *
 * @author juanj
 */
public class ControllerDueno implements ActionListener {

    DuenoDAO duenoDAO = new DuenoDAO();
    Dueno dueno = new Dueno();
    frmDueno vistaDueno = new frmDueno();
    Helpers help = new Helpers();

    public ControllerDueno(frmDueno frm) {
        this.vistaDueno = frm;
        this.vistaDueno.btnGuardar.addActionListener(this);
        this.vistaDueno.btnEditar.addActionListener(this);
        this.vistaDueno.btnEliminar.addActionListener(this);
        this.vistaDueno.btnBuscar.addActionListener(this);
        this.vistaDueno.btnRefrescar.addActionListener(this);
        this.vistaDueno.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaDueno.btnGuardar) {
            if (validacion() == 1) {
                agregarDueno();
                limpiarCampos();
                filtrarTablaPorNombre(vistaDueno.tblDueno, "");
            }
        }

        if (e.getSource() == vistaDueno.btnEditar) {
            if (validacion() == 1) {
                actualizarDueno();
                limpiarCampos();
                filtrarTablaPorNombre(vistaDueno.tblDueno, "");
            }
        }

        if (e.getSource() == vistaDueno.btnEliminar) {
            if (vistaDueno.txtIdDueno.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un ID válido");
            } else {
                eliminarDueno();
                filtrarTablaPorNombre(vistaDueno.tblDueno, "");
            }
        }
        if (e.getSource() == vistaDueno.btnBuscar) {

            filtrarTablaPorNombre(vistaDueno.tblDueno, vistaDueno.txtBuscar.getText());
        }

        if (e.getSource() == vistaDueno.btnRefrescar) {

            filtrarTablaPorNombre(vistaDueno.tblDueno, "");
        }
        if (e.getSource() == vistaDueno.btnCancelar) {
            limpiarCampos();
        }
    }

    public void agregarDueno() {
        String nombre = vistaDueno.txtNombre.getText();
        String apellido1 = vistaDueno.txtApellido1.getText();
        String apellido2 = vistaDueno.txtApellido2.getText();
        String cedula = vistaDueno.txtCedula.getText();
        String genero = vistaDueno.boxGenero.getSelectedItem().toString();
        String email = vistaDueno.txtEmail.getText();
        String telefono = vistaDueno.txtTelefono.getText();
        String direccion = vistaDueno.txtDireccion.getText();

        dueno.setNombre(nombre);
        dueno.setApellido1(apellido1);
        dueno.setApellido2(apellido2);
        dueno.setCedula(cedula);
        dueno.setGenero(genero);
        dueno.setEmail(email);
        dueno.setTelefono(telefono);
        dueno.setDireccion(direccion);

        int r = duenoDAO.agregarDueno(dueno);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaDueno, "USUARIO REGISTRADO CON EXITO");
            filtrarTablaPorNombre(vistaDueno.tblDueno, "");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaDueno, "USUARIO NO REGISTRADO");
        }
    }

    public void actualizarDueno() {

        int idDueno = Integer.parseInt(vistaDueno.txtIdDueno.getText());
        String nombre = vistaDueno.txtNombre.getText();
        String apellido1 = vistaDueno.txtApellido1.getText();
        String apellido2 = vistaDueno.txtApellido2.getText();
        String cedula = vistaDueno.txtCedula.getText();
        String genero = vistaDueno.boxGenero.getSelectedItem().toString();
        String email = vistaDueno.txtEmail.getText();
        String telefono = vistaDueno.txtTelefono.getText();
        String direccion = vistaDueno.txtDireccion.getText();

        dueno.setIdDueno(idDueno);
        dueno.setNombre(nombre);
        dueno.setApellido1(apellido1);
        dueno.setApellido2(apellido2);
        dueno.setCedula(cedula);
        dueno.setGenero(genero);
        dueno.setEmail(email);
        dueno.setTelefono(telefono);
        dueno.setDireccion(direccion);

        int r = duenoDAO.actualizarDueno(dueno);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaDueno, "DUEÑO ACTUALIZADO CON ÉXITO");
            filtrarTablaPorNombre(vistaDueno.tblDueno, "");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaDueno, "DUEÑO NO ACTUALIZADO, INTENTE MÁS TARDE");
        }
    }

    public void eliminarDueno() {
        int idDueno = Integer.parseInt(vistaDueno.txtIdDueno.getText());
        int r = duenoDAO.eliminarDueno(idDueno);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaDueno, "DUEÑO ELIMINADO EXITOSAMENTE");
            filtrarTablaPorNombre(vistaDueno.tblDueno, "");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaDueno, "DUEÑO NO ELIMINADO, DUEÑO PUEDE CONTENER REGISTROS");
        }
    }

    public void filtrarTablaPorNombre(JTable table, String filtro) {
        duenoDAO.filtarTablaPorNombre(table, filtro);
        help.centrarCeldas(table);
    }

    public int validacion() {

        int r = 0;

        if (vistaDueno.txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un nombre");
        } else if (vistaDueno.txtApellido1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un apellido 1");
        } else if (vistaDueno.txtApellido2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un apellido 2");
        } else if (vistaDueno.txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un numero de Cedula");
        } else if (vistaDueno.boxGenero.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un genero");
        } else if (vistaDueno.txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un Email");
        } else if (vistaDueno.txtTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar un telefono");
        } else if (vistaDueno.txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaDueno, "Error, debe ingresar una direccion");
        } else {
            r++;
        }

        return r;
    }

    public void limpiarCampos() {

        vistaDueno.txtIdDueno.setText("");
        vistaDueno.txtNombre.setText("");
        vistaDueno.txtApellido1.setText("");
        vistaDueno.txtApellido2.setText("");
        vistaDueno.txtCedula.setText("");
        vistaDueno.txtEmail.setText("");
        vistaDueno.txtTelefono.setText("");
        vistaDueno.txtDireccion.setText("");

    }

    public void iniciar() {

        limpiarCampos();
        filtrarTablaPorNombre(vistaDueno.tblDueno, "");
        help.centrarCeldas(vistaDueno.tblDueno);
    }

}
