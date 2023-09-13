/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import helpers.Helpers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.Cita;
import modelo.Mascota;
import modelo.MascotaDAO;
import modelo.RegistroCitasDAO;
import modelo.Usuario;
import modelo.UsuarioDAO;
import modelo.Veterinario;
import modelo.VeterinarioDAO;
import vista.frmRegistroCita;

/**
 *
 * @author juanj
 */
public class ControllerRegistroCitas implements ActionListener {

    MascotaDAO mDAO = new MascotaDAO();
    Mascota mascota = new Mascota();
    VeterinarioDAO vDAO = new VeterinarioDAO();
    Veterinario vet = new Veterinario();
    UsuarioDAO uDAO = new UsuarioDAO();
    Usuario user = new Usuario();
    frmRegistroCita vistaRegistroCita = new frmRegistroCita();
    RegistroCitasDAO registroCitasDAO = new RegistroCitasDAO();
    Cita cita = new Cita();
    Helpers help = new Helpers();

    public ControllerRegistroCitas(frmRegistroCita frm) {
        this.vistaRegistroCita = frm;
        this.vistaRegistroCita.btnGuardar.addActionListener(this);
        this.vistaRegistroCita.btnEditar.addActionListener(this);
        this.vistaRegistroCita.btnBuscarIdMascota.addActionListener(this);
        this.vistaRegistroCita.btnBuscarMascota.addActionListener(this);
        this.vistaRegistroCita.btnBuscarVeterinario.addActionListener(this);
        this.vistaRegistroCita.btnBuscarUsuario.addActionListener(this);
        this.vistaRegistroCita.btnCancelar.addActionListener(this);
        this.vistaRegistroCita.btnEliminar.addActionListener(this);
        this.vistaRegistroCita.btnRefrescar.addActionListener(this);
    }

