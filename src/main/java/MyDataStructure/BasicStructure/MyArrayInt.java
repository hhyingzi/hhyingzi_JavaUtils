package MyDataStructure.BasicStructure;

public class MyArrayInt {
    private int[] data;
    private int size;

    public MyArrayInt(){
        this.data = new int[10];
        this.size = 10;
    }
    public MyArrayInt(int size){
        this.data = new int[size];
        this.size = size;
    }

    public void resize(int newSize){
        if(newSize<10) newSize=10;
        int[] newData = new int[newSize];
        for(int i=0; i<size; i++){
            newData[i] = data[i];
        }
        this.data = newData;
    }

    public void add(int index, int value){
        if(size == data.length) resize(data.length << 1);
        for(int i=size; i>index; i--){
            data[i] = data[i-1];
        }
        data[index] = value;
        size++;
    }

    public int remove(int index){
        int result = data[index];
        for(int i=index; i<size; i++){
            data[i] = data[i+1];
        }
        size--;
        if(size == data.length/4) resize(data.length >> 1);
        return result;
    }

    public int get(int index){
        return data[index];
    }

    public int length(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }
}
