/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import helpers.Helpers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.ReportesDAO;
import modelo.Mascota;
import modelo.MascotaDAO;
import modelo.Usuario;
import modelo.UsuarioDAO;
import modelo.Veterinario;
import modelo.VeterinarioDAO;
import vista.frmCitasDueno;
import vista.frmCitasFecha;

/**
 *
 * @author oddso
 */
public class ControllerReportes implements ActionListener{

        ReportesDAO dao = new ReportesDAO();
        frmCitasDueno vistaDueno = new frmCitasDueno();
        frmCitasFecha vistaFecha = new frmCitasFecha();
        Helpers help = new Helpers();
        Mascota mascota = new Mascota();
        Veterinario vet = new Veterinario();
        VeterinarioDAO vetDAO = new VeterinarioDAO();
        Usuario user = new Usuario();
        UsuarioDAO userDAO = new UsuarioDAO();
        MascotaDAO mascDAO = new MascotaDAO();
        

    public ControllerReportes(frmCitasDueno frm) {
        this.vistaDueno = frm;
        this.vistaDueno.btnExportar.addActionListener(this);
        this.vistaDueno.btnRefresh.addActionListener(this);
        this.vistaDueno.btnBuscar.addActionListener(this);
        this.vistaDueno.jButton1.addActionListener(this);
    }

    public ControllerReportes(frmCitasFecha frm) {
        this.vistaFecha = frm;
        this.vistaFecha.btnExportar.addActionListener(this);
        this.vistaFecha.btnRefresh.addActionListener(this);
        this.vistaFecha.btnBuscar.addActionListener(this);
        this.vistaFecha.btnCargar.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
//Empiezan sources de dueño
        if (e.getSource() == vistaDueno.btnExportar) {
            try {
                help.exportarExcel(vistaDueno.tblCitasD, JOptionPane.showInputDialog(vistaDueno, "Ingrese el nombre de la hoja"));
            } catch (IOException ex) {
            }
        }
        if (e.getSource() == vistaDueno.btnBuscar) {
            help.centrarCeldas(vistaDueno.tblCitasD);
            filtrarTablaDueno(vistaDueno.tblCitasD, JOptionPane.showInputDialog(vistaDueno, "Ingrese una cedula"));
        }
        if (e.getSource() == vistaDueno.btnRefresh) {
            dao.tablaTodo(vistaDueno.tblCitasD);
            vistaDueno.txtIdMascota.setText("");
            vistaDueno.txtIdUsuario.setText("");
            vistaDueno.txtIdVet.setText("");
            vistaDueno.txtNomMasc.setText("");
            vistaDueno.txtNomUser.setText("");
            vistaDueno.txtNomVet.setText("");
            help.centrarCeldas(vistaDueno.tblCitasD);
        }
      
        if (e.getSource() == vistaDueno.jButton1) {
            if (vistaDueno.txtIdMascota.getText().isEmpty() || vistaDueno.txtIdVet.getText().isEmpty() || vistaDueno.txtIdUsuario.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaDueno, "Debe seleccionar una cita");
            } else {
                cargarGentePorID();
            }
        }
//Empiezan sources de fecha
        if (e.getSource() == vistaFecha.btnBuscar) {
            Date inicio = vistaFecha.txtDateFirst.getDate();
            Date fin = vistaFecha.txtDateFinal.getDate();

            if (inicio == null || fin == null) {
                JOptionPane.showMessageDialog(vistaFecha, "Debe ingresar ambas fechas para filtrar");
                return;
            }
            filtrarTablaFechas(vistaFecha.tblContents, inicio, fin);
            help.centrarCeldas(vistaFecha.tblContents);
        }
        if (e.getSource() == vistaFecha.btnRefresh) {
            dao.tablaTodo(vistaFecha.tblContents);
            vistaFecha.txtIdMascota.setText("");
            vistaFecha.txtIdUsuario.setText("");
            vistaFecha.txtIdVet.setText("");
            vistaFecha.txtNomMasc.setText("");
            vistaFecha.txtNomUser.setText("");
            vistaFecha.txtNomVet.setText("");
            help.centrarCeldas(vistaFecha.tblContents);
        }
    
