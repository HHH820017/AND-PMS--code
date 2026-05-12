package Reset;

public class PhaseRelyComponent {

    /**
     *   这个二维数组里面放置phase对组件的依赖关系
     *   (0,0)(0,1)(0,2)...(0,n)//第一阶段的n+1个组件依赖
     *   .
     *   .
     *   .
     *   (m,0)(m,1)                  //第m+1个阶段的2个组件依赖
     *
     */

    public int[][]  Rely;     //Rely数组用来存放每个阶段所涉及到的元件


    public PhaseRelyComponent() {
    }
    public PhaseRelyComponent(int[][] rely) {
        Rely = rely;
    }

    public int[][] getRely() {
        return Rely;
    }

    public void setRely(int[][] rely) {
        Rely = rely;
    }


}
