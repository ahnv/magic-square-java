package com.ahnv;
import java.lang.*;
import java.util.*;

class MagicSquareHelper implements Runnable{
    static int[] summation = new int[100];
    int[][] matrix;
    int start, type, index; // 1 - Row, 2- Column, 3- Diagnol, 4-DiagnolAlternate
    public MagicSquareHelper(int[][] arr, int i, int index, int type){
        this.matrix = arr;
        this.start = i ;
        this.type = type;
        this.index = index;
    }
    public MagicSquareHelper(){ }
    public int[] getSummation(){ return summation; }
    public void run() {
        switch (this.type){
            case 1:
                summation[this.index] = 0;
                for (int i = 0; i < this.matrix.length; i++) {
                    summation[this.index] += matrix[this.start][i];
                }
                break;
            case 2:
                summation[this.index] = 0;
                for (int i = 0; i < this.matrix.length; i++) {
                    summation[this.index] += matrix[i][this.start];
                }
                break;
            case 3:
                summation[this.index] = 0;
                for (int i = 0; i < this.matrix.length; i++) {
                    summation[this.index] += matrix[i][i];
                }
                break;
            case 4:
                summation[this.index] = 0;
                for (int i = 0; i < this.matrix.length; i++) {
                    summation[this.index] += matrix[this.matrix.length - i - 1][this.matrix.length - i - 1];
                }
                break;
        }
    }
}

class MagicSquare{

    public static void main(String[] args) {
        int rc;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Number of Rows and Columns : ");
        rc = sc.nextInt();
        int[][] matrix = new int[rc][rc];
        int iter = (2*rc) + 2;
        System.out.println("Enter Elements : ");
        for (int  i =0 ; i< rc; i++)
            for (int j = 0; j < rc ; j++)
                matrix[i][j] = sc.nextInt();
        MagicSquareHelper[] magicSquareHelpers = new MagicSquareHelper[iter];
        for (int i = 0 ; i< rc ; i++){
            magicSquareHelpers[i] = new MagicSquareHelper(matrix,i,i,1);
        }
        magicSquareHelpers[rc] = new MagicSquareHelper(matrix,0,rc,3);
        for (int i = 0 ; i< rc ; i++){
            magicSquareHelpers[rc+1+i] = new MagicSquareHelper(matrix,i,rc+1+i,2);
        }
        magicSquareHelpers[2*rc+1] = new MagicSquareHelper(matrix,0,2*rc+1,2);
        Thread[] threads = new Thread[iter];
        for (int i = 0; i < iter ; i++) {
            try {
                threads[i] = new Thread(magicSquareHelpers[i]);
                threads[i].start();
                threads[i].join();
            }catch (InterruptedException e){
                System.out.print(e);
            }
        }
        MagicSquareHelper magicSquareHelper = new MagicSquareHelper();
        int[] sum = magicSquareHelper.getSummation();
        int flag = 0;
        for (int i = 0 ; i < iter; i++){
            if (sum[i] != sum[0]){
                flag = 1;
            }
        }
        if (flag == 0) System.out.print("Given Matrix is a Magic Square");
        else System.out.print("Given Matrix is not a Magic Square");
    }
}
