package MyDataStructure.BasicStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 *  B- 树，多路平衡查找树。
 *  m 阶 B- 树，即 m 路平衡查找树，每个节点有 m 个子树 和 m-1 个 key-value 对。
 *      m 阶 B- 树，全称为 (m/2, m)- 树，分别代表每个节点可以拥有的 最少 和 最多 的子树数量。
 *      对 m 阶 B-树，最多 m 个子树，最少 m/2 个。分支树多了要进行分裂操作，少了要进行合并操作。
 *      对 m 阶 B-树，最多 m-1 个关键码，最少 m/2-1 个。根节点不受最少关键码的限制，因为无可合并。
 *  对每个节点，将key，value，child都用数组（或列表）存储。
 *      其中 keyList 从左往右递增，并且:
 *      key = keyList.get(keyIndex) 恒大于左子树 childList.get(keyIndex)，
 *      key = keyList.get(keyIndex) 恒小于右相邻子树 childList.get(keyIndex + 1)
 */
public class MyB_Tree {
    public Node root;
    public final int _order = 3; //树的阶次，多路平衡查找树至少为3阶
    public int size;  //树的节点总数
    public class Node{
        ArrayList<Integer> keyList = new ArrayList<>();
        ArrayList<Integer> valueList = new ArrayList<>();
        Node parent;
        ArrayList<Node> childList = new ArrayList<>();
    }
    Node _hot;
    public LinkedHashMap<Integer, Integer> resultLinkedHashMap = new LinkedHashMap<>();

    //根据 key 查找对应的 value
    public int get(int key){
        Node node = this.root;
        while(node != null){
            //对 node.keyList 进行二分查找，获取 key 应该在的位置（右端点）
            int keyIndex = Collections.binarySearch(node.keyList, key);
            //命中
            if(node.keyList.get(keyIndex) == key) return node.valueList.get(keyIndex);
            else{
                node = node.childList.get(keyIndex+1);
            }
        }
        return -1;  //未找到
    }

    //插入 key-value 对，如果 key 已有则修改其 value。并重平衡。
    public void put(int key, int value){}
    //根据 key 寻找对应的 Node，命中则返回 node，未命中则返回 null（_hot 已被设置为其父节点）
    private Node searchNode(int key){ return null; }
    //删除key对应的节点，并重平衡。
    public void delete(int key){}
}
