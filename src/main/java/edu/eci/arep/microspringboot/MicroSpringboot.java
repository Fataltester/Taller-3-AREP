/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.eci.arep.microspringboot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

/**
 *
 * @author juan.mmendez
 */
public class MicroSpringboot {

    public static void main(String[] args) throws IOException, URISyntaxException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        System.out.println("Starting MicroSpringBoot:");
        HTTPServer.startServer(args);
    }
}
