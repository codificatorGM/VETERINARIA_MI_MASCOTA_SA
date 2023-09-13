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
import modelo.Mascota;
import modelo.MascotaDAO;
import vista.frmMascota;

/**
 *
 * @author Dell PC
 */
public class ControllerMascota implements ActionListener {

    frmMascota vistaPet = new frmMascota();
    MascotaDAO mDAO = new MascotaDAO();
    Mascota pet = new Mascota();
    Helpers help = new Helpers();
    Dueno dueno = new Dueno();
    DuenoDAO dDAO = new DuenoDAO();

    public ControllerMascota(frmMascota vPet) {
        this.vistaPet = vPet;
        this.vistaPet.btnGuardar.addActionListener(this);
        this.vistaPet.btnEditar.addActionListener(this);
        this.vistaPet.btnEliminar.addActionListener(this);
        this.vistaPet.btnRefrescar.addActionListener(this);
        this.vistaPet.btnCancelar.addActionListener(this);
        this.vistaPet.btnBuscarDueño.addActionListener(this);
        this.vistaPet.btnBuscar.addActionListener(this);
        this.vistaPet.tglbtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPet.btnGuardar) {
            if (validacion() == 1) {
                agregarMascota();
            }
        }
        if (e.getSource() == vistaPet.btnRefrescar) {
             vistaPet.tglbtn.setSelected(false);
            vistaPet.tglbtn.setText("ACTIVO");
            buscarTablaFiltroCategoria(vistaPet.tblMascota, vistaPet.cbCategoria.getSelectedItem().toString(), "");
        }
        if (e.getSource() == vistaPet.btnCancelar) {
            eliminarEspacios();
        }
        if (e.getSource() == vistaPet.btnEliminar) {
            if (vistaPet.txtId.getText().isEmpty()) {
                JOptionPane.showInputDialog(vistaPet, "Debe seleccionar una mascota");
            }else{
            eliminarMascota();
        }}
        if (e.getSource() == vistaPet.btnEditar) {
            if (validacion() == 1) {
                editarMascota();
            }
        }
        if (e.getSource() == vistaPet.btnBuscar) {
              vistaPet.tglbtn.setSelected(false);
            vistaPet.tglbtn.setText("ACTIVO");
            buscarTablaFiltroCategoria(vistaPet.tblMascota, vistaPet.cbCategoria.getSelectedItem().toString(), vistaPet.txtBuscar.getText());
        }
        if (e.getSource() == vistaPet.btnBuscarDueño) {
            cargarDuenoPorID();
        } if (e.getSource() == vistaPet.tglbtn) {
            if (vistaPet.tglbtn.isSelected()) {
                mDAO.mostrarTablaPorEstado(vistaPet.tblMascota, 1);
                help.centrarCeldas(vistaPet.tblMascota);
            } else {
                mDAO.mostrarTablaPorEstado(vistaPet.tblMascota, 0);
                help.centrarCeldas(vistaPet.tblMascota);
            }
        }
    }

    public void cargarDuenoPorID() {
        String idDueno = JOptionPane.showInputDialog(null, "Ingrese un ID de dueno");
        dueno = dDAO.cargarDuenoPorId(Integer.parseInt(idDueno));
        if (dueno.getNombre() == null) {
            JOptionPane.showMessageDialog(null, "El id no corresponde a ningun nombre");
            vistaPet.txtNombreDueño.setText("");
            vistaPet.txtIdDueño.setText("");
        } else {
            vistaPet.txtIdDueño.setText(String.valueOf(dueno.getIdDueno()));
            vistaPet.txtNombreDueño.setText(dueno.getNombre());
        }
    }

    public int agregarMascota() {

        int r = 0;

        String nombre = vistaPet.txtNombre.getText();
        String genero = vistaPet.cbGenero.getSelectedItem().toString();
        String tipo = vistaPet.txtTipo.getText();
        String raza = vistaPet.txtRaza.getText();
        boolean activo = vistaPet.cbxActivo.isSelected();
        int idDueno = Integer.parseInt(vistaPet.txtIdDueño.getText());

        pet.setNombre(nombre);
        pet.setGenero(genero);
        pet.setTipo(tipo);
        pet.setRaza(raza);
        if (activo == true) {
            pet.setActivo(1);
        } else {
            pet.setActivo(0);
        }
        dueno.setIdDueno(idDueno);
        pet.setDueño(dueno);

        r = mDAO.agregarMascota(pet);

        if (r == 1) {
            JOptionPane.showMessageDialog(null, "Se ha agregado la mascota con exito");
            eliminarEspacios();
            buscarTablaFiltroCategoria(vistaPet.tblMascota, vistaPet.cbCategoria.getSelectedItem().toString(), "");

        } else {
            JOptionPane.showMessageDialog(null, "No se ha agregado la mascota, intente de nuevo");
        }

        return r;
    }

    public int editarMascota() {

        int r = 0;

        int id = Integer.parseInt(vistaPet.txtId.getText());
        String nombre = vistaPet.txtNombre.getText();
        String genero = vistaPet.cbGenero.getSelectedItem().toString();
        String tipo = vistaPet.txtTipo.getText();
        String raza = vistaPet.txtRaza.getText();
        boolean activo = vistaPet.cbxActivo.isSelected();
        int idDueno = Integer.parseInt(vistaPet.txtIdDueño.getText());

        pet.setIdMascota(id);
        pet.setNombre(nombre);
        pet.setGenero(genero);
        pet.setTipo(tipo);
        pet.setRaza(raza);
        if (activo == true) {
            pet.setActivo(1);
        } else {
            pet.setActivo(0);
        }
        dueno.setIdDueno(idDueno);
        pet.setDueño(dueno);
        
        r = mDAO.editarMascota(pet);

        if (r == 1) {
            JOptionPane.showMessageDialog(null, "Se ha editado la mascota con exito");
            eliminarEspacios();
            buscarTablaFiltroCategoria(vistaPet.tblMascota, vistaPet.cbCategoria.getSelectedItem().toString(), "");

        } else {
            JOptionPane.showMessageDialog(null, "No se ha editado la mascota, intente de nuevo");
        }

        return r;
    }

    public int eliminarMascota() {

        int r = 0;

        int id = Integer.parseInt(vistaPet.txtId.getText());
        pet.setIdMascota(id);

        r = mDAO.eliminarMascota(pet);

        if (r == 1) {
            JOptionPane.showMessageDialog(null, "Se ha elminado la mascota con exito");
            eliminarEspacios();
            buscarTablaFiltroCategoria(vistaPet.tblMascota, vistaPet.cbCategoria.getSelectedItem().toString(), "");

        } else {
            JOptionPane.showMessageDialog(null, "No se ha elminado la mascota, intente de nuevo");
        }

        return r;
    }

    public void buscarTablaFiltroCategoria(JTable tablaPet, String categoria, String filtro) {

        mDAO.filtrarTablaFiltroCategoria(tablaPet, categoria, filtro);
        help.centrarCeldas(tablaPet);
    }

    public int validacion() {

        int r = 0;

        if (vistaPet.txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un nombre");
        } else if (vistaPet.txtTipo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un tipo de mascota");
        } else if (vistaPet.txtRaza.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar una raza de mascota");
        } else if (vistaPet.txtIdDueño.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un dueno de la mascota");
        } else {
            r++;
        }
        return r;
    }

    public void eliminarEspacios() {

        vistaPet.txtId.setText("");
        vistaPet.txtNombre.setText("");
        vistaPet.txtTipo.setText("");
        vistaPet.txtRaza.setText("");
        vistaPet.txtIdDueño.setText("");
        vistaPet.txtNombreDueño.setText("");
        vistaPet.cbGenero.setSelectedIndex(0);
        vistaPet.cbxActivo.setSelected(false);

    }

}
