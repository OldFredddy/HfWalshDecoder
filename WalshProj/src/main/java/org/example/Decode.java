package org.example;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

//porog-допустимое количество ошибок
public class Decode {
    public static List<String> decArrWT=new ArrayList<>();
    private static int intNumOfSym;
    public static List<Character> fastDecode(List<Character> codeArr,List<String> decodeTableWalsh,List<String> decodeTableBin, int porog,String numOfSym){
        List<Character> decArr=new ArrayList<>();
        intNumOfSym = Integer.parseInt(numOfSym);
        decArrWT.clear();
        char[] tempArr=new char[intNumOfSym];
         int x=0;
        for (int i = 0; i < (int)(codeArr.size()/intNumOfSym); i++) {
            for (int j = 0; j < intNumOfSym; j++) {
               tempArr[j]=codeArr.get(x);
               x++;
            }
            decArrWT.add(decodeTableWalsh.get(GetDecodeIndex(tempArr,decodeTableWalsh,porog)));
            decArr.add((decodeTableBin.get(GetDecodeIndex(tempArr,decodeTableWalsh,porog))).charAt(0));
            decArr.add((decodeTableBin.get(GetDecodeIndex(tempArr,decodeTableWalsh,porog))).charAt(1));
            decArr.add((decodeTableBin.get(GetDecodeIndex(tempArr,decodeTableWalsh,porog))).charAt(2));
            decArr.add((decodeTableBin.get(GetDecodeIndex(tempArr,decodeTableWalsh,porog))).charAt(3));
        }
        return decArr;
    }
    private static int GetDecodeIndex(char[] arr,List<String> decodeTableWalsh, int porog){
        int index=0;
        int[] weight=new int[decodeTableWalsh.size()];
        for (int i = 0; i < decodeTableWalsh.size(); i++) {
            for (int j = 0; j < intNumOfSym; j++) {
                if (((decodeTableWalsh.get(i)).charAt(j))==arr[j]){
                    weight[i]++;
                }
            }

        }

        index=FindMaxElementIndex(weight,porog);

        return index;
    }
    private static int FindMaxElementIndex(int[] weight,int porog){
        int Max=-1;
        int indexMax=-1;
        for (int i = 0; i < weight.length; i++) {

            if(weight[i]>Max){
                Max=weight[i];
                indexMax=i;
            }
        }
        if (weight[indexMax]>=(intNumOfSym-porog)){

        } else {
            indexMax=0;
        }
        return indexMax;
    }
}
