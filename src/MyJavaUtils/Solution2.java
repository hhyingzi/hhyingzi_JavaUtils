package MyJavaUtils;
import java.util.ArrayList;
import java.util.List;

public class Solution2 {

    public static void main(String[] args){
        String[] m = {"000001", "000773", "260104"};
        String[] n = {"000001", "000003", "260104"};
        List<String> result = new ArrayList<>();
        int m_index=0, n_index=0;
        while(m_index<m.length && n_index<n.length){
            if(m[m_index].compareTo(n[n_index])<0){
                result.add(m[m_index]);
                m_index++;
            }
            else if(m[m_index].compareTo(n[n_index])>0) {
                result.add(n[n_index]);
                n_index++;
            }
            else m_index++;
        }
        if(m_index<m.length){
            while(m_index<m.length){
                result.add(m[m_index]);
                m_index++;
            }
        }
        else if(n_index<n.length){
            while(n_index<n.length){
                result.add(n[n_index]);
                n_index++;
            }
        }

        for(String item:result){
            System.out.println(item);
        }
    }
}
