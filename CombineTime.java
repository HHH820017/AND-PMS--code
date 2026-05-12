package BDDTest;

import java.util.ArrayList;

public class BDD_GridTest2 {


    public static void main(String[] args) {


        int times= 1000;
        double beforeCombineTreeTime =0;
        long startTime = 0;
        long endTime = 0;

        int[][] componentsArr ={{1,2,3},{7,8,9,10},{11,16}};
//        ,{3,5},{10, 11} ,1,2 ,2,2
        int[] kArr = {2, 3, 2};
        int[] nArr = {3, 4, 2};



        ArrayList<BDD_Grid> bdd_gridsForPhase = new ArrayList<BDD_Grid>();
        ArrayList<ArrayList<BDD_Grid>> bdd_gridPhaseArrayList  = new ArrayList<ArrayList<BDD_Grid>>();

        for (int k = 0; k < times; k++) {

            startTime = System.currentTimeMillis();//获取当前时间currentTimeMillis()  nanoTime()


            for (int i = 0; i < componentsArr.length; i++) {

                ArrayList<BDD_Grid> tmpBDD = BDDGen(kArr[i], nArr[i], i + 1, componentsArr[i]);
                bdd_gridPhaseArrayList.add(tmpBDD);
            }


            for (int i = 0; i < bdd_gridPhaseArrayList.size(); i++) {

                ArrayList<BDD_Grid> nodeList = bdd_gridPhaseArrayList.get(i);

                for (int j = nodeList.size()-2; j >=0 ; j--) {

                    BDD_Grid node = nodeList.get(j);

                    if(node.RightIndex >0)
                    {
                        node.RightChild = nodeList.get(node.RightIndex);
                    }
                    if(node.LeftIndex >0)
                    {
                        node.LeftChild = nodeList.get(node.LeftIndex);
                    }

                }

                bdd_gridsForPhase.add(nodeList.get(0));
            }


            BDD_Grid  final_PMS_GridBDD = bdd_gridsForPhase.get(0);
            for (int i = 1; i < bdd_gridsForPhase.size(); i++) {

                final_PMS_GridBDD = or(final_PMS_GridBDD, bdd_gridsForPhase.get(i));
            }
//            System.out.println(final_PMS_GridBDD);
            endTime = System.currentTimeMillis();
            beforeCombineTreeTime+= endTime - startTime;


            bdd_gridsForPhase.removeAll(bdd_gridsForPhase);
            bdd_gridPhaseArrayList.removeAll(bdd_gridPhaseArrayList);

        }


        System.out.println("合并后程序运行时间：" + beforeCombineTreeTime + "ms");


    }

    public static ArrayList<BDD_Grid> BDDGen(int k , int n, int phase, int[] phaseArr)
    {
         int index= 0 ;
         int count = 0;
         int countX = 0;
        ArrayList<BDD_Grid> BDD = new ArrayList<>();



        for (int y = 0; y <= k - 1; y++) {
            for (int x = 0; x <= n - k; x++) {

                BDD_Grid node = new BDD_Grid();

                if (x == n - k)
                {
                   node.RightChild = null ;
                }
                else
                {
                    node.RightIndex = index + 1;

                    ++count;
                }

                if (y == k - 1)
                {
                    node.LeftChild = null;
                }
                else
                {
                    node.LeftIndex = index + n - k + 1;

                };

                node.BddPhase= phase;
                node.BddId = phaseArr[countX++];

                BDD.add(node);
                index += 1;
            }
            countX -= count;
            count = 0;
        }

        // console.log(BDD)
        return BDD;

    }


