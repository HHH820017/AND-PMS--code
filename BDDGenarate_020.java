package BDDTest;

import java.util.*;

public class BDDGenarate_02 {

    static List<List<Integer>>  res = new ArrayList<>();
    static Deque<Integer> list = new ArrayDeque<>();
    static ArrayList<BDDTree> bddTreePhaseArrayList = new ArrayList<>();
    public static void main(String[] args) {

        int times= 1000;
        double beforeCombineTreeTime =0;
        long startTime = 0;
        long endTime = 0;

        int[][] beforeCombineRely = {

                {1, 2, 3, 4},
                {2, 3, 5},
                {1, 3},
                {2, 4},
                {4, 5},

                {1, 2, 3},
                {1, 4},
                {1, 5},
                {1, 2, 3, 4,5},
                {2, 3, 4},

                {1, 4},
                {1, 2, 3, 4, 5},
                {1, 2 , 5},
                {3, 4, 5},
                {1, 3},

                {1, 2, 3, 4},
                {2, 3, 4},
                {2, 5},
                {1, 2, 3, 4},
                {4, 5}

        };
//        int[][] beforeCombineRely = {{1,2,3,4,5}};
//        int[][] beforeCombineRely = {{1,2,3},{3,4} ,{2,3}};and
//        int[][] beforeCombineRely = {{1,2,3},{3,4} ,{2,3}}; or
//        int[][] beforeCombineRely = {{1,2,4},{3,4} ,{1,2,3}};
//        int[][] beforeCombineRely = {{1,2,3},{3,4} ,{2,3}};

        ArrayList<String> beforeCombineLabel = new ArrayList<String>();
        beforeCombineLabel.add("3/4");
        beforeCombineLabel.add("2/3");
        beforeCombineLabel.add("2/2");
        beforeCombineLabel.add("1/2");
        beforeCombineLabel.add("1/2");
        beforeCombineLabel.add("3/3");
        beforeCombineLabel.add("2/2");
        beforeCombineLabel.add("2/2");
        beforeCombineLabel.add("4/5");
        beforeCombineLabel.add("2/3");



        beforeCombineLabel.add("2/2");
        beforeCombineLabel.add("4/5");
        beforeCombineLabel.add("3/3");
        beforeCombineLabel.add("2/3");
        beforeCombineLabel.add("2/2");
        beforeCombineLabel.add("4/4");
        beforeCombineLabel.add("2/3");
        beforeCombineLabel.add("2/2");
        beforeCombineLabel.add("3/4");
        beforeCombineLabel.add("1/2");

        for (int i = 0; i < times; i++) {

            startTime = System.currentTimeMillis();//获取当前时间currentTimeMillis()  nanoTime()
            geneatePhaseBdd(beforeCombineRely, beforeCombineLabel);

            BDDTree PMS_BDD = bddTreePhaseArrayList.get(0);
            for (int j = 1; j < bddTreePhaseArrayList.size(); j++) {

                PMS_BDD = and(PMS_BDD, bddTreePhaseArrayList.get(j));
            }
            bddTreePhaseArrayList.removeAll(bddTreePhaseArrayList);
//            System.out.println("最终PMS:"+PMS_BDD);
            endTime = System.currentTimeMillis();
            beforeCombineTreeTime+= endTime - startTime;

        }

         beforeCombineTreeTime  = (double)(beforeCombineTreeTime  /times);
        System.out.println("合并前阶段数为："+beforeCombineRely.length);
        System.out.println("合并前程序运行时间：" + beforeCombineTreeTime + "ms");


        System.out.println("============================================================================================");
        /////////阶段合并算法==============================================================

        ArrayList<String> afterCombineLabel = new ArrayList<String>();

        int[] combinePhase = combinePhase(beforeCombineRely,beforeCombineRely.length,beforeCombineLabel);
        System.out.print("可以被合并的数组有（数组中非0的阶段）：");
        for (int i = 0; i < combinePhase.length; i++) {

            System.out.print(combinePhase[i]+" ");
        }
        System.out.println();

        int afterCombineLength = 0;
        for (int i = 0; i < combinePhase.length; i++) {

            if(combinePhase[i] == 0)
            {
                afterCombineLength++;
            }
        }
        int[][] afterCombineRely = new int[afterCombineLength][];

        int idx = 0;
        for (int i = 0; i < combinePhase.length; i++) {

                if(combinePhase[i] == 0)
                {
                    afterCombineRely[idx]= beforeCombineRely[i];
                    idx++;
                    afterCombineLabel.add(beforeCombineLabel.get(i));
                }
        }

        double afterCombineTreeTime =0;

        for (int i = 0; i < times; i++) {
            startTime = System.currentTimeMillis();//获取当前时间currentTimeMillis()  nanoTime()
            geneatePhaseBdd(afterCombineRely,afterCombineLabel);
            BDDTree PMS_BDD = bddTreePhaseArrayList.get(0);
            for (int j = 1; j < bddTreePhaseArrayList.size(); j++) {

                PMS_BDD = and(PMS_BDD, bddTreePhaseArrayList.get(j));
            }
//            System.out.println("最终PMS为: "+PMS_BDD);
            bddTreePhaseArrayList.removeAll(bddTreePhaseArrayList);
            endTime = System.currentTimeMillis();
            afterCombineTreeTime += endTime - startTime;
        }

        afterCombineTreeTime  = (double)(afterCombineTreeTime  /times);

        System.out.println("合并后阶段数为："+afterCombineRely.length);
        System.out.println("合并后程序运行时间：" + afterCombineTreeTime + "ms");




    }


