/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.microspringboot;

import edu.eci.arep.microspringboot.annotations.GetMapping;
import edu.eci.arep.microspringboot.annotations.RestController;
import edu.eci.arep.microspringboot.annotations.RequestParam;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    @GetMapping("/greeting")
    public static String greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return "Hello "+name;
        
    }
    
}
