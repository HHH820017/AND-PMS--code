package Reset.Experiment;

public class Test_01 {

    public static void getTime()
    {
        int a = 0;
        int b = 0;
        for (int i = 0; i < 100000; i++) {

            a += i;
        }

    }

    public static void main(String[] args) {


        double startTime =0;
        double endTime = 0;
        for (int i = 0; i < 10; i++) {

//            startTime = System.currentTimeMillis();
//            getTime();
              endTime = System.currentTimeMillis();
//            System.out.println(String.format("%.6f", endTime- startTime));
        }

        System.out.println(endTime);
    }


}