    public static BDD_Grid and(BDD_Grid one , BDD_Grid two)
    {


        if (one.BddId < two.BddId)
        {
            BDD_Grid tmpBDD = new BDD_Grid();
            tmpBDD.BddId = one.BddId;
            tmpBDD.BddPhase= one.BddPhase;
            if(one.LeftChild == null && two == null)
            {
                tmpBDD.LeftChild = null;

            }else if(one.LeftChild == null && two!=null)
            {
                tmpBDD.LeftChild = null;
            }else if(one.LeftChild!=null && two ==null)
            {
                tmpBDD.LeftChild = null;
            }else if(one.LeftChild !=null && two !=null)
            {
                tmpBDD.LeftChild = and(one.LeftChild,two);
            }

            if(one.RightChild == null && two == null)
            {
                tmpBDD.RightChild = null;

            }else if(one.RightChild == null && two!=null)
            {
                tmpBDD.RightChild = two;
            }else if(one.RightChild!=null && two ==null)
            {
                tmpBDD.RightChild = one.RightChild;
            }else if(one.RightChild !=null && two !=null)
            {
                tmpBDD.RightChild = and(one.RightChild,two);
            }

            return tmpBDD;
        }
        else  if (one.BddId > two.BddId)
        {
            BDD_Grid tmpBDD = new BDD_Grid();
            tmpBDD.BddId = two.BddId;
            tmpBDD.BddPhase= two.BddPhase;
            if(two.LeftChild == null && one == null)
            {
                tmpBDD.LeftChild = null;

            }else if(two.LeftChild == null && one!=null)
            {
                tmpBDD.LeftChild = null;
            }else if(two.LeftChild!=null && one ==null)
            {
                tmpBDD.LeftChild = null;
            }else if(two.LeftChild !=null && one !=null)
            {
                tmpBDD.LeftChild = and(two.LeftChild,one);
            }

            if(two.RightChild == null && one == null)
            {
                tmpBDD.RightChild = null;

            }else if(two.RightChild == null && one!=null)
            {
                tmpBDD.RightChild = one;
            }else if(two.RightChild!=null && one ==null)
            {
                tmpBDD.RightChild = two.RightChild;
            }else if(two.LeftChild !=null && one !=null)
            {
                tmpBDD.RightChild = and(two.RightChild,one);
            }

            return tmpBDD;
        }
        else
        {
            BDD_Grid tmpBDD = new BDD_Grid();
            if(one.BddPhase == two.BddPhase)
            {

                tmpBDD.BddId = one.BddId;
                tmpBDD.BddPhase = one.BddPhase;
                if(one.LeftChild == null && two.LeftChild == null)
                {
                    tmpBDD.LeftChild =null;
                }
                else if(one.LeftChild != null && two.LeftChild == null)
                {
                    tmpBDD.LeftChild=null;
                }
                else if(one.LeftChild == null && two.LeftChild != null)
                {
                    tmpBDD.LeftChild=null;
                }
                else if(one.LeftChild != null && two.LeftChild != null)
                {
                    tmpBDD.LeftChild = and(one.LeftChild, two.LeftChild);
                }


                if(one.RightChild == null && two.RightChild == null)
                {
                    tmpBDD.RightChild =null;
                }
                else if(one.RightChild != null && two.RightChild == null)
                {
                    tmpBDD.RightChild=one.RightChild;
                }
                else if(one.RightChild == null && two.RightChild != null)
                {
                    tmpBDD.RightChild=two.RightChild;
                }
                else if(one.RightChild != null && two.RightChild != null)
                {
                    tmpBDD.RightChild = and(one.RightChild, two.RightChild);
                }


            }else if(one.BddPhase < two.BddPhase)
            {
                tmpBDD.BddId = one.BddId;
                tmpBDD.BddPhase= one.BddPhase;
                if(one.LeftChild == null && two == null)
                {
                    tmpBDD.LeftChild  = null;
                }
                else if(one.LeftChild != null  && two == null)
                {
                    tmpBDD.LeftChild  = null;
                }
                else if(one.LeftChild ==null && two !=null)
                {
                    tmpBDD.LeftChild  = null;
                }else if(one.LeftChild !=null && two !=null)
                {
                    tmpBDD.LeftChild = and(one.LeftChild,two);
                }

                if(one.RightChild == null && two.RightChild == null)
                {
                    tmpBDD.RightChild  = null;
                }
                else if(one.RightChild != null  && two.RightChild == null)
                {
                    tmpBDD.RightChild  = one.RightChild;
                }
                else if(one.RightChild ==null && two.RightChild !=null)
                {
                    tmpBDD.RightChild  = two.RightChild;
                }else if(one.RightChild !=null && two.RightChild !=null)
                {
                    tmpBDD.RightChild= and(one.RightChild, two.RightChild);
                }


            }else {
                tmpBDD.BddId = two.BddId;
                tmpBDD.BddPhase= two.BddPhase;
                if(two.LeftChild == null && one == null)
                {
                    tmpBDD.LeftChild  = null;
                }
                else if(two.LeftChild != null  && one == null)
                {
                    tmpBDD.LeftChild  = null;
                }
                else if(two.LeftChild ==null && one !=null)
                {
                    tmpBDD.LeftChild  = null;
                }else if(two.LeftChild !=null && one !=null)
                {
                    tmpBDD.LeftChild = and(two.LeftChild,one);
                }

                if(two.RightChild == null && one.RightChild == null)
                {
                    tmpBDD.RightChild  = null;
                }
                else if(two.RightChild != null  && one.RightChild == null)
                {
                    tmpBDD.RightChild  = two.RightChild;
                }
                else if(two.RightChild ==null && one.RightChild !=null)
                {
                    tmpBDD.RightChild  = one.RightChild;
                }else if(two.RightChild !=null && one.RightChild !=null)
                {
                    tmpBDD.RightChild= and(two.RightChild, two.RightChild);
                }

            }

            return  tmpBDD;

        }

    }



