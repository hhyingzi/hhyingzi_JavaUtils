package MyDataStructure.BasicStructure;

import java.util.*;
import java.util.function.Consumer;

public class MyArrayE<E> implements Cloneable, Iterable<E>{
    private Object[] data;
    //数组中实际元素的数量
    private int size;

    public static final int DEFAULT_CAPACITY = 10;

    //ArrayList默认都初始化为空数组，执行第一个add时，初始化一个长度为10的数组。
    //我这里默认初始化为10。
    public MyArrayE() {
        this.data = new Object[10];
    }
    public MyArrayE(int initialCapacity){
        this.data = new Object[initialCapacity];
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
        MyArrayE newArray = new MyArrayE<>(data.length);
        for(int i=0; i<size; i++){
            newArray.add(data[i]);
        }
        return newArray;
    }

    public void edit(int index, E e){
        data[index] = e;
    }

    public E get(int index){
        return (E)data[index];
    }

    public int length(){
        return size;
    }

    public E remove(){
        E oldValue = (E) data[size--];
        data[size] = null;
        if(size > 0 && size == data.length/4) resize(data.length >> 1);
        return oldValue;
    }
    public E remove(int index) {
        E oldValue = (E) data[index];
        data[index] = null;
        for(int i=index; i<size; i++){
            data[i] = data[i+1];
        }
        size--;
        if(size > 0 && size == data.length/4) resize(data.length >> 1);
        return (E) oldValue;
    }

    //扩容缩容
    private void resize(int newSize){
        if(newSize < DEFAULT_CAPACITY) return;
        Object[] temp = new Object[newSize];
        //等价于 data=Arrays.copyOf(data, max);
        for(int i=0; i<size; i++){
            temp[i] = data[i];
        }
        data = temp;
    }
    @Override
    public void forEach(Consumer<? super E> action) {
        //用于 forEach() + lambda 表达式，action.accept(param) 将 param 参数传给该 lambda
        for (int i=0; i < size; i++) {
            action.accept((E)data[i]);
        }
    }
    // 迭代器
    @Override
    public Iterator<E> iterator() { return new IterElement(); }
    class IterElement implements Iterator<E>{
        private int cursor = 0;
        private int last = -1;
        @Override
        public boolean hasNext() {
            return cursor != size;
        } //与 ArrayList 源码一致
        @Override
        public E next() {
            if(cursor >= size)
            last = cursor;
            return (E)data[cursor++];
        }
        @Override
        public void remove() {
            E oldElement = (E)data[last];
            MyArrayE.this.remove(last);
            last=-1;
            cursor--;
        }
    }
    @Override
    public Spliterator spliterator() { return Iterable.super.spliterator(); }
    @Override //与 ArrayList 源码一致
    public String toString() {
        Iterator<E> it = iterator();
        if (! it.hasNext()) return "[]";

        StringBuilder sb = new StringBuilder(); sb.append('[');
        E e = it.next(); sb.append(e);
        while(it.hasNext()){
            e = it.next();
            sb.append(", " + e);
        }
        sb.append("]");
        return sb.toString();
    }

    public E[] getArray(){ return (E[]) data; }
    public E getElement(int index){ return (E) data[index]; }
}
