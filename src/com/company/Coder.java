package com.company;

import java.util.ArrayList;
import java.util.List;

public class Coder {
    public int[]incode(int[] sygnal){
        List<Integer> code = new ArrayList();
        for(int i=0;i<sygnal.length;i++){
            for(int k=0;k<3;k++){
                code.add(sygnal[i]);
            }
        }
        return code.stream().mapToInt(Integer::intValue).toArray();
    }

    int f=0;
    public int[] decode(int[] sygnal){
        int[] base = new int[2];
        List<Integer> code = new ArrayList();
        for(int i=0;i<sygnal.length;i++){
            base[sygnal[i]]++;
            f++;
            if(f%3==0){
                if(base[0]>base[1]) {
                    code.add(0);
                }
                else code.add(1);
                base = new int[2];
            }
        }
        return  code.stream().mapToInt(Integer::intValue).toArray();
    }
}
