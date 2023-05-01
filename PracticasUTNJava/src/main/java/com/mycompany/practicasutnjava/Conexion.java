/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;
/**
 *
 * @author makom
 */
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conectar = null;

    String usuario = "root";
    String contraseña = "multihero0.96";
    String bd = "Universidad";
    String ip = "localhost";
    String puerto = "3306";

    String ruta = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

    public Connection establecerConexion() {

        try {
            // carga el controlador de la base de datos , creo q es el DBMS
            Class.forName("com.mysql.cj.jdbc.Driver");
            // estable la conexion con la base de datos utilizando la ruta usuario y contraseña
            conectar = DriverManager.getConnection(ruta, usuario, contraseña);
            
            System.out.println("Se conecto correctamente");
            

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "NO SE PUDO CONECTAR CORRECTAMENTE." + e);

        }

        return conectar;
    }
}