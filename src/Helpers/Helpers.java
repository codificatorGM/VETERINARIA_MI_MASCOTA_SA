/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


/**
 *
 * @author hfons
 */
public class Helpers {


    public void abrirArchivo(String url) {
        try {
            String filePath = new File(url).getAbsolutePath();
            File path = new File(filePath);
            Desktop.getDesktop().open(path);
        } catch (IOException ex) {
        }
    }
}