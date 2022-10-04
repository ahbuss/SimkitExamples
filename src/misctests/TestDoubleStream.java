/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misctests;

import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.DoubleStream;

/**
 *
 * @author ahbuss
 */
public class TestDoubleStream {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] array = new double[10];
        Random random = new Random();
        for (int i = 0; i < array.length; ++i) {
            array[i] = random.nextDouble();
        }
        System.out.println("Original:");
        DoubleStream stream = DoubleStream.of(array);
        stream.forEach(System.out::println);
        
//        int limit = 3;
//        System.out.println("Only first " + limit);
//        DoubleStream.of(array).limit(limit).forEach(System.out::println);

        stream = DoubleStream.of(array);
        OptionalDouble optional = stream.max();
        System.out.println(optional);
        
        stream = DoubleStream.of(array);
    }
    
}
