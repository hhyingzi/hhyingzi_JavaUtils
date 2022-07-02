package MyDataStructure.BasicStructure;

import java.util.*;
import java.util.function.Consumer;

public class MyArrayE<E> implements Cloneable, Iterable<E>{
    private E[] data;
    //数组中实际元素的数量
    private int size;

    public static final int DEFAULT_CAPACITY = 10;

    //ArrayList默认都初始化为空数组，执行第一个add时，初始化一个长度为10的数组。
    //我这里默认初始化为10。
    public MyArrayE() {
        this.data = (E[]) new Object[10];
    }
    public MyArrayE(int initialCapacity){
        this.data = (E[]) new Object[initialCapacity];
    }

    public void add(E e) {
        if(size == data.length) resize(data.length << 1);
        data[size] = e;
        size++;
    }
    public void add(int index, E e){
        if(size == data.length) resize(data.length << 1);
        for(int i=size; i>index; i--){
            data[i] = data[i-1];
        }
        data[index] = e;
        size++;
    }

    //清空数组，并初始化容量为10
    public void clear(){
        E temp[] = (E[]) new Object[10];
        data = temp;
        size = 0;
    }

    //浅拷贝
    public MyArrayE clone(){
        MyArrayE<E> newArray = new MyArrayE<>(data.length);
        for(int i=0; i<size; i++){
            newArray.add(data[i]);
        }
        return newArray;
    }

    public void edit(int index, E e){
        data[index] = e;
    }

    public int length(){
        return size;
    }

    public E remove(){
        E oldValue = data[size--];
        data[size] = null;
        if(size > 0 && size == data.length/4) resize(data.length >> 1);
        return oldValue;
    }
    public E remove(int index) {
        E oldValue = data[index];
        data[index] = null;
        for(int i=index; i<size; i++){
            data[i] = data[i+1];
        }
        size--;
        if(size > 0 && size == data.length/4) resize(data.length >> 1);
        return data[index];
    }

    //扩容缩容
    private void resize(int newSize){
        if(newSize < DEFAULT_CAPACITY) return;
        E[] temp = (E[]) new Object[newSize];
        //等价于 data=Arrays.copyOf(data, max);
        for(int i=0; i<size; i++){
            temp[i] = data[i];
        }
        data = temp;
    }

    public E search(int index){
        return data[index];
    }
    //public Object clone(); //浅拷贝方法

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Length:" + size + ", Capacity: " + data.length + ", content = [");
        for(int i=0; i<data.length-1; i++){
            builder.append(data[i] + ", ");
        }
        builder.append(data[data.length-1] + "]");
        System.out.println(builder.toString());
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new IterElement();
    }

    class IterElement implements Iterator<E>{
        private int cursorIndex = 0;
        private int last = -1;

        @Override
        public boolean hasNext() {
            return cursorIndex < size;
        }

        @Override
        public E next() {
            last = cursorIndex;
            return data[cursorIndex++];
        }

        @Override
        public void remove() {
            E oldElement = data[last];
            MyArrayE.this.remove(last);
            last=-1;
            cursorIndex--;
        }
    }

    @Override
    public void forEach(Consumer action) {
        //Objects.requireNonNull(action);
        for (int i=0; i < size; i++) {
            action.accept(data[i]);
        }
    }

    @Override
    public Spliterator spliterator() {
        return Iterable.super.spliterator();
    }
}