        if (e.getSource() == vistaFecha.btnCargar) {
            if (vistaFecha.txtIdMascota.getText().isEmpty() || vistaFecha.txtIdVet.getText().isEmpty() || vistaFecha.txtIdUsuario.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vistaFecha, "Debe seleccionar una cita");
            } else {
                cargarGentePorID1();
            }
        }if (e.getSource() == vistaFecha.btnExportar) {
            try {
                help.exportarExcel(vistaFecha.tblContents, JOptionPane.showInputDialog(vistaFecha, "Ingrese el nombre de la hoja"));
            } catch (IOException ex) {
            }
        }
    }


    public void filtrarTablaDueno(JTable table, String cedulaDueno){
        dao.filtrarTablaDueno(vistaDueno.tblCitasD, cedulaDueno);
    }
    public void cargarGentePorID(){
    String idMascota = vistaDueno.txtIdMascota.getText();
    mascota = mascDAO.cargarMascotaPorId(Integer.parseInt(idMascota));
    String idVeterinario = vistaDueno.txtIdVet.getText();
    vet = vetDAO.cargarVeterinarioPorId(Integer.parseInt(idVeterinario));
    String idUsuario = vistaDueno.txtIdUsuario.getText();
    user = userDAO.cargarUsuarioPorId(Integer.parseInt(idUsuario));
    if (mascota.getNombre() == null) {
        JOptionPane.showMessageDialog(vistaDueno, "El id no corresponde a ningun nombre de mascota");
        vistaDueno.txtIdMascota.setText("");
        vistaDueno.txtNomMasc.setText("");
    } else {
        vistaDueno.txtIdMascota.setText(String.valueOf(mascota.getIdMascota()));
        vistaDueno.txtNomMasc.setText(mascota.getNombre());
    }if (vet.getNombre() == null) {
        JOptionPane.showMessageDialog(vistaDueno, "El id no corresponde a ningun nombre de veterinario");
        vistaDueno.txtIdVet.setText("");
        vistaDueno.txtNomVet.setText("");
    } else {
        vistaDueno.txtIdVet.setText(String.valueOf(vet.getId()));
        vistaDueno.txtNomVet.setText(vet.getNombre());
    }if (user.getNombre() == null) {
        JOptionPane.showMessageDialog(vistaDueno, "El id no corresponde a ningun nombre de veterinario");
        vistaDueno.txtIdUsuario.setText("");
        vistaDueno.txtNomUser.setText("");
    } else {
        vistaDueno.txtIdUsuario.setText(String.valueOf(user.getIdUsuario()));
        vistaDueno.txtNomUser.setText(user.getNombre());
    }
   
}    
    public void inicio(){
    dao.tablaTodo(vistaDueno.tblCitasD);
    dao.tablaTodo(vistaFecha.tblContents);
    help.centrarCeldas(vistaDueno.tblCitasD);
    help.centrarCeldas(vistaFecha.tblContents);
    }
    public void cargarGentePorID1(){
    String idMascota = vistaFecha.txtIdMascota.getText();
    mascota = mascDAO.cargarMascotaPorId(Integer.parseInt(idMascota));
    String idVeterinario = vistaFecha.txtIdVet.getText();
    vet = vetDAO.cargarVeterinarioPorId(Integer.parseInt(idVeterinario));
    String idUsuario = vistaFecha.txtIdUsuario.getText();
    user = userDAO.cargarUsuarioPorId(Integer.parseInt(idUsuario));
    if (mascota.getNombre() == null) {
        JOptionPane.showMessageDialog(vistaFecha, "El id no corresponde a ningun nombre de mascota");
        vistaFecha.txtIdMascota.setText("");
        vistaFecha.txtNomMasc.setText("");
    } else {
        vistaFecha.txtIdMascota.setText(String.valueOf(mascota.getIdMascota()));
        vistaFecha.txtNomMasc.setText(mascota.getNombre());
    }if (vet.getNombre() == null) {
        JOptionPane.showMessageDialog(vistaFecha, "El id no corresponde a ningun nombre de veterinario");
        vistaFecha.txtIdVet.setText("");
        vistaFecha.txtNomVet.setText("");
    } else {
        vistaFecha.txtIdVet.setText(String.valueOf(vet.getId()));
        vistaFecha.txtNomVet.setText(vet.getNombre());
    }if (user.getNombre() == null) {
        JOptionPane.showMessageDialog(vistaFecha, "El id no corresponde a ningun nombre de veterinario");
        vistaFecha.txtIdUsuario.setText("");
        vistaFecha.txtNomUser.setText("");
    } else {
        vistaFecha.txtIdUsuario.setText(String.valueOf(user.getIdUsuario()));
        vistaFecha.txtNomUser.setText(user.getNombre());
    }
}
    public void filtrarTablaFechas(JTable table, Date inicio, Date fin){
    java.sql.Date sqlInicio = new java.sql.Date(inicio.getTime());
    java.sql.Date sqlFin = new java.sql.Date(fin.getTime());

    dao.filtrarTablaFechas(table, sqlInicio, sqlFin);
}
}
   
