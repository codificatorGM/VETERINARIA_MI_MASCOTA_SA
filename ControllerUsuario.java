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
import modelo.Usuario;
import modelo.UsuarioDAO;
import vista.frmUsuarios;

public class ControllerUsuario implements ActionListener {

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    Usuario usuario = new Usuario();
    frmUsuarios vistaUsuario = new frmUsuarios();
    Helpers help = new Helpers();

    public ControllerUsuario(frmUsuarios frm) {
        this.vistaUsuario = frm;
        this.vistaUsuario.btnGuardar.addActionListener(this);
        this.vistaUsuario.btnEditar.addActionListener(this);
        this.vistaUsuario.btnEliminar.addActionListener(this);
        this.vistaUsuario.btnBuscar.addActionListener(this);
        this.vistaUsuario.btnRefrescar.addActionListener(this);
        this.vistaUsuario.btnCancelar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaUsuario.btnGuardar) {
            if (validacion() == 1) {
                agregarUsuario();
                limpiarCampos();
                filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
            }
        }

        if (e.getSource() == vistaUsuario.btnEditar) {
            if (validacion() == 1) {
                actualizarUsuario();
                limpiarCampos();
                filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
            }
        }

            if (e.getSource() == vistaUsuario.btnEliminar) {
                if (vistaUsuario.txtIdUsuario.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(vistaUsuario, "Error, debe ingresar un ID válido");
                } else {
                    eliminarUsuario();
                    filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
                }
            }
            if (e.getSource() == vistaUsuario.btnBuscar) {

                filtrarTablaPorNombre(vistaUsuario.tblUsuarios, vistaUsuario.txtBuscar.getText());
            }

            if (e.getSource() == vistaUsuario.btnRefrescar) {

                filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
            }
            if (e.getSource() == vistaUsuario.btnCancelar) {
                limpiarCampos();
            }      
    }

    public void agregarUsuario() {
        String nombre = vistaUsuario.txtNombre.getText();
        String apellido1 = vistaUsuario.txtApellido1.getText();
        String apellido2 = vistaUsuario.txtApellido2.getText();
        String email = vistaUsuario.txtEmail.getText();
        String nombreUsuario = vistaUsuario.txtNombreUsuario.getText();
        String password = vistaUsuario.txtPassword.getText();
        String tipoUsuario = vistaUsuario.cbTipoUsuario.getSelectedItem().toString();
        boolean activo = vistaUsuario.cbActivo.isSelected();
        
        usuario.setNombre(nombre);
        usuario.setApellido1(apellido1);
        usuario.setApellido2(apellido2);
        usuario.setEmail(email);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(password);
        usuario.setTipoUsuario(tipoUsuario);
        if (activo == true) {
            usuario.setActivo(1);
        } else {
            usuario.setActivo(0);
        }

        int r = usuarioDAO.agregarUsuario(usuario);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaUsuario, "USUARIO REGISTRADO CON EXITO");
            filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaUsuario, "USUARIO NO REGISTRADO");
        }
    }

    public void actualizarUsuario() {

        int idUsuario = Integer.parseInt(vistaUsuario.txtIdUsuario.getText());
        String nombre = vistaUsuario.txtNombre.getText();
        String apellido1 = vistaUsuario.txtApellido1.getText();
        String apellido2 = vistaUsuario.txtApellido2.getText();
        String email = vistaUsuario.txtEmail.getText();
        String nombreUsuario = vistaUsuario.txtNombreUsuario.getText();
        String password = vistaUsuario.txtPassword.getText();
        String tipoUsuario = vistaUsuario.cbTipoUsuario.getSelectedItem().toString();
        boolean activo = vistaUsuario.cbActivo.isSelected();

        usuario.setIdUsuario(idUsuario);
        usuario.setNombre(nombre);
        usuario.setApellido1(apellido1);
        usuario.setApellido2(apellido2);
        usuario.setEmail(email);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(password);
        usuario.setTipoUsuario(tipoUsuario);
        if (activo == true) {
            usuario.setActivo(1);
        } else {
            usuario.setActivo(0);
        }
        int r = usuarioDAO.actualizarUsuario(usuario);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaUsuario, "USUARIO ACTUALIZADO CON ÉXITO");
            filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaUsuario, "USUARIO NO ACTUALIZADO, INTENTE MÁS TARDE");
        }
    }

    public void eliminarUsuario() {
        int idUsuario = Integer.parseInt(vistaUsuario.txtIdUsuario.getText());
        int r = usuarioDAO.eliminarUsuario(idUsuario);

        if (r == 1) {
            JOptionPane.showMessageDialog(vistaUsuario, "USUARIO ELIMINADO EXITOSAMENTE");
            filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(vistaUsuario, "USUARIO NO ELIMINADO, INTENTE MÁS TARDE");
        }
    }

    public void filtrarTablaPorNombre(JTable table, String filtro) {
        usuarioDAO.filtarTablaPorNombre(table, filtro);
        help.centrarCeldas(table);
    }

    public void limpiarCampos() {

        vistaUsuario.txtIdUsuario.setText("");
        vistaUsuario.txtNombre.setText("");
        vistaUsuario.txtApellido1.setText("");
        vistaUsuario.txtApellido2.setText("");
        vistaUsuario.txtEmail.setText("");
        vistaUsuario.txtNombreUsuario.setText("");
        vistaUsuario.txtPassword.setText("");
        vistaUsuario.cbTipoUsuario.setSelectedIndex(0);

    }

    public int validacion() {
        int r = 0;

        if (vistaUsuario.txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaUsuario, "Error, debe ingresar un nombre");
        } else if (vistaUsuario.txtApellido1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaUsuario, "Error, debe ingresar un apellido 1");
        } else if (vistaUsuario.txtApellido2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaUsuario, "Error, debe ingresar un apellido 2");
        } else if (vistaUsuario.txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaUsuario, "Error, debe ingresar un email");
        } else if (vistaUsuario.txtNombreUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaUsuario, "Error, debe ingresar un nombre de usuario");
        } else if (vistaUsuario.txtPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaUsuario, "Error, debe ingresar una contraseña");  
        } else if (vistaUsuario.cbTipoUsuario.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(vistaUsuario, "Error, debe seleccionar un tipo usuario");
        } else {
            r++;
        }
        return r;
    }

    public void iniciar() {

        limpiarCampos();
        filtrarTablaPorNombre(vistaUsuario.tblUsuarios, "");
        help.centrarCeldas(vistaUsuario.tblUsuarios);
    }
}
