package Reset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test_02 {

    //================阶段合并算法==========================================================================
    //此方法可以合并的阶段标记为1，并返回一个数组。数组中标记为1的阶段即为可以表示

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

        };

            ArrayList<String> LabelLib = new ArrayList<>();
            LabelLib = new ArrayList<>();

            LabelLib.add("3/4");
            LabelLib.add("2/3");
            LabelLib.add("2/2");
            LabelLib.add("1/2");
            LabelLib.add("1/2");

            LabelLib.add("3/3");
            LabelLib.add("2/2");
            LabelLib.add("2/2");
            LabelLib.add("4/5");
            LabelLib.add("2/3");

            int times= 1000;
            long startTime = 0;
            long endTime = 0;
            double afterCombineTreeTime =0;

            int[] combinePhase;
            for (int i = 0; i < times; i++) {

                startTime = System.currentTimeMillis();//获取当前时间currentTimeMillis()  nanoTime()
                combinePhase = combinePhase(Rely, Rely.length, LabelLib);
                endTime = System.currentTimeMillis();
                afterCombineTreeTime += endTime - startTime;
            }

            afterCombineTreeTime  = (double)(afterCombineTreeTime  /times);

            System.out.println(afterCombineTreeTime);

    }

    public  static  int[] combinePhase(int[][] Rely, int phasnum, ArrayList<String> labelLib) {
//        数组展示  每个阶段的元件集合

        int[] combine = new int[phasnum];
        for (int i = 0; i < phasnum; i++) {          //遍历每个阶段的元件    第一层循环是选定一个阶段，判断当前阶段之后的所有阶段能否与当前阶段合并。从 阶段1 = {1, 2, 3, 4}开始。
            for (int j = i + 1; j < phasnum; j++) {
                //遍历阶段1之后所有的阶段 从 阶段2 = {2, 3, 5}开始，之后阶段3，阶段4，判断阶段1之后的所有阶段是否满足三个合并条件之一，能够与阶段1合并。

                int res = canBeCombine(Rely[i], Rely[j], labelLib.get(i), labelLib.get(j));//此方法用于判断两个阶段符合哪种合并条件。label数组记录着当前阶段的k/n值
                if (res == 1)       //符合Case1情况
                {
                    combine[j] = 1;  //标记当前这个阶段可以被合并

                } else if (res == 2)    //符合Case2情况
                {
                    combine[j] = 2;

                } else if (res == 3)        //符合Case3情况
                {
                    combine[j] = 3;

                }

            }
        }
        return combine;  //返回的数组中，凡是标记为数字1，2，3的阶段，都是可以被合并的阶段。
    }

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
