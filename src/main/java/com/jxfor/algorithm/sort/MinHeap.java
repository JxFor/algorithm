package com.jxfor.algorithm.sort;

import java.util.Arrays;

/**
 * 最小堆
 *
 * 1）0 位置为空，为了更好的计算父节点
 *    index 的左右节点为 2*index 2*index+1
 *    index 的父节点为 index/2
 *
 * @author jxfor
 * @date 2020/9/28 9:42
 */
public class MinHeap {

    private int[] values =  new int[16];

    private int size = 1;

    /**
     * 自下向上
     * @param item
     */
    public void push( int item ){
        if(size >= values.length) {
            Arrays.copyOf(values, size << 1) ;
        }

        values[size++]=item;
        int index = size - 1;
        while (values[index] < values[index/2] && index > 1){
            swap(index,index/2);
            index = index/2;
        }
    }

    /**
     * 自上向下
     */
    public void pop( ){
        if( size <= 1 ){
            return;
        }

        values[1] = values[size-1];
        values[--size]=0;

        int index = 1 ;
        int l = 2 * index ;
        while ( l < size ){
            if( (l+1 < size) && values[l+1] < values[l]){
                l++;
            }
            if( values[index] <= values[l] ){
                break;
            }
            swap(index,l);
            index = l;
            l = 2 * index +1;
        }

    }

    private void swap( int i , int j ){
        int tmp = values[i];
        values[i] = values[j];
        values[j] = tmp;
    }

    public void print( ){
        for( int i = 1 ; i < size;i++){
            System.out.print(values[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();
        minHeap.push(4);
        minHeap.push(5);
        minHeap.push(2);
        minHeap.push(6);
        minHeap.push(1);
        minHeap.print();

        minHeap.pop();
        minHeap.print();

        minHeap.pop();
        minHeap.print();
    }

}
