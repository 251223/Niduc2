package com.company;

import java.math.*;

/**
 * This is a model for binary symmetric channel. Error occurs with probability 'error_probability'
 */
public class BSChannel {
    double error_probability = 30.0;

    /**
     * Construct a BSC channel with error probability f
     * @param f
     */
    public BSChannel(double f){
        error_probability = f;
    }

    /**
     * Send array x through BSC.
     * Returns the received array
     * @param x
     * @return
     */
    public int[] send(int[] x){
        for(int i = 0; i<x.length;i++)
            if(Math.random()<error_probability){
                if(x[i]==1)
                    x[i]=0;
                else x[i]=1;
            }
        return x;
    }
    public int[] errorForHamming(int[] x, int errors){
        for (int item : x) {
            System.out.print(item);
        }
        System.out.println("");

        for(int i = 0; i<x.length;i++)
            if(Math.random()<error_probability && errors > 0){
                if(x[i]==1)
                    x[i]=0;
                else x[i]=1;
                errors--;
            }
        for (int value : x) {
            System.out.print(value);
        }
        System.out.println("");
        return x;
    }
}