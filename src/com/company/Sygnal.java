package com.company;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Sygnal {
    public int[] code;
    private static Scanner scan;

    public void scanner() throws FileNotFoundException {
        scan = new Scanner(System.in);

        System.out.println("Press 1 to generate random signal");
        System.out.println("Press 2 to read signal from file");
        int choise = Integer.parseInt(scan.nextLine());


        switch (choise) {
            case 1:
                System.out.print(" Enter the number of data bits: ");
                int size = Integer.parseInt(scan.nextLine());
                generateRandom(size);
                displaySygnal();
                break;
            case 2:
                code = Sygnal.readFromFile();
                displaySygnal();
                break;
        }
    }

    public void writeToFile() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sygnal.txt"))) {
            for (int i = 0; i < code.length; i++) {
                writer.write(code[i] + "");
            }
            System.out.println("size of sygnal " + code.length + "");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] readFromFile() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("sygnal.txt")));
        String temp = sc.next();
        char[] temp2 = temp.toCharArray();
        int [] readSygnal = new int[temp2.length];
        for (int i = 0; i < temp2.length; i++) {
            try {
                readSygnal[i] = temp2[i] - 48;;
            } catch (NumberFormatException nfe) {
            };
        }
        return readSygnal;
    }

    public static double calculateErrorRate(int[]before,int[]after){
        int errors=0;
        for(int i=0;i<before.length;i++){
            if(before[i]!=after[i])
                errors++;
        }
        return (double)errors/(double)before.length;
    }
    public void generateRandom (int size) {
        Random rand = new Random();
        code = rand.ints(0, 2).limit(size).toArray();
    }

    public void displaySygnal (){
        System.out.println("Your signal is: ");
        for (int i = 0; i < code.length; i++) {
            System.out.printf(code[i] + "");
        }
        System.out.println("\n");
    }

}





