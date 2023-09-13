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
import modelo.Veterinario;
import modelo.VeterinarioDAO;
import vista.frmVeterinario;

/**
 *
 * @author Dell PC
 */
public class ControllerVeterinario implements ActionListener {

    frmVeterinario vistaVet = new frmVeterinario();
    Veterinario vet = new Veterinario();
    VeterinarioDAO vDAO = new VeterinarioDAO();
    Helpers help = new Helpers();

    public ControllerVeterinario(frmVeterinario vistaV) {
        this.vistaVet = vistaV;
        this.vistaVet.btnGuardar.addActionListener(this);
        this.vistaVet.btnRefrescar.addActionListener(this);
        this.vistaVet.btnBuscar.addActionListener(this);
        this.vistaVet.btnCancelar.addActionListener(this);
        this.vistaVet.btnEditar.addActionListener(this);
        this.vistaVet.btnEliminar.addActionListener(this);
        this.vistaVet.tglbtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaVet.btnGuardar) {
            if (validacion() == 1) {
                guardarVeterinario();
            }
        }
        if (e.getSource() == vistaVet.btnBuscar) {
            vistaVet.tglbtn.setSelected(false);
            vistaVet.tglbtn.setText("ACTIVO");
            mostrarTablaFiltroCategoria(vistaVet.tblVeterinarios, vistaVet.cbCategoria.getSelectedItem().toString(), vistaVet.txtBuscar.getText());
        }
        if (e.getSource() == vistaVet.btnRefrescar) {
            vistaVet.tglbtn.setSelected(false);
            vistaVet.tglbtn.setText("ACTIVO");
            mostrarTablaFiltroCategoria(vistaVet.tblVeterinarios, vistaVet.cbCategoria.getSelectedItem().toString(), "");
        }
        if (e.getSource() == vistaVet.btnEliminar) {
            eliminarVeterionario();
        }
        if (e.getSource() == vistaVet.btnEditar) {
            if (validacion() == 1) {
                editarVeterinario();
            }
        }
        if (e.getSource() == vistaVet.btnCancelar) {
            eliminarEspacios();
        }
        if (e.getSource() == vistaVet.tglbtn) {
            if (vistaVet.tglbtn.isSelected()) {
                vDAO.mostrarTablaPorEstado(vistaVet.tblVeterinarios, 1);
                help.centrarCeldas(vistaVet.tblVeterinarios);
            } else {
                vDAO.mostrarTablaPorEstado(vistaVet.tblVeterinarios, 0);
                help.centrarCeldas(vistaVet.tblVeterinarios);
            }
        }

    }

    public int guardarVeterinario() {

        int r = 0;

        String nombre = vistaVet.txtNombre.getText();
        String apellido1 = vistaVet.txtApellido1.getText();
        String apellido2 = vistaVet.txtApellido2.getText();
        String cedula = vistaVet.txtCedula.getText();
        String codProfesional = vistaVet.txtCodPro.getText();
        String email = vistaVet.txtEmail.getText();
        String telefono = vistaVet.txtTel.getText();
        String direccion = vistaVet.txtDireccion.getText();
        boolean activo = vistaVet.chbActivo.isSelected();

        vet.setNombre(nombre);
        vet.setApellido1(apellido1);
        vet.setApellido2(apellido2);
        vet.setCedula(cedula);
        vet.setCodProfesional(codProfesional);
        vet.setEmail(email);
        vet.setTelefono(telefono);
        vet.setDireccion(direccion);
        if (activo == true) {
            vet.setActivo(1);
        } else {
            vet.setActivo(0);
        }

        r = vDAO.agregarVeterinario(vet);

        if (r == 1) {
            JOptionPane.showMessageDialog(null, "Se ha agregado el Veterinario con exito");
            mostrarTablaFiltroCategoria(vistaVet.tblVeterinarios, vistaVet.cbCategoria.getSelectedItem().toString(), "");
            eliminarEspacios();
        } else {
            JOptionPane.showMessageDialog(null, "No ha agregado el Veterinario, intente de nuevo");
        }

        return r;
    }

    public int editarVeterinario() {

        int r = 0;

        int id = Integer.parseInt(vistaVet.txtIdVeterinario.getText());
        String nombre = vistaVet.txtNombre.getText();
        String apellido1 = vistaVet.txtApellido1.getText();
        String apellido2 = vistaVet.txtApellido2.getText();
        String cedula = vistaVet.txtCedula.getText();
        String codProfesional = vistaVet.txtCodPro.getText();
        String email = vistaVet.txtEmail.getText();
        String telefono = vistaVet.txtTel.getText();
        String direccion = vistaVet.txtDireccion.getText();
        boolean activo = vistaVet.chbActivo.isSelected();

        vet.setId(id);
        vet.setNombre(nombre);
        vet.setApellido1(apellido1);
        vet.setApellido2(apellido2);
        vet.setCedula(cedula);
        vet.setCodProfesional(codProfesional);
        vet.setEmail(email);
        vet.setTelefono(telefono);
        vet.setDireccion(direccion);
        if (activo == true) {
            vet.setActivo(1);
        } else {
            vet.setActivo(0);
        }

        r = vDAO.editarVeterinario(vet);

        if (r == 1) {
            JOptionPane.showMessageDialog(null, "Se ha editado el Veterinario con exito");
            mostrarTablaFiltroCategoria(vistaVet.tblVeterinarios, vistaVet.cbCategoria.getSelectedItem().toString(), "");
            eliminarEspacios();

        } else {
            JOptionPane.showMessageDialog(null, "No ha editado el Veterinario, intente de nuevo");
        }

        return r;

    }

    public int eliminarVeterionario() {

        int r = 0;
        int id = Integer.parseInt(vistaVet.txtIdVeterinario.getText());

        vet.setId(id);

        r = vDAO.eliminarVeterinario(vet);

        if (r == 1) {
            JOptionPane.showMessageDialog(null, "Se ha eliminado el Veterinario con exito");
            mostrarTablaFiltroCategoria(vistaVet.tblVeterinarios, vistaVet.cbCategoria.getSelectedItem().toString(), "");
            eliminarEspacios();

        } else {
            JOptionPane.showMessageDialog(null, "No ha eliminado el Veterinario, intente de nuevo");

        }

        return r;

    }

    public void mostrarTablaFiltroCategoria(JTable tablaVet, String categoria, String filtro) {
        vDAO.BuscarTablaFiltro(tablaVet, categoria, filtro);
        help.centrarCeldas(tablaVet);
    }

    public int validacion() {

        int r = 0;

        if (vistaVet.txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
        } else if (vistaVet.txtApellido1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un primer apellido");
        } else if (vistaVet.txtApellido2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un segundo apellido");
        } else if (vistaVet.txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una cedula valida");
        } else if (vistaVet.txtCodPro.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un codigo profesional valido");
        } else if (vistaVet.txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un email");
        } else if (vistaVet.txtTel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un numero de telefono");
        } else if (vistaVet.txtDireccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una direccion");
        } else {
            r++;
        }
        return r;
    }

    public void eliminarEspacios() {

        vistaVet.txtIdVeterinario.setText("");
        vistaVet.txtNombre.setText("");
        vistaVet.txtApellido1.setText("");
        vistaVet.txtApellido2.setText("");
        vistaVet.txtCedula.setText("");
        vistaVet.txtCodPro.setText("");
        vistaVet.txtEmail.setText("");
        vistaVet.txtDireccion.setText("");
        vistaVet.txtTel.setText("");
        vistaVet.chbActivo.setSelected(false);

    }

}
