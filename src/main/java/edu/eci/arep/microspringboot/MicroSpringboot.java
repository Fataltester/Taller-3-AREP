/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.eci.arep.microspringboot;

import static edu.eci.arep.microspringboot.HTTPServer.loadRestControllers;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

/**
 *
 * @author juan.mmendez
 */
public class MicroSpringboot {

    public static void main(String[] args) throws IOException, URISyntaxException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, Exception {
        System.out.println("Starting MicroSpringBoot:");
        HTTPServer.startServer("edu.eci.arep.microspringboot");
        
    }
}