    public static  void geneatePhaseBdd(int[][] Rely, ArrayList<String> label)
    {
        for (int i = 0; i < Rely.length; i++) {

            String[] split = label.get(i).split("/");
            int numk = Integer.parseInt(split[0]);
            int failk = Rely[i].length - numk +1;

            res.removeAll(res);
            res = SucessInteraction(Rely[i],failk);

            ArrayList<BDDTree> bddTreeArrayList = new ArrayList<>();

//            System.out.println(res);

            for (List<Integer> list : res) {

                BDDTree bddTreeForPhase = new BDDTree(list.get(0),i+1, null, null);

                for (int t = 1; t < list.size(); t++) {

                    bddTreeForPhase = and(bddTreeForPhase, new BDDTree(list.get(t),i+1,null,null));
//                    System.out.println(bddTreeForPhase);
                }


//                System.out.println(bddTreeForPhase);

                bddTreeArrayList.add(bddTreeForPhase);
            }

            BDDTree bddTreefFinal = bddTreeArrayList.get(0);
            for (int j = 1; j < bddTreeArrayList.size(); j++) {

                bddTreefFinal = or(bddTreefFinal, bddTreeArrayList.get(j));

            }
//            System.out.println("最终的单阶段BDD为 ："+bddTreefFinal);
            bddTreePhaseArrayList.add(bddTreefFinal);
        }

    }

    public static BDDTree and(BDDTree one , BDDTree two)
    {


            if (one.BddId < two.BddId)
            {
                BDDTree tmpBDD = new BDDTree();
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
                BDDTree tmpBDD = new BDDTree();
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
                BDDTree tmpBDD = new BDDTree();
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



    public static BDDTree or(BDDTree one , BDDTree two)
    {

            if (one.BddId < two.BddId)
            {

                BDDTree tmpBDD = new BDDTree();
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
                BDDTree tmpBDD = new BDDTree();
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
                BDDTree tmpBDD = new BDDTree();
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


    //================阶段合并算法==========================================================================
    //此方法可以合并的阶段标记为1，并返回一个数组。数组中标记为1的阶段即为可以表示
    public static int[] combinePhase(int[][] Rely, int phasnum, ArrayList<String> labelLib) {
        int[] combine = new int[phasnum];

        for (int i = 0; i < phasnum; i++) {
            for (int j = i + 1; j < phasnum; j++) {
                int res = canBeCombine(Rely[i], Rely[j], labelLib.get(i), labelLib.get(j));
                if (res == 1)       //符合Case1情况
                {
                    combine[j] = 1;

                } else if (res == 2)    //符合Case2情况
                {
                    combine[j] = 2;

                } else if (res == 3)        //符合Case3情况
                {
                    combine[j] = 3;

                }

            }
        }

        return combine;
    }

    //此方法用于判断 两个阶段是否可以合并
    public static int canBeCombine(int[] arrFirst, int[] arrSecond, String firstKN, String secondKN) {
        int ni = arrFirst.length;
        int nj = arrSecond.length;

        String[] splitFirst = firstKN.split("/");
        String[] splitSecond = secondKN.split("/");

        int ki = Integer.parseInt(splitFirst[0]);
        int kj = Integer.parseInt(splitSecond[0]);

        Arrays.sort(arrFirst);
        Arrays.sort(arrSecond);

        ArrayList<Integer> commonComponentTmp = new ArrayList<>(); //统计相同元件，并放入集合
        // 采用双指针，来遍历两个数组，获取拥有共同的元件的数量
        for (int i = 0, j = 0; i < ni && j < nj; ) {
            if (arrFirst[i] == arrSecond[j]) {
                commonComponentTmp.add(arrFirst[i]);
                i++;
                j++;
            } else if (arrFirst[i] > arrSecond[j]) {
                j++;
            } else {
                i++;
            }
        }

        int[] commonComponent = new int[commonComponentTmp.size()]; //将集合转为数组 方便计算
        int idx = 0;
        for (int common : commonComponentTmp) {

            commonComponent[idx++] = common;
        }

        int n = commonComponent.length;

//		Case one  可以合并的第一种情况
        if (HasContainRelation(arrSecond, arrFirst) && kj - ki >= 0 && nj - ni <= 0) {
            return 1;
        }

//		Case Two 可以合并的第二种情况
        if (HasContainRelation(arrFirst, arrSecond) && kj - ki >= nj - ni && nj - ni >= 0) {
            return 2;
        }

//		Case Three 可以合并的第三种情况 （一般型）
        if (n > 0 && kj - ki >= nj - n) {
            return 3;
        }

        return 0;

    }

    //此方法用于判断两个阶段元件集合的包含关系
    public static boolean HasContainRelation(int[] first, int[] second) {
        List<Integer> listOne = new ArrayList<>();
        List<Integer> listTwo = new ArrayList<>();

        for (int i = 0; i < first.length; i++) {
            listOne.add(first[i]);
        }
        for (int i = 0; i < second.length; i++) {
            listTwo.add(second[i]);
        }

        if (first.length > second.length) {
            return false;
        }
        if (listTwo.containsAll(listOne)) {
            return true;
        }
        return false;
    }
}
