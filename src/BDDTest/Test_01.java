package BDDTest;
import java.util.*;
public class Test_01 {

    static List<List<Integer>>  res = new ArrayList<>();
    static  Deque<Integer> list = new ArrayDeque<>();
    public static void main(String[] args) {




        int[][] Rely = {{1, 2, 3}};

        List<List<Integer>> res = SucessInteraction(Rely[0],1);

        for (List<Integer> list : res) {

            for (Integer x : list) {

                System.out.print(x+" ");
            }
            System.out.println();
        }


    }
    public  static  List<List<Integer>> SucessInteraction(int[] candidates, int k) {
        if(candidates.length==0){
            return res;
        }
        Arrays.sort(candidates);

        backtraing(candidates, k, 0 );

        return res;

    }

    public static void backtraing(int[] candidates , int k, int idx )
    {
        if( list.size() == k)
        {
            res.add( new ArrayList<>(list));
            return;
        }

        for (int i = idx; i < candidates.length; i++) {

            list.addLast(candidates[i]);
            backtraing(candidates, k, i+1);
            list.removeLast();

        }

    }
}
