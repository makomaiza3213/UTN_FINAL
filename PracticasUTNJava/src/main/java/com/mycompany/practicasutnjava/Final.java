/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practicasutnjava;
/**
 *
 * @author makom
 */
import java.io.IOException;
import java.sql.SQLException;

public class Final{

    public static void main(String[] args) throws SQLException, IOException {
                        
        //creamos el menú de dialogo para solicitar una operacion
        OperacionesMenu menu = new OperacionesMenu();
        menu.menuPrincipal();
        
    }
}