     @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vistaRegistroCita.btnGuardar) {
            if (validacion() == 1) {
                agregarRegistro();
                limpiarCampos();
                filtrarTablaPorId(vistaRegistroCita.tblRegistroCitas, "");
            }
        }
        if (e.getSource() == vistaRegistroCita.btnEditar) {

            if (validacion() == 1) {
                actualizarRegistro();
                limpiarCampos();
                filtrarTablaPorId(vistaRegistroCita.tblRegistroCitas, "");
            }
        }
            if (e.getSource() == vistaRegistroCita.btnCancelar) {

                limpiarCampos();
            }
            if (e.getSource() == vistaRegistroCita.btnBuscarIdMascota) {
                if (vistaRegistroCita.txtBuscarIdMascota == null) {
                    JOptionPane.showMessageDialog(vistaRegistroCita, "Debe ingresar el id de la mascota");
                } else {
                    registroCitasDAO.filtarTablaPorIdMascota(vistaRegistroCita.tblRegistroCitas, vistaRegistroCita.txtBuscarIdMascota.getValue().toString());
                }
            }
            if (e.getSource() == vistaRegistroCita.btnRefrescar) {
                tabla(vistaRegistroCita.tblRegistroCitas);
                vistaRegistroCita.txtBuscarIdMascota.setValue(0);
                help.centrarCeldas(vistaRegistroCita.tblRegistroCitas);

            }
            if (e.getSource() == vistaRegistroCita.btnBuscarMascota) {

             cargarMascotaPorId();

         }
         if (e.getSource() == vistaRegistroCita.btnBuscarUsuario) {

             cargarUserPorId();

         }
         if (e.getSource() == vistaRegistroCita.btnBuscarVeterinario) {

             cargarVetPorId();
         }
            if (e.getSource() == vistaRegistroCita.btnEliminar) {
                if (vistaRegistroCita.txtIdCita.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(vistaRegistroCita, "Debe seleccionar una cita");
                } else {
                eliminarCita();
                tabla(vistaRegistroCita.tblRegistroCitas);
            }
            }
    }
    
    public void cargarMascotaPorId() {

        String idMascota = JOptionPane.showInputDialog(vistaRegistroCita, "Ingrese un id de mascota");

        mascota = mDAO.cargarMascotaPorId(Integer.parseInt(idMascota));

        if (mascota.getNombre() == null) {

            JOptionPane.showMessageDialog(vistaRegistroCita, "No se encontró ningúna mascota, Intente de nuevo");
            vistaRegistroCita.txtIdMascota.setText("");
            vistaRegistroCita.txtNombreMascota.setText("");

        } else {

            vistaRegistroCita.txtIdMascota.setText(String.valueOf(mascota.getIdMascota()));
            vistaRegistroCita.txtNombreMascota.setText(mascota.getNombre());

        }
    }

    public void cargarVetPorId() {

        String idVeterinario = JOptionPane.showInputDialog(vistaRegistroCita, "Ingrese un id de veterinario");

        vet = vDAO.cargarVeterinarioPorId(Integer.parseInt(idVeterinario));

        if (vet.getNombre() == null) {

            JOptionPane.showMessageDialog(vistaRegistroCita, "No se encontró ningun veterinario, Intente de nuevo");
            vistaRegistroCita.txtIdVeterinario.setText("");
            vistaRegistroCita.txtNombreVeterinario.setText("");

        } else {

            vistaRegistroCita.txtIdVeterinario.setText(String.valueOf(vet.getId()));
            vistaRegistroCita.txtNombreVeterinario.setText(vet.getNombre());

        }

    }

    public void cargarUserPorId() {

        String idUsuario = JOptionPane.showInputDialog(vistaRegistroCita, "Ingrese un id de usuario");

        user = uDAO.cargarUsuarioPorId(Integer.parseInt(idUsuario));

        if (user.getNombre() == null) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "No se encontró ningun usuario, Intente de nuevo");
            vistaRegistroCita.txtIdUsuario.setText("");
            vistaRegistroCita.txtNombreUsuario.setText("");
        } else {
            vistaRegistroCita.txtIdUsuario.setText(String.valueOf(user.getIdUsuario()));
            vistaRegistroCita.txtNombreUsuario.setText(user.getNombre());
        }
    }

    public void filtrarTablaPorId(JTable table, String filtro) {
        registroCitasDAO.filtarTablaPorIdMascota(table, filtro);
        help.centrarCeldas(table);
        
    }

    public void tabla(JTable table) {
        registroCitasDAO.tabla(table);
    }

    public void limpiarCampos() {

        vistaRegistroCita.txtIdCita.setText("");
        vistaRegistroCita.txtIdMascota.setText("");
        vistaRegistroCita.txtNombreMascota.setText("");
        vistaRegistroCita.txtIdVeterinario.setText("");
        vistaRegistroCita.txtNombreVeterinario.setText("");
        vistaRegistroCita.txtIdUsuario.setText("");
        vistaRegistroCita.txtNombreUsuario.setText("");
        vistaRegistroCita.txtAsunto.setText("");
        vistaRegistroCita.dateFecha.setDate(null);
        vistaRegistroCita.lblHoraCita.setText("");

    }

    public void agregarRegistro() {
        String idMascota = vistaRegistroCita.txtIdMascota.getText();
        String idVeterinario = vistaRegistroCita.txtIdVeterinario.getText();
        String idUsuario = vistaRegistroCita.txtIdUsuario.getText();
        String hora = vistaRegistroCita.spinHr.getValue().toString()+":"+ vistaRegistroCita.spinMin.getValue().toString()+":00";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaSQL = dateFormat.format(vistaRegistroCita.dateFecha.getDate());

        String asunto = vistaRegistroCita.txtAsunto.getText();
        String fechaCreacion = vistaRegistroCita.txtFecha.getText();

        cita.setIdMascota(idMascota);
        cita.setIdVeterinario(idVeterinario);
        cita.setIdUsuario(idUsuario);
        cita.setHora(hora);
        cita.setFecha(fechaSQL);
        cita.setAsunto(asunto);
        cita.setFechaCreacion(fechaCreacion);

        int r = registroCitasDAO.agregarCita(cita);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "REGISTRO REALIZADO CON EXITO");
            tabla(vistaRegistroCita.tblRegistroCitas);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaRegistroCita, "REGISTRO NO EXITOSO");
        }
    }

    public void actualizarRegistro() {

        int idCita = Integer.parseInt(vistaRegistroCita.txtIdCita.getText());
        String idMascota = vistaRegistroCita.txtIdMascota.getText();
        String idVeterinario = vistaRegistroCita.txtIdVeterinario.getText();
        String idUsuario = vistaRegistroCita.txtIdUsuario.getText();
        String hora = vistaRegistroCita.spinHr.getValue().toString()+":"+ vistaRegistroCita.spinMin.getValue().toString()+":00";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaSQL = dateFormat.format(vistaRegistroCita.dateFecha.getDate());

        String asunto = vistaRegistroCita.txtAsunto.getText();
        String fechaCreacion = vistaRegistroCita.txtFecha.getText();

        cita.setIdCita(idCita);
        cita.setIdMascota(idMascota);
        cita.setIdVeterinario(idVeterinario);
        cita.setIdUsuario(idUsuario);
        cita.setHora(hora);
        cita.setFecha(fechaSQL);
        cita.setAsunto(asunto);
        cita.setFechaCreacion(fechaCreacion);

        int r = registroCitasDAO.actualizarCita(cita);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "CITA ACTUALIZADA CON ÉXITO");
            tabla(vistaRegistroCita.tblRegistroCitas);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaRegistroCita, "CITA NO ACTUALIZADA, INTENTE MÁS TARDE");
        }
    }

    public void inicio() {
        limpiarCampos();
        tabla(vistaRegistroCita.tblRegistroCitas);
        help.centrarCeldas(vistaRegistroCita.tblRegistroCitas);
    }

    public int eliminarCita() {

        int r = 0;
        int id = Integer.parseInt(vistaRegistroCita.txtIdCita.getText());

        cita.setIdCita(id);

        r = registroCitasDAO.eliminarCita(cita);

        if (r == 1) {
            JOptionPane.showMessageDialog(null, "Se ha eliminado la cita con exito");
            tabla(vistaRegistroCita.tblRegistroCitas);
            limpiarCampos();

        } else {
            JOptionPane.showMessageDialog(null, "No ha eliminado la cita, intente de nuevo");

        }

        return r;

    }

    public int validacion() {
        int hr = (int) vistaRegistroCita.spinHr.getValue();
        int min = (int) vistaRegistroCita.spinMin.getValue();
        int r = 0;

        if (vistaRegistroCita.txtIdMascota.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "Error, debe ingresar un ID de Mascota");
        } else if (vistaRegistroCita.txtIdVeterinario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "Error, debe ingresar un ID de Veterinario");
        } else if (vistaRegistroCita.txtIdUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "Error, debe ingresar un ID de Usuario");
        } else if (vistaRegistroCita.txtAsunto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "Error, debe ingresar un asunto");
        } else if (vistaRegistroCita.dateFecha.getDateFormatString().isEmpty()) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "Error, debe ingresar una fecha de creacion");
        }else if (hr < 0 || hr > 23) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "Error, debe ingresar una hora valida");
        }else if (min < 0 || hr > 59) {
            JOptionPane.showMessageDialog(vistaRegistroCita, "Error, debe ingresar un minuto valido");
        } else {
            r++;
        }
        return r;
    }
}
