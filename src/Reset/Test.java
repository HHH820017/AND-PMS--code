package Reset;

import tree.ExpFunction;
import tree.PhaseRelyCom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class Test {
    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();//获取当前时间currentTimeMillis()  nanoTime()

//        int[][] Rely = {{1, 2, 3}, {1, 2, 3, 4}, {1, 3, 4}};

        int[][] Rely = {{1,2,3,4,5},{1,2,3,6},{1,2,3},{1,2,3},{7,8},{8,9},{8,9,10},{7,8,9,10},{11,12,13},{11,14},{11,15},{11,16}};

        PMS_ANDNode beforeCombineTree = new PMS_ANDNode();

        beforeCombineTree.phase = Rely.length;

        beforeCombineTree.MaxComponents = beforeCombineTree.MaxNum(Rely);

        PhaseRelyComponent phaseRelyComponent = new PhaseRelyComponent();

        phaseRelyComponent.Rely = Rely;

        beforeCombineTree.phaseRelyComponent = phaseRelyComponent;
        beforeCombineTree.LabelLib = new ArrayList<>();
 /*       beforeCombineTree.LabelLib.add("2/3");
        beforeCombineTree.LabelLib.add("2/4");
        beforeCombineTree.LabelLib.add("2/3");*/
       beforeCombineTree.LabelLib.add("1/5");
       beforeCombineTree.LabelLib.add("2/4");
       beforeCombineTree.LabelLib.add("2/3");
       beforeCombineTree.LabelLib.add("2/3");
       beforeCombineTree.LabelLib.add("1/2");
       beforeCombineTree.LabelLib.add("1/2");
       beforeCombineTree.LabelLib.add("2/3");
       beforeCombineTree.LabelLib.add("3/4");
       beforeCombineTree.LabelLib.add("1/3");
       beforeCombineTree.LabelLib.add("1/2");
       beforeCombineTree.LabelLib.add("1/2");
       beforeCombineTree.LabelLib.add("2/2");

        beforeCombineTree.listPathOne = new ArrayList<String>();
        beforeCombineTree.listPathZero = new ArrayList<String>();

        beforeCombineTree.ComponentNumber = 1;//组件结点值
        beforeCombineTree.Mark = 2;//是什么结点，非汇聚为2，否则0，1
        beforeCombineTree.way = new ArrayList<String>();
        String beforeCombineString = "1";
        beforeCombineTree.way.add(beforeCombineString);

        beforeCombineTree.ROLL(beforeCombineTree);

/*        getProbability countProbability = new getProbability();
        List<String> listData = new ArrayList<String>();
        listData.add("10 30 20");
        listData.add("x=0.055 x=0.001 x=0.0015");
        listData.add("x=0.045 0.015 0.002");
        listData.add("0.006/2 x=0.002 0.018");
        listData.add("0.03 0.015 x=0.021");
        ArrayList<ArrayList<String>> List = countProbability.getData(listData);
        ArrayList<ArrayList<Double>> List2 = countProbability.changelist(List);*/


        System.out.println("阶段合并前失效的路径数：" + beforeCombineTree.listPathOne.size());
        System.out.println("阶段合并前不失效的路径数：" + beforeCombineTree.listPathZero.size());
//        System.out.println("阶段合并前系统失效概率为：" + countProbability.Tree_Result(beforeCombineTree.listPathOne, List2));
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
        System.out.println("===================================================================================");
        startTime = System.currentTimeMillis();//获取当前时间currentTimeMillis()  nanoTime()
        PMS_ANDNode afterCombineTree = beforeCombineTree.getAfterCombineTree(beforeCombineTree);
        afterCombineTree.ROLL(afterCombineTree);

        System.out.println("阶段合并后失效的路径数：" + afterCombineTree.listPathOne.size());
        System.out.println("阶段合并后不失效的路径数：" + afterCombineTree.listPathZero.size());
        System.out.println();


        for (int i : afterCombineTree.combinePhase) {
            System.out.print(i+" ");
        }
        System.out.println();
        System.out.println(afterCombineTree.phase);
//        System.out.println("阶段合并后系统失效概率为：" + countProbability.Tree_ResultWithMap(afterCombineTree.listPathOne, List2, afterCombineTree.combineMap));
        endTime = System.currentTimeMillis();
        System.out.println("采用阶段合并算法后程序运行时间：" + (endTime - startTime) + "ms");
    }
}
