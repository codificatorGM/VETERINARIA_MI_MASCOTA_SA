/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell PC
 */
public class Conexion {

    private static final String DATABASE = "utilidadesdb";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASSWORD = "luigi009";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?user=" + USER + "&password=" + PASSWORD + "&useSSL=false";

    private Connection con;

    public Conexion() {
        con = null;
        try {
            Class.forName(DRIVER);
            con = (Connection) DriverManager.getConnection(URL);
            if (con != null) {
                System.out.println("Conexión a la BD con éxito");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            System.err.println(ex);

        }
    }

    /**
     * Retorna la conexión a la BD
     *
     * @return
     */
    public Connection getConnection() {
        return con;
    }

    /**
     * Permite desconectar la BD
     */
    public void desconectar() {
        try {
            con.close();
            System.exit(0);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
