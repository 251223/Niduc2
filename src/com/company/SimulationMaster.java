package com.company;

import java.io.BufferedReader;

import java.io.InputStreamReader;


public class SimulationMaster {
    Sygnal sygnal = new Sygnal();
    Coder coder = new Coder();
    String menu = "Choose encoding technique\n" +
            "1.Triple code with voting algorithm\n" +
            "2.Hamming code\n" +
            "3.Reed-Solomon code\n" +
            "0.Exit\n";

    void start() {
        while (true) {
            System.out.println(menu);
            int d = getChoice();
            if (d == 0)
                return;


            switch (d) {
                case 1: {
                    this.initSignal();
                    //incoding com.company.Sygnal
                    int[] code = coder.incode(sygnal.code);
                    System.out.printf("\n");
                    for (int i = 0; i < code.length; i++) {
                        System.out.printf(code[i] + "");
                    }
                    //Sedning thru binary symmetric channel, specifying the rate of an error
                    BSChannel channel = new BSChannel(0.25);
                    code = channel.send(code);
                    //Outputting result
                    System.out.printf("\n");
                    for (int i = 0; i < code.length; i++) {
                        System.out.printf(code[i] + "");
                    }

                    //Decoding sygnal
                    int[] result = coder.decode(code);
                    System.out.printf("\n");
                    for (int i = 0; i < result.length; i++) {
                        System.out.printf(result[i] + "");
                    }

                    System.out.println("\nBit Error rate:" + Sygnal.calculateErrorRate(sygnal.code, result));
                    break;
                }
                case 2: {
                    this.initSignal();
                    int[] signalForHamming = sygnal.code;
                    int[] hammingCode = HammingCode.generateCode(signalForHamming);
                    BSChannel channel = new BSChannel(0.25);
                    int[] brokenCode = channel.errorForHamming(hammingCode, 1);
                    int[] resulted = HammingCode.decode(brokenCode, hammingCode.length - signalForHamming.length);
                    for (int i = 0; i < resulted.length; i++) {
                        System.out.print(resulted[i]);
                    }
                    System.out.println("\n");
                    break;
                }
                case 3: {
                    ReedSolomon codec = new ReedSolomon(4, 2);
                    byte[][] shards = {
                            new byte[]{'A', 'B', 'C', 'D'},
                            new byte[]{'E', 'F', 'G', 'H'},
                            new byte[]{'I', 'G', 'K', 'L'},
                            new byte[]{'I', 'G', 'K', 'L'},
                            new byte[]{0, 0, 0, 0},
                            new byte[]{0, 0, 0, 0}
                    };
                    System.out.println("Your data is:");
                    for (byte[] ddd : shards) {
                        for (byte ddd2 : ddd) {
                            System.out.print(ddd2 + " ");
                        }
                        System.out.println("");
                    }
                    System.out.println(" \n ");
                    codec.encodeParity(shards, 0, 4);
                    //I assume we generate a boolean array of trues, where there is a small chance we gat a false
                    //Its still unclear what do we do if more then acceptable amount of shards go missing
                    // (more than 2 in this case)
                    System.out.println("Data before errors");
                    for (byte[] ddd : shards) {
                        for (byte ddd2 : ddd) {
                            System.out.print(ddd2 + " ");
                        }
                        System.out.println("");
                    }
                    System.out.println(" \n ");

                    System.out.println("Data after errors :");
                    for (int i = 2; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            shards[i][j] = 0;
                        }
                    }
                    for (byte[] ddd : shards) {
                        for (byte ddd2 : ddd) {
                            System.out.print(ddd2 + " ");
                        }
                        System.out.println("");
                    }
                    System.out.println(" \n ");

                    boolean[] present = {true, true, false, false, true, true};
                    codec.decodeMissing(shards, present, 0, 4);
                    for (byte[] ddd : shards) {
                        for (byte ddd2 : ddd) {
                            System.out.print(ddd2 + " ");
                        }
                        System.out.println("");
                    }

                    System.out.println(" \n ");
                    break;
                }
                case 0: {
                    return;
                }
                default: {
                    System.out.println("No such option. Try again\n");
                }
            }
        }
    }

    static int getChoice() {
        String input;
        int k = 0;
        int f = 0;
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        do {
            f = 0;
            try {
                input = reader.readLine();
                k = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Incorrect input. Try again");
                f = 1;
            }
        } while (f == 1);

        return k;

    }

    public void initSignal() {
        try {
            sygnal.scanner();
        } catch (Exception e) {
            System.out.println("Bad path or file type or data\n. Try again.\n");
        }
    }

    public static void main(String[] args) {
        SimulationMaster simulation = new SimulationMaster();
        simulation.start();
    }
}


