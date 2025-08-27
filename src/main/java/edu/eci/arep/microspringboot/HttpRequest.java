/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.microspringboot;

import java.net.URI;

/**
 *
 * @author juan.mmendez
 */
public class HttpRequest {
    URI reqUri = null;
    HttpRequest(URI requestUri) {
        reqUri = requestUri;
    }
    public String getValue(String paramName){
        String paramValue = reqUri.getQuery().split("=")[1];
        return paramValue;
    }
}
