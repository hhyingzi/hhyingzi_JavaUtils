package MyDataStructure.Sort;

/**
 * 优先级队列（堆排序）：数组堆，大顶堆
 * 堆索引同数组索引，[0, length-1]
 */
public class MyMaxPriorityQueues {
    private int[] arr;
    private int size = 0;

    public MyMaxPriorityQueues(int maxSize){
        this.arr = new int[maxSize+1];
    }

    public MyMaxPriorityQueues(int[] inputArr){
        arr = new int[inputArr.length*2]; //方便执行add，因为没有写成动态数组
        for(int i=0; i<inputArr.length; i++){
            arr[i] = inputArr[i];
            size++;
        }
        initHeap(arr);
    }

    //上浮
    public static void swim(int[] arr, int k){
        while(k > 0){
            int parentIndex = (k-1)/2;
            if(arr[k] > arr[parentIndex]){
                int temp = arr[k];
                arr[k] = arr[parentIndex];
                arr[parentIndex] = temp;
                k = parentIndex;
            }
            else break;
        }
    }

    //下沉
    public static void sink(int[] arr, int k, int tail){
        for(int i=k; i*2+1 <= tail; ){
            int maxChild = i*2+1;
            if(maxChild+1 <= tail && arr[maxChild] < arr[maxChild+1]) maxChild++;
            if(arr[i] < arr[maxChild]){
                int temp = arr[i];
                arr[i] = arr[maxChild];
                arr[maxChild] = temp;
                i = maxChild;
            }
            else break;
        }
    }

    //堆排序 [0, length]，依赖函数 sink()
    public static void heapSort(int[] arr){
        if(arr == null || arr.length <= 1) return;
        //建堆从最后一个父节点，一直往前
        for(int i = arr.length/2-1; i >= 0; i--){
            sink(arr, i, arr.length-1);
        }
        //将堆顶依次与最后一个元素交换，并缩小堆
        int N = arr.length-1;
        while(N >= 0){
            int temp = arr[N];
            arr[N] = arr[0];
            arr[0] = temp;
            N--;
            sink(arr, 0, N);
        }
    }

    public void initHeap(int[] arr){
        if(arr == null || arr.length <= 1) return;
        //建堆从最后一个父节点，一直往前
        for(int i = size/2-1; i >= 0; i--){
            sink(arr, i, size-1);
        }
    }

    public void add(int newData){
        arr[size] = newData;
        swim(arr, size);
        size++;
    }

    public int remove(){
        int result = arr[0];
        arr[0] = arr[size-1];
        size--;
        sink(arr, 0, size-1);
        return result;
    }

    public static void testSort(int[] arr){
        heapSort(arr);
    }
}
