package Reset;

import tree.PhaseRelyCom;
import tree.TreeNode;

import java.util.*;

public class PMS_ANDNode {

    public ArrayList<String> listPathZero;

    // 从根节点节点到‘0’节点路径

    public ArrayList<String> listPathOne;
    // 从根节点节点到‘1’节点路径

    public HashMap<Integer, ArrayList<Integer>> combineMap;
    //合并之后对应的阶段map， 用来记录新的阶段 对应的原来的阶段

    public int Mark;
    //判断它是非汇聚节点 非汇聚为2，否则0，1

    public PhaseRelyComponent phaseRelyComponent;
    //阶段对应组件的关系类

    public int phase;
    //阶段数的数目

    public int MaxComponents;
    //最大的组件数

    public ArrayList<String> LabelLib;
    //该节点的K/N标签库

    public int ComponentNumber;
    //该节点组件标记

    public ArrayList<String> way;
    //根节点到达该节点的路径集合

    public int canBeCombinePhaseNum;
    //可以被合并的阶段数的数目

    public int[] combinePhase;
    //可以被合并的阶段标记数组

    public PMS_ANDNode() {
    }


    public PMS_ANDNode(ArrayList<String> listPathZero, ArrayList<String> listPathOne, HashMap<Integer, ArrayList<Integer>> combineMap, int mark, PhaseRelyComponent phaseRelyComponent, int phase, int maxComponents, ArrayList<String> labelLib, int componentNumber, ArrayList<String> way, int canBeCombinePhaseNum, int[] combinePhase) {
        this.listPathZero = listPathZero;
        this.listPathOne = listPathOne;
        this.combineMap = combineMap;
        Mark = mark;
        this.phaseRelyComponent = phaseRelyComponent;
        this.phase = phase;
        MaxComponents = maxComponents;
        LabelLib = labelLib;
        ComponentNumber = componentNumber;
        this.way = way;
        this.canBeCombinePhaseNum = canBeCombinePhaseNum;
        this.combinePhase = combinePhase;
    }

    public ArrayList<String> getListPathZero() {
        return listPathZero;
    }

    public void setListPathZero(ArrayList<String> listPathZero) {
        this.listPathZero = listPathZero;
    }

    public ArrayList<String> getListPathOne() {
        return listPathOne;
    }

    public void setListPathOne(ArrayList<String> listPathOne) {
        this.listPathOne = listPathOne;
    }

    public HashMap<Integer, ArrayList<Integer>> getCombineMap() {
        return combineMap;
    }

    public void setCombineMap(HashMap<Integer, ArrayList<Integer>> combineMap) {
        this.combineMap = combineMap;
    }

    public int getMark() {
        return Mark;
    }

    public void setMark(int mark) {
        Mark = mark;
    }

    public PhaseRelyComponent getPhaseRelyComponent() {
        return phaseRelyComponent;
    }