    public static BDD_Grid or(BDD_Grid one , BDD_Grid two)
    {

        if (one.BddId < two.BddId)
        {

            BDD_Grid tmpBDD = new BDD_Grid();
            tmpBDD.BddId = one.BddId;
            tmpBDD.BddPhase = one.BddPhase;
            if(one.LeftChild == null && two == null)
            {
                tmpBDD.LeftChild = null;

            }else if(one.LeftChild == null && two!=null)
            {
                tmpBDD.LeftChild = two;
            }else if(one.LeftChild!=null && two ==null)
            {
                tmpBDD.LeftChild = one.LeftChild;
            }else if(one.LeftChild !=null && two !=null)
            {
                tmpBDD.LeftChild = or(one.LeftChild,two);
            }

            if(one.RightChild == null && two == null)
            {
                tmpBDD.RightChild = null;

            }else if(one.RightChild == null && two!=null)
            {
                tmpBDD.RightChild = null;
            }else if(one.RightChild!=null && two ==null)
            {
                tmpBDD.RightChild = null;
            }else if(one.RightChild !=null && two !=null)
            {
                tmpBDD.RightChild = or(one.RightChild,two);
            }

            return tmpBDD;


        }
        else  if (one.BddId > two.BddId)
        {
            BDD_Grid tmpBDD = new BDD_Grid();
            tmpBDD.BddId = two.BddId;
            tmpBDD.BddPhase= two.BddPhase;
            if(two.LeftChild == null && one == null)
            {
                tmpBDD.LeftChild = null;

            }else if(two.LeftChild == null && one!=null)
            {
                tmpBDD.LeftChild = one;
            }else if(two.LeftChild!=null && one ==null)
            {
                tmpBDD.LeftChild = two.LeftChild;
            }else if(two.LeftChild !=null && one !=null)
            {
                tmpBDD.LeftChild = or(two.LeftChild,one);
            }

            if(two.RightChild == null && one == null)
            {
                tmpBDD.RightChild = null;

            }else if(two.RightChild == null && one!=null)
            {
                tmpBDD.RightChild = null;
            }else if(two.RightChild!=null && one ==null)
            {
                tmpBDD.RightChild = null;
            }else if(two.RightChild !=null && two !=null)
            {
                tmpBDD.RightChild = or(two.RightChild,one);
            }

            return tmpBDD;
        }
        else
        {
            BDD_Grid tmpBDD = new BDD_Grid();
            if(one.BddPhase == two.BddPhase)
            {

                tmpBDD.BddId = one.BddId;
                tmpBDD.BddPhase = one.BddPhase;
                if(one.LeftChild == null && two.LeftChild == null)
                {
                    tmpBDD.LeftChild =null;
                }
                else if(one.LeftChild != null && two.LeftChild == null)
                {
                    tmpBDD.LeftChild=one.LeftChild;
                }
                else if(one.LeftChild == null && two.LeftChild != null)
                {
                    tmpBDD.LeftChild=two.LeftChild;
                }
                else if(one.LeftChild != null && two.LeftChild != null)
                {
                    tmpBDD.LeftChild = or(one.LeftChild, two.LeftChild);
                }


                if(one.RightChild == null && two.RightChild == null)
                {
                    tmpBDD.RightChild =null;
                }
                else if(one.RightChild != null && two.RightChild == null)
                {
                    tmpBDD.RightChild=null;
                }
                else if(one.RightChild == null && two.RightChild != null)
                {
                    tmpBDD.RightChild=null;
                }
                else if(one.RightChild != null && two.RightChild != null)
                {
                    tmpBDD.RightChild = or(one.RightChild, two.RightChild);
                }

            }else if(one.BddPhase < two.BddPhase)
            {
                tmpBDD.BddId = two.BddId;
                tmpBDD.BddPhase= two.BddPhase;
                if(one.LeftChild == null && two.LeftChild == null)
                {
                    tmpBDD.LeftChild =null;
                }
                else if(one.LeftChild != null && two.LeftChild == null)
                {
                    tmpBDD.LeftChild=one.LeftChild;
                }
                else if(one.LeftChild == null && two.LeftChild != null)
                {
                    tmpBDD.LeftChild=two.LeftChild;
                }
                else if(one.LeftChild != null && two.LeftChild != null)
                {
                    tmpBDD.LeftChild = or(one.LeftChild, two.LeftChild);
                }


                if(one == null && two.RightChild == null)
                {
                    tmpBDD.RightChild =null;
                }
                else if(one != null && two.RightChild == null)
                {
                    tmpBDD.RightChild=null;
                }
                else if(one == null && two.RightChild != null)
                {
                    tmpBDD.RightChild=null;
                }
                else if(one != null && two.RightChild != null)
                {
                    tmpBDD.RightChild = or(one, two.RightChild);
                }



            }else {
                tmpBDD.BddId = one.BddId;
                tmpBDD.BddPhase= one.BddPhase;
                if(two.LeftChild == null && one.LeftChild == null)
                {
                    tmpBDD.LeftChild =null;
                }
                else if(two.LeftChild != null && one.LeftChild == null)
                {
                    tmpBDD.LeftChild=two.LeftChild;
                }
                else if(two.LeftChild == null && one.LeftChild != null)
                {
                    tmpBDD.LeftChild=one.LeftChild;
                }
                else if(two.LeftChild != null && one.LeftChild != null)
                {
                    tmpBDD.LeftChild = or(two.LeftChild, one.LeftChild);
                }


                if(two == null && one.RightChild == null)
                {
                    tmpBDD.RightChild =null;
                }
                else if(two != null && one.RightChild == null)
                {
                    tmpBDD.RightChild=null;
                }
                else if(two == null && one.RightChild != null)
                {
                    tmpBDD.RightChild=null;
                }
                else if(two != null && one.RightChild != null)
                {
                    tmpBDD.RightChild = or(two, one.RightChild);
                }


            }



            return  tmpBDD;
        }


    }

}
