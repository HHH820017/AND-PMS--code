package Reset;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class getProbability {
    //该阶段的数据和时间
    public static String dealDat(String str, String Time) {
        String[] A = str.split("=");                            //x=0.23的情况
        String[] B = str.split("/");                            //有x和w的情况
        if (A.length == 1 && B.length == 1) {            //没切开，单个q的情况
            BigDecimal a = new BigDecimal(str);
            String result = a.toString();
            return result;
        } else if (A.length == 2) {                            //有x那木塔的情况
            BigDecimal a = new BigDecimal(A[1]);      //得到纳姆塔x
            BigDecimal time = new BigDecimal(Time);
            double q = cutnum(1.0 - cutnum(Math.exp(-(a.multiply(time).doubleValue()))));
            String result = String.valueOf(q);
            return result;
        } else if (B.length == 2) {
            BigDecimal x = new BigDecimal(B[0]);
            BigDecimal w = new BigDecimal(B[1]);
            BigDecimal time = new BigDecimal(Time);
            double ka = cutnum(Math.pow(cutnum(x.multiply(time).doubleValue()), cutnum(w.doubleValue())));
            double q = cutnum(1.0 - cutnum(Math.exp(-ka)));                   //将ka四舍五入
            String result = String.valueOf(q);
            return result;
        }
        return null;
    }

    public static double cutnum(double num) {
        BigDecimal b = new BigDecimal(num);

        double f1 = b.setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue();

        return f1;
    }

    public static double cutnumfinalresult(double num) {
        BigDecimal b = new BigDecimal(num);

        double f1 = b.setScale(30, BigDecimal.ROUND_HALF_UP).doubleValue();

        return f1;
    }

    public static double oneway(String str, ArrayList<ArrayList<Double>> list) //处理单独的一条路线，将其求出来，str的格式为1,2/2,0/3,3/4
    {
        String[] strs = str.split("/");

        BigDecimal result = new BigDecimal(1.0);

        for (int i = 0; i < strs.length - 1; i++) {
            String[] pots = strs[i].split(",");      //得到2,3这种形式的结果
            int nodeNum = Integer.valueOf(pots[0]);
            int Edg = Integer.valueOf(pots[1]);

            int here = list.get(nodeNum - 1).size() - 1;//0边的位置
            if (Edg == 0) {
                double fail = list.get(nodeNum - 1).get(here);
                BigDecimal Fail = new BigDecimal(Double.toString(fail));
                result = result.multiply(Fail);
            } else if (Edg != 0) {
                double fail = list.get(nodeNum - 1).get(Edg - 1);
                BigDecimal Fail = new BigDecimal(Double.toString(fail));
                result = result.multiply(Fail);
            }
        }
        return result.doubleValue();
    }

    public static double onewayWithMap(String str, ArrayList<ArrayList<Double>> list, HashMap<Integer, ArrayList<Integer>> combineMap) //处理单独的一条路线，将其求出来，str的格式为1,2/2,0/3,3/4
    {
        String[] strs = str.split("/");

        BigDecimal result = new BigDecimal(1.0);

        for (int i = 0; i < strs.length - 1; i++) {
            String[] pots = strs[i].split(",");      //得到2,3这种形式的结果
            int nodeNum = Integer.valueOf(pots[0]);
            int Edg = Integer.valueOf(pots[1]);


            int here = list.get(nodeNum - 1).size() - 1;//0边的位置
            if (Edg == 0) {
                ArrayList<Integer> zeroList = combineMap.get(0);
                double fail;
                BigDecimal Fail;
                BigDecimal resultFail = new BigDecimal(0);
                for (int k = 0; k < zeroList.size(); k++) {
                    fail = list.get(nodeNum - 1).get(zeroList.get(k));
                    Fail = new BigDecimal(Double.toString(fail));
                    resultFail = resultFail.add(Fail);
                }
                fail = list.get(nodeNum - 1).get(here);
                Fail = new BigDecimal(Double.toString(fail));
                resultFail = resultFail.add(Fail);
                result = result.multiply(resultFail);
            } else if (Edg != 0) {
                ArrayList<Integer> edgeList = combineMap.get(Edg);
                double fail;
                BigDecimal Fail;
                BigDecimal resultFail = new BigDecimal(0);
                for (int k = 0; k < edgeList.size(); k++) {
                    fail = list.get(nodeNum - 1).get(edgeList.get(k));
                    Fail = new BigDecimal(Double.toString(fail));
                    resultFail = resultFail.add(Fail);
                }
                result = result.multiply(resultFail);
            }
        }
        return result.doubleValue();
    }

    public ArrayList<ArrayList<String>> getData(List<String> list) //把里面的每个元素都换成String,方便大数计算，得到都是q的类似二维数组
    {
        ArrayList<ArrayList<String>> DATA = new ArrayList<ArrayList<String>>();
        String[] Time = list.get(0).split(" ");//构造一个关于时间的字符串数组

        for (int i = 1; i < list.size(); i++) {
            ArrayList<String> doubledata = new ArrayList<String>();   //用来存放每一行的数据
            String[] yihang = list.get(i).split(" ");
            for (int j = 0; j < yihang.length; j++) {                                                     //把每一行的单个数据都提取出来，进行处理
                String data = dealDat(yihang[j], Time[j]);                                 //这就是这个q
                doubledata.add(data);
            }
            DATA.add(doubledata);
        }

        return DATA;
    }

    public ArrayList<ArrayList<Double>> changelist(ArrayList<ArrayList<String>> Num1) {//这里没有时间一行了
        ArrayList<ArrayList<Double>> Num2 = new ArrayList<ArrayList<Double>>();           //变化后的list
        for (int i = 0; i < Num1.size(); i++) {
            ArrayList<String> hang = Num1.get(i);                                                                        //每一行的数据
            ArrayList<Double> newhang = new ArrayList<Double>();                                //新的行

            BigDecimal sum = new BigDecimal("0.0");                                                   //每一行的第一个数据
            BigDecimal Summer = new BigDecimal("0.0");                                           //给多出来的一列数据，也就是edge-0用的
            BigDecimal One = new BigDecimal("1.0");
            for (int j = 0; j <= Num1.get(i).size(); j++) {
                if (j == 0) {
                    String lin = hang.get(0);
                    BigDecimal Lin = new BigDecimal(lin);
                    sum = sum.add(Lin);
                    Summer = Summer.add(sum);                                                                            //edge-0用的
                    newhang.add(sum.doubleValue());                                                                  //将double放入
                } else if (0 < j && j < hang.size()) {
                    String lin = hang.get(j);//拿到的概率
                    BigDecimal Lin = new BigDecimal(lin);

                    double summ11 = 0.0;
                    for (int k = 0; k < j; k++) {//求Lin的和
                        summ11 += newhang.get(k);
                    }

                    BigDecimal S = new BigDecimal(String.valueOf(summ11));
                    BigDecimal O = One.subtract(S);  //1-（）
                    newhang.add(cutnum(O.multiply(Lin).doubleValue()));

                    Summer = Summer.add(O.multiply(Lin));
                } else if (j == hang.size()) {
                    double a = cutnum(One.subtract(Summer).doubleValue());
                    newhang.add(a);                                                   //多出来一行，这一行放edge-0
                }

            }
            Num2.add(newhang);
        }
        return Num2;
    }

    public double Tree_Result(ArrayList<String> wayList, ArrayList<ArrayList<Double>> list) {
        //每一条边拿出来，求出概率相加
        BigDecimal result = new BigDecimal(0.0);
        for (int i = 0; i < wayList.size(); i++) {
            String str = wayList.get(i);
            double q = oneway(str, list);
            BigDecimal F = new BigDecimal(Double.toString(q));
            result = F.add(result);
        }
//		System.out.println(result);
        double RESULT = cutnumfinalresult(result.doubleValue());            //最终结果
        return RESULT;
    }

    public double Tree_ResultWithMap(ArrayList<String> wayList, ArrayList<ArrayList<Double>> list, HashMap<Integer, ArrayList<Integer>> combineMap) {
        //每一条边拿出来，求出概率相加
        BigDecimal result = new BigDecimal(0.0);
        for (int i = 0; i < wayList.size(); i++) {
            String str = wayList.get(i);
            double q = onewayWithMap(str, list, combineMap);
            BigDecimal F = new BigDecimal(Double.toString(q));
            result = F.add(result);
        }
        double RESULT = cutnumfinalresult(result.doubleValue());            //最终结果
        return RESULT;
    }


}