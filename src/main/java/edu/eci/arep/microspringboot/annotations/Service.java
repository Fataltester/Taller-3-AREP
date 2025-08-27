/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.microspringboot.annotations;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public interface Service {
    public String executeService(HttpRequest req, HttpResponse res);
}
