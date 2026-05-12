package Reset.Experiment.PhaseNum;

import Reset.PMS_ANDNode;
import Reset.PhaseRelyComponent;
import Reset.getProbability;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class experimentPhaseNum_30 {

    public static void main(String[] args) {

        int[][] Rely = {

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
                {4, 5},

                {1, 2, 3},
                {2, 3, 4, 5},
                {1, 2, 5},
                {1, 2},
                {1, 3, 4, 5},

                {1, 2, 3, 4, 5},
                {2, 4},
                {1, 3, 5},
                {2, 4},
                {1, 3}


        };


        getProbability countProbability = new getProbability();
        List<String> listData = new ArrayList<String>();

        listData.add("10 30 20 30 20 25 15 20 30 40 10 30 20 30 20 25 15 20 30 40 20 15 25 35 25 20 10 15 15 25");


        listData.add("0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002");
        listData.add("0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002");
        listData.add("0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002");
        listData.add("0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002");
        listData.add("0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002 0.001 0.0015 0.002 0.001 0.005 0.001 0.005 0.001 0.0015 0.002");
        ArrayList<ArrayList<String>> List = countProbability.getData(listData);
        ArrayList<ArrayList<Double>> List2 = countProbability.changelist(List);



        PMS_ANDNode beforeCombineTreeTest = new PMS_ANDNode();

        beforeCombineTreeTest.phase = Rely.length;

        beforeCombineTreeTest.MaxComponents = beforeCombineTreeTest.MaxNum(Rely);

        PhaseRelyComponent phaseRelyComponent = new PhaseRelyComponent();

        phaseRelyComponent.Rely = Rely;

        beforeCombineTreeTest.phaseRelyComponent = phaseRelyComponent;
        beforeCombineTreeTest.LabelLib = new ArrayList<>();

        beforeCombineTreeTest.LabelLib.add("3/4");
        beforeCombineTreeTest.LabelLib.add("2/3");
        beforeCombineTreeTest.LabelLib.add("2/2");
        beforeCombineTreeTest.LabelLib.add("1/2");
        beforeCombineTreeTest.LabelLib.add("1/2");

        beforeCombineTreeTest.LabelLib.add("3/3");
        beforeCombineTreeTest.LabelLib.add("2/2");
        beforeCombineTreeTest.LabelLib.add("2/2");
        beforeCombineTreeTest.LabelLib.add("4/5");
        beforeCombineTreeTest.LabelLib.add("2/3");

        beforeCombineTreeTest.LabelLib.add("2/2");
        beforeCombineTreeTest.LabelLib.add("4/5");
        beforeCombineTreeTest.LabelLib.add("3/3");
        beforeCombineTreeTest.LabelLib.add("2/3");
        beforeCombineTreeTest.LabelLib.add("2/2");

        beforeCombineTreeTest.LabelLib.add("4/4");
        beforeCombineTreeTest.LabelLib.add("2/3");
        beforeCombineTreeTest.LabelLib.add("2/2");
        beforeCombineTreeTest.LabelLib.add("3/4");
        beforeCombineTreeTest.LabelLib.add("1/2");

        beforeCombineTreeTest.LabelLib.add("3/3");
        beforeCombineTreeTest.LabelLib.add("4/4");
        beforeCombineTreeTest.LabelLib.add("3/3");
        beforeCombineTreeTest.LabelLib.add("2/2");
        beforeCombineTreeTest.LabelLib.add("3/4");

        beforeCombineTreeTest.LabelLib.add("4/5");
        beforeCombineTreeTest.LabelLib.add("1/2");
        beforeCombineTreeTest.LabelLib.add("3/3");
        beforeCombineTreeTest.LabelLib.add("1/2");
        beforeCombineTreeTest.LabelLib.add("2/2");



        beforeCombineTreeTest.listPathOne = new ArrayList<String>();
        beforeCombineTreeTest.listPathZero = new ArrayList<String>();

        beforeCombineTreeTest.ComponentNumber = 1;//组件结点值
        beforeCombineTreeTest.Mark = 2;//是什么结点，非汇聚为2，否则0，1
        beforeCombineTreeTest.way = new ArrayList<String>();
        String beforeCombineString = "1";
        beforeCombineTreeTest.way.add(beforeCombineString);
        beforeCombineTreeTest.canBeCombinePhaseNum = 0;




        int times= 10000;
        double beforeCombineTreeTime =0;
        long startTime = 0;
        long endTime = 0;
        long CombinestartTime = 0;
        long CombineendTime = 0;
        double resultBeforeCombine = 0;
        PMS_ANDNode beforeCombineTree = new PMS_ANDNode();
        for (int i = 0; i < times; i++) {
            startTime = System.currentTimeMillis();//获取当前时间currentTimeMillis()  nanoTime()
            beforeCombineTree = beforeCombineTree.getBeforeCombineTreeTest(beforeCombineTreeTest);
            beforeCombineTree.ROLL(beforeCombineTree);
            endTime = System.currentTimeMillis();
            resultBeforeCombine= countProbability.Tree_Result(beforeCombineTree.listPathOne, List2);
            beforeCombineTreeTime+= endTime - startTime;

        }
        beforeCombineTreeTime  = (double)(beforeCombineTreeTime  /times);

        System.out.println("阶段合并前失效的路径数：" + beforeCombineTree.listPathOne.size());
        System.out.println("阶段合并前不失效的路径数：" + beforeCombineTree.listPathZero.size());
//        System.out.println("阶段合并前系统失效概率为：" + countProbability.Tree_Result(beforeCombineTree.listPathOne, List2));
        System.out.println("阶段合并前系统失效概率为：" + resultBeforeCombine);
        System.out.println("程序运行时间：" + beforeCombineTreeTime + "ms");
        System.out.println("===================================================================================");

        //启动阶段合并算法 重新开始计算时间
        //控制变量，但阶段数增长时候，可以被合并掉的阶段数一直为总阶段数的一半


        PMS_ANDNode afterCombineTree = new PMS_ANDNode();
        double afterCombineTreeTime =0;
        double resultAfterCombine = 0;
        double CombineNeedTime = 0;
        for (int i = 0; i < times; i++) {
            startTime = System.currentTimeMillis();
            CombinestartTime = System.currentTimeMillis();
            afterCombineTree = beforeCombineTree.getAfterCombineTree(beforeCombineTree);
            CombineendTime = System.currentTimeMillis();
            afterCombineTree.ROLL(afterCombineTree);
            resultAfterCombine = countProbability.Tree_ResultWithMap(afterCombineTree.listPathOne, List2,afterCombineTree.combineMap);
            endTime = System.currentTimeMillis();
            CombineNeedTime += CombineendTime- CombinestartTime;
            afterCombineTreeTime += endTime - startTime;

        }

        afterCombineTreeTime  = (double)(afterCombineTreeTime  /times);
        CombineNeedTime  = (double)( CombineNeedTime  /times);

        System.out.println("可以被合并的阶段数"+afterCombineTree.canBeCombinePhaseNum);
        System.out.println("阶段合并后失效的路径数：" + afterCombineTree.listPathOne.size());
        System.out.println("阶段合并后不失效的路径数：" + afterCombineTree.listPathZero.size());
        //            如果规模有变化  重新开始计算时间
//        System.out.println("阶段合并后系统失效概率为：" + countProbability.Tree_ResultWithMap(afterCombineTree.listPathOne, List2,afterCombineTree.combineMap));
        System.out.println("阶段合并后系统失效概率为：" + resultAfterCombine);
        System.out.println("阶段合并算法合并预处理时间：" + CombineNeedTime + "ms");
        System.out.println("采用阶段合并算法后程序运行时间：" + afterCombineTreeTime + "ms");

        System.out.println("----------------------------------------------------------------------------");

/*        for (Map.Entry<Integer, ArrayList<Integer>> entry : afterCombineTree.combineMap.entrySet()) {
            System.out.println("新的阶段："+entry.getKey());
            int size = entry.getValue().size();
            for (int s = 0; s < size; s++) {
                System.out.print(entry.getValue().get(s)+1+" ");
            }
            System.out.println();
        }*/


    }

}