    public void setPhaseRelyComponent(PhaseRelyComponent phaseRelyComponent) {
        this.phaseRelyComponent = phaseRelyComponent;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public int getMaxComponents() {
        return MaxComponents;
    }

    public void setMaxComponents(int maxComponents) {
        MaxComponents = maxComponents;
    }

    public ArrayList<String> getLabelLib() {
        return LabelLib;
    }

    public void setLabelLib(ArrayList<String> labelLib) {
        LabelLib = labelLib;
    }

    public int getComponentNumber() {
        return ComponentNumber;
    }

    public void setComponentNumber(int componentNumber) {
        ComponentNumber = componentNumber;
    }

    public ArrayList<String> getWay() {
        return way;
    }

    public void setWay(ArrayList<String> way) {
        this.way = way;
    }

    public int getCanBeCombinePhaseNum() {
        return canBeCombinePhaseNum;
    }

    public void setCanBeCombinePhaseNum(int canBeCombinePhaseNum) {
        this.canBeCombinePhaseNum = canBeCombinePhaseNum;
    }

    public int[] getCombinePhase() {
        return combinePhase;
    }

    public void setCombinePhase(int[] combinePhase) {
        this.combinePhase = combinePhase;
    }

    //核心方法 roll 用来生成最终的PMS-AND MDD, 才用二维数组存放，每一行代表同一个相同元件值的节点
    //利用M个Arraylist<PMS_ANDNode>生成整体MDD
    public void ROLL(PMS_ANDNode node) {
        List<ArrayList<PMS_ANDNode>> listAll = new ArrayList<ArrayList<PMS_ANDNode>>();

        for (int i = 0; i < node.MaxComponents; i++) {
            //生成M个arraylist
            ArrayList<PMS_ANDNode> arrayList = new ArrayList<PMS_ANDNode>();
            listAll.add(arrayList);
        }

        listAll.get(0).add(node);   //第一行放入根节点，即为整个PMS-AND MDD的根节点，并且该行只有一个节点。

        for (int i = 0; i < listAll.size() - 1; i++) //得到每一个arraylist（除了最后一行，因为最后的节点不会再生成子节点） 取出里面的节点，然后生成该节点的子节点 ()。
        {
            if (!listAll.get(i).isEmpty() && listAll.get(i + 1).isEmpty())       //前一个arraylist不为空，后一个为空
            {
                for (int j = 0; j < listAll.get(i).size(); j++)                    //将不为空listAll.get(i)的arraylist中每个元素拿出来 即为把每个节点取出来，然后生成它的子节点
                {
                    for (int k = 0; k <= node.phase; k++)             //每一个边上都生成子节点
                    {

                        PMS_ANDNode childnode = GenerateNode(k, listAll.get(i).get(j));         //生成的子节点

                        if (childnode.Mark == 2 && Same(listAll.get(i + 1), childnode).isEmpty())   //无相似结点
                        {
                            listAll.get(i + 1).add(childnode);   //放到listAll.get(i+1)中，即为生成一个不可以合并的节点，并放入集合中
                        } else if (childnode.Mark == 2 && !Same(listAll.get(i + 1), childnode).isEmpty())  //有相似
                        {
                            ArrayList<PMS_ANDNode> temp = Same(listAll.get(i + 1), childnode);   //里面都是相似结点

                            for (int num = 0; num < temp.size(); num++) {
                                childnode.way.addAll(temp.get(num).way);     //新节点中保存全部相似结点的路径
                            }

                            for (int num = 0; num < temp.size(); num++)    //合并相似结点 简化MDD
                            {
                                for (int num1 = 0; num1 < listAll.get(i + 1).size(); num1++) {
                                    if (temp.get(num).way.toString().equals(listAll.get(i + 1).get(num1).way.toString())) {
                                        listAll.get(i + 1).remove(num1);
                                    }
                                }
                            }
                            listAll.get(i + 1).add(childnode);
                        }
                    }
                }
            }
        }

        for (int i = 0; i < listAll.get(node.MaxComponents - 1).size(); i++)   //最后一个arraylist的节点连接 ”0“，”1“等终端节点
        {
            for (int k = 0; k <= node.phase; k++)                   //为每一个阶段都生成子节点
            {
                GenerateNode(k, listAll.get(node.MaxComponents - 1).get(i));
            }
        }
    }

    //该方法用于生成孩子节点。
    public PMS_ANDNode GenerateNode(int edge, PMS_ANDNode node) {
        if (edge == 0)    //如果连接父亲节点是0边
        {
            PMS_ANDNode Child = new PMS_ANDNode();    //生成孩子节点
            Child.way = new ArrayList<String>();
            Child.LabelLib = new ArrayList<String>();
            int Tnum = 0;             //T的数目
            for (int Edge = 0; Edge < node.phase; Edge++)    //遍历父亲节点的 label标签
            {
                if (delLab(node.LabelLib.get(Edge), node.ComponentNumber, node.phaseRelyComponent.Rely[Edge]).equals("T"))   //生成了第一个T 只要有一个成功，该父亲节点0边就连接“0”终端节点
                {
                    for (int phase = 0; phase < node.way.size(); phase++) {
                        Child.way.add(node.way.get(phase) + ",0/--0结点"); //添加到“0”终端节点的路径

                        listPathZero.add(Child.way.get(phase));         //从根节点到“0”终端节点的路径
                    }
                    Tnum++;
                    Child.Mark = 0;               //此孩子节点为“0”终端节点
                    return Child;
                }
            }
            if (Tnum == 0) {
                for (int phase = 0; phase < node.way.size(); phase++) {
                    Child.way.add(node.way.get(phase) + "," + edge + "/" + (node.ComponentNumber + 1)); //添加到该子节点的路径
                }

                //生成一个tmplist 用来存放父节点的label标签，接下来根据父节点label标签生成子节点的label标签
                ArrayList<String> temp = new ArrayList<>();
                for (int o = 0; o < node.LabelLib.size(); o++) {
                    temp.add(node.LabelLib.get(o));
                }
                Child.LabelLib = temp;
                for (int i = 0; i < node.phase; i++) {
                    //为子节点建立label标签
                    Child.LabelLib.set(i, delLab(node.LabelLib.get(i), node.ComponentNumber, node.phaseRelyComponent.Rely[i]));
                }
                Child.Mark = 2;   //此孩子节点为非汇聚节点
                Child.phaseRelyComponent = node.phaseRelyComponent;
                Child.phase = node.phase;
                Child.MaxComponents = node.MaxComponents;
                Child.ComponentNumber = node.ComponentNumber + 1;  //子节点的元件值为父节点的元件值+1
                return Child;
            }
        }
        if (edge != 0)   //如果连接父亲节点不是0边
        {
            PMS_ANDNode Child = new PMS_ANDNode(); //生成孩子节点
            Child.way = new ArrayList<String>();

            //生成一个tmplist 用来存放父节点的label标签，接下来根据父节点label标签生成子节点的label标签
            ArrayList<String> temp = new ArrayList<>();
            for (int o = 0; o < node.LabelLib.size(); o++) {
                temp.add(node.LabelLib.get(o));
            }
            Child.LabelLib = temp;

            int Fnum = 0;//F的数目
            int Tnum = 0;//T的数目
            for (int Edge = 0; Edge < edge - 1; Edge++)    //遍历父亲节点的 label标签
            {
                if (delLab(node.LabelLib.get(Edge), node.ComponentNumber, node.phaseRelyComponent.Rely[Edge]).equals("F")) {
                    Fnum++;
                }
                if (delLab(node.LabelLib.get(Edge), node.ComponentNumber, node.phaseRelyComponent.Rely[Edge]).equals("T"))   //生成了第一个T 只要有一个成功，该父亲节点0边就连接“0”终端节点
                {
                    for (int phase = 0; phase < node.way.size(); phase++) {
                        Child.way.add(node.way.get(phase) + "," + edge + "/--0结点");   //添加到“0”终端节点的路径
                        listPathZero.add(Child.way.get(phase));      //从根节点到“0”终端节点的路径
                    }
                    Tnum++;
                    Child.Mark = 0;   //此孩子节点为“0”终端节点
                    return Child;
                } else if (!delLab(node.LabelLib.get(Edge), node.ComponentNumber, node.phaseRelyComponent.Rely[Edge]).equals("T")) {
                    //为子节点建立label标签
                    Child.LabelLib.set(Edge, delLab(node.LabelLib.get(Edge), node.ComponentNumber, node.phaseRelyComponent.Rely[Edge]));
                }
            }

            for (int Edge = edge - 1; Edge < node.phase; Edge++)    //遍历父亲节点的 label标签
            {
                if (delLab2(node.LabelLib.get(Edge), node.ComponentNumber, node.phaseRelyComponent.Rely[Edge]).equals("F"))  //统计生成的F个数
                {
                    Fnum++;
                }
                //为子节点建立label标签
                Child.LabelLib.set(Edge, delLab2(node.LabelLib.get(Edge), node.ComponentNumber, node.phaseRelyComponent.Rely[Edge]));
            }
            if (Fnum == node.phase)  //F个数等于阶段数目
            {
                for (int phase = 0; phase < node.way.size(); phase++) {
                    Child.way.add(node.way.get(phase) + "," + edge + "/--1结点"); //添加到“1”终端节点的路径
                    listPathOne.add(Child.way.get(phase));                       //从根节点到“1”终端节点的路径
                }
                Child.Mark = 1;   //此孩子节点为“1”终端节点
                return Child;
            } else if (Fnum != node.phase && Tnum == 0) {
                for (int phase = 0; phase < node.way.size(); phase++) {
                    Child.way.add(node.way.get(phase) + "," + edge + "/" + (node.ComponentNumber + 1)); //添加到该子节点的路径
                }
                Child.Mark = 2;  //该子节点为非汇聚节点
                Child.phaseRelyComponent = node.phaseRelyComponent;
                Child.phase = node.phase;
                Child.MaxComponents = node.MaxComponents;
                Child.ComponentNumber = node.ComponentNumber + 1; //子节点的元件值为父节点的元件值+1

                return Child;
            }
            return Child;
        }
        return node;
    }

    //该方法用于判断两个节点是否可以合并，并且返回的集合为可以合并的相似节点
    public ArrayList<PMS_ANDNode> Same(ArrayList<PMS_ANDNode> arrayList, PMS_ANDNode sinkNode) {
        //是否在arraylist中，是就挑出来放集合里面
        // TODO Auto-generated method stub
        //如果为空，直接返回
        if (arrayList.isEmpty()) {
            return arrayList;
        }
        if (!arrayList.isEmpty()) {
            ArrayList<PMS_ANDNode> new1 = new ArrayList<PMS_ANDNode>(); //新建一个arraylist 用来存储 互相不能合并的节点
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).LabelLib.toString().equals(sinkNode.LabelLib.toString())) {
                    new1.add(arrayList.get(i));   //如果标签纸相同，意味着两个节点一模一样，直接加入arraylist
                }
                //查找，比较有无可以合并的相似节点
                if (!arrayList.get(i).LabelLib.toString().equals(sinkNode.LabelLib.toString()) && sameNodeLog(sinkNode, arrayList.get(i)) != -1)  //有共同为1 的标签
                {
                    int same = sameNodeLog(sinkNode, arrayList.get(i));  //最后一个v1=v2=1，且属于集合的点所在的阶段i
                    int part1 = 0;  //前一部分
                    for (int j = 0; j <= same; j++) {
                        if (arrayList.get(i).LabelLib.get(j).equals(sinkNode.LabelLib.get(j))) {//前面相等
                            part1++;
                        }
                    }
                    int part2 = 0;  //后一部分
                    for (int j = same + 1; j < sinkNode.phase; j++) {
                        //在该阶段都为F，且sinknode属于该阶段
                        if (sameNums(arrayList.get(i).LabelLib.get(j), sinkNode.LabelLib.get(j)) && sinkNode.Contain(sinkNode.ComponentNumber, sinkNode.phaseRelyComponent.Rely[j])) {
                            part2++;
                        }
                    }
//					System.out.println(part2);
                    if (part1 + part2 == sinkNode.phase)  //前一部分+后一部分等于sinknode的阶段数，则为可以判断为相似，可以合并的阶段。
                    {
                        new1.add(arrayList.get(i));  //把节点加入到集合中
                    }
                }

            }
            return new1;
        }
        return arrayList;
    }

    //此方法用于判断两个节点的标签的F
    public boolean sameNums(String A, String B) {
        if (A.equals(B)) {
            return true;
        }
        if (A.equals("F") && (B.split("/")[0].equals(B.split("/")[1]))) {
            return true;
        }
        if (B.equals("F") && (A.split("/")[0].equals(A.split("/")[1]))) {
            return true;
        }

        return false;
    }

    //此方法用于 找出最后一个v1=v2=1，且属于集合的点所在的阶段i,-1就是不存在
    public int sameNodeLog(PMS_ANDNode N1, PMS_ANDNode N2) {
        ArrayList<String> A1 = N1.LabelLib;
        ArrayList<String> A2 = N2.LabelLib;
        for (int i = A1.size() - 1; i >= 0; i--) {
            if (A1.get(i).split("/")[0].equals("1") && A2.get(i).split("/")[0].equals("1") && Contain(N1.ComponentNumber, N1.phaseRelyComponent.Rely[i])) {
                return i;
            }
        }
        return -1;
    }

    //该方法用于生成新的 label， 为label标签生成规则 （第一种）
    public String delLab(String label, int componentNumber, int[] order) {
        //k/n都-1的情况,标签，哪个阶段，结点是哪个，阶段的可用元件X有哪些
        if (label.equals("F")) //等于F，不变
        {
            return "F";
        }
        if (!Contain(componentNumber, order)) //不在数组中
        {
            return label;
        }
        if (Contain(componentNumber, order)) //在数组中
        {
            String k1 = label.split("/")[0];
            String n1 = label.split("/")[1];
            int k = Integer.parseInt(k1);
            int n = Integer.parseInt(n1);
            if ((k - 1) == 0) {
                return "T";  //意味着不会发生失效
            } else {
                //返回新生成的标签值
                String newlabel = String.valueOf(k - 1) + "/" + String.valueOf(n - 1);
                return newlabel;
            }
        }
        return label;
    }

    //该方法用于生成新的 label， 为label标签生成规则 （第二种）
    public String delLab2(String label, int componentNumber, int[] order) {
        //n-1的情况,标签，哪个阶段，结点是哪个，阶段的可用元件X有哪些
        if (label.equals("F"))    //等于F，不变
        {
            return "F";
        }
        if (!Contain(componentNumber, order) && !label.equals("F"))  //不在数组中
        {
            return label;
        }
        if (Contain(componentNumber, order) && !label.equals("F"))  //在数组中
        {
            String k1 = label.split("/")[0];
            String n1 = label.split("/")[1];
            int k = Integer.parseInt(k1);
            int n = Integer.parseInt(n1);
            n = n - 1;
            if (k > n) {
                return "F";
            } else {
                //返回新生成的标签值
                String newlabel = Integer.toString(k) + "/" + Integer.toString(n);
                return newlabel;
            }
        }
        return label;
    }

    //该方法用于判断该元件是否对该阶段有影响，即该阶段包含该元件
    public boolean Contain(int componentNumber, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (componentNumber == arr[i]) {
                return true;
            }
        }
        return false;
    }

    //此方法用于找到所有涉及到的元件，元件值最大的元件
    public int MaxNum(int[][] arr) {
        int max = arr[0][arr[0].length - 1];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i][arr[i].length - 1] > max) {
                max = arr[i][arr[i].length - 1];
            }
        }
        return max;
    }

    //================阶段合并算法==========================================================================
    //此方法可以合并的阶段标记为1，并返回一个数组。数组中标记为1的阶段即为可以表示
    public int[] combinePhase(int[][] Rely, int phasnum, ArrayList<String> labelLib) {
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
    public int canBeCombine(int[] arrFirst, int[] arrSecond, String firstKN, String secondKN) {
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
    public boolean HasContainRelation(int[] first, int[] second) {
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

    //此方法用于生成一个MAP， 保存合并后的阶段 对应的 合并前的阶段关系
    public HashMap<Integer, ArrayList<Integer>> getCombineMap(int[] arr) {
        int i = 0;
        int j = 0;
        int n = arr.length;
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        int idx = 1;
        while (i < n && j < n) {
            if (arr[j] == 0) {
                ArrayList<Integer> tmpList = new ArrayList<>();

                for (int k = i; k <= j; k++) {
                    tmpList.add(k);
                }
                map.put(idx++, tmpList);
                j++;
                i = j;
            } else {
                j++;
            }
        }
        ArrayList<Integer> ZeroList = new ArrayList<>();
        map.put(0, ZeroList);
        if (i < n && j == n) {
            ArrayList<Integer> tmplist = map.get(0);

            for (int k = i; k < n; k++) {
                tmplist.add(k);
            }
            map.put(0, tmplist);
        }
        //这个可以输出 阶段合并后 和 阶段合并前的 一个阶段对应关系
/*        for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {

            System.out.println("新的阶段："+entry.getKey());
            int size = entry.getValue().size();
            for (int s = 0; s < size; s++) {
                System.out.print(entry.getValue().get(s)+1+" ");
            }
            System.out.println();
        }*/
        return map;
    }

    //此方法用来生成采用阶段合并方法 合并后的生成的 PMS-MDD 的根节点
    public PMS_ANDNode getAfterCombineTree(PMS_ANDNode beforeCombineTree) {
        PMS_ANDNode AfterCombineTree = new PMS_ANDNode(); //生成一个采用阶段算法合并后的PMS-MDD的根节点
        //判断哪些阶段和可以与前面某个阶段合并的阶段，并把他们标记.返回一个标记数组
        int[] combinePhase = combinePhase(beforeCombineTree.phaseRelyComponent.Rely, beforeCombineTree.phaseRelyComponent.Rely.length, beforeCombineTree.LabelLib);
        AfterCombineTree.combinePhase = combinePhase;
        AfterCombineTree.canBeCombinePhaseNum = getCanBeCombinePhaseNum(combinePhase);
        HashMap<Integer, ArrayList<Integer>> combineMap = getCombineMap(combinePhase); //根据标记数组，得到合并之后的阶段对应关系；
        AfterCombineTree.phase = 0;
        //填入它的合并后的阶段数目
        for (int i = 0; i < combinePhase.length; i++) {
            if (combinePhase[i] == 0) {
                AfterCombineTree.phase++;
            }
        }
        int[][] Rely = new int[AfterCombineTree.phase][];  //Rely数组用来存放每个阶段所涉及到的元件
        AfterCombineTree.LabelLib = new ArrayList<>();
        int idx = 0;
        for (int i = 0; i < combinePhase.length; i++) {
            if (combinePhase[i] == 0) {
                Rely[idx++] = beforeCombineTree.phaseRelyComponent.Rely[i];  //数组用来存放合并后每个阶段所涉及到的元件
                AfterCombineTree.LabelLib.add(beforeCombineTree.LabelLib.get(i)); //将合并后的阶段 k/n 标签填入
            }
        }
        AfterCombineTree.MaxComponents = AfterCombineTree.MaxNum(Rely);  //找出元件值最大的元件
        PhaseRelyComponent phaseRelyComponent = new PhaseRelyComponent(); //创建新的对应组件的关系类
        phaseRelyComponent.Rely = Rely;                                   //填入合并后的元件依赖数组
        AfterCombineTree.phaseRelyComponent = phaseRelyComponent;
        AfterCombineTree.ComponentNumber = 1;                                //组件结点值
        AfterCombineTree.way = new ArrayList<String>();
        String AfterCombineString = "1";
        AfterCombineTree.way.add(AfterCombineString);               //根节点到该节点的路径集合
        AfterCombineTree.listPathOne = new ArrayList<String>();           //存放到终端节点‘1’的路径集合
        AfterCombineTree.listPathZero = new ArrayList<String>();          //存放到终端节点‘0’的路径集合
        AfterCombineTree.combineMap = combineMap;                   //合并之后的阶段对应关系；

        return AfterCombineTree;
    }

    public PMS_ANDNode getAfterCombineTreeTest(PMS_ANDNode AfterCombineTree, PMS_ANDNode beforeCombineTree) {

        AfterCombineTree.canBeCombinePhaseNum = getCanBeCombinePhaseNum(AfterCombineTree.combinePhase);
        HashMap<Integer, ArrayList<Integer>> combineMap = getCombineMap(AfterCombineTree.combinePhase); //根据标记数组，得到合并之后的阶段对应关系；
        AfterCombineTree.phase = 0;
        //填入它的合并后的阶段数目
        for (int i = 0; i < AfterCombineTree.combinePhase.length; i++) {
            if (AfterCombineTree.combinePhase[i] == 0) {
                AfterCombineTree.phase++;
            }
        }
        int[][] Rely = new int[AfterCombineTree.phase][];  //Rely数组用来存放每个阶段所涉及到的元件
        AfterCombineTree.LabelLib = new ArrayList<>();
        int idx = 0;
        for (int i = 0; i < AfterCombineTree.combinePhase.length; i++) {
            if (AfterCombineTree.combinePhase[i] == 0) {
                Rely[idx++] = beforeCombineTree.phaseRelyComponent.Rely[i];  //数组用来存放合并后每个阶段所涉及到的元件
                AfterCombineTree.LabelLib.add(beforeCombineTree.LabelLib.get(i)); //将合并后的阶段 k/n 标签填入
            }
        }
        AfterCombineTree.MaxComponents = AfterCombineTree.MaxNum(Rely);  //找出元件值最大的元件
        PhaseRelyComponent phaseRelyComponent = new PhaseRelyComponent(); //创建新的对应组件的关系类
        phaseRelyComponent.Rely = Rely;                                   //填入合并后的元件依赖数组
        AfterCombineTree.phaseRelyComponent = phaseRelyComponent;
        AfterCombineTree.ComponentNumber = 1;                                //组件结点值
        AfterCombineTree.way = new ArrayList<String>();
        String AfterCombineString = "1";
        AfterCombineTree.way.add(AfterCombineString);               //根节点到该节点的路径集合
        AfterCombineTree.listPathOne = new ArrayList<String>();           //存放到终端节点‘1’的路径集合
        AfterCombineTree.listPathZero = new ArrayList<String>();          //存放到终端节点‘0’的路径集合
        AfterCombineTree.combineMap = combineMap;                   //合并之后的阶段对应关系；

        return AfterCombineTree;
    }


    //此算法用来生成一个测试的beforeCombineTree，无非就是数据复制，进行不断ROLL
    public PMS_ANDNode getBeforeCombineTreeTest(PMS_ANDNode beforeCombineTree) {

        PMS_ANDNode BeforeCombineTree = new PMS_ANDNode();
        PhaseRelyComponent phaseRelyComponent = new PhaseRelyComponent();

        phaseRelyComponent.Rely = beforeCombineTree.phaseRelyComponent.Rely;

        BeforeCombineTree.phaseRelyComponent = phaseRelyComponent;

        BeforeCombineTree.phase = BeforeCombineTree.phaseRelyComponent.Rely.length;
        BeforeCombineTree.MaxComponents = BeforeCombineTree.MaxNum(BeforeCombineTree.phaseRelyComponent.Rely);

        BeforeCombineTree.LabelLib = new ArrayList<>();
        BeforeCombineTree.LabelLib = beforeCombineTree.LabelLib;
        BeforeCombineTree.listPathOne = new ArrayList<String>();
        BeforeCombineTree.listPathZero = new ArrayList<String>();

        BeforeCombineTree.ComponentNumber = 1;//组件结点值
        BeforeCombineTree.Mark = 2;//是什么结点，非汇聚为2，否则0，1
        BeforeCombineTree.way = new ArrayList<String>();
        String beforeCombineString = "1";
        BeforeCombineTree.way.add(beforeCombineString);
        BeforeCombineTree.canBeCombinePhaseNum = 0;
        return BeforeCombineTree;
    }

    //此方法可以获得采用阶段合并算法后，可以被合并的阶段数目
    public int getCanBeCombinePhaseNum(int[] combinePhase) {
        int cnt = 0;
        for (int i = 0; i < combinePhase.length; i++) {
            if (combinePhase[i] != 0) {
                cnt++;
            }
        }
        return cnt;
    }
}
