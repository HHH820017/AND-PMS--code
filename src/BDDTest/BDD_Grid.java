package BDDTest;

public class BDD_Grid {

    int BddId;

    int BddPhase;

    BDD_Grid RightChild;

    BDD_Grid LeftChild;

    int LeftIndex;

    int RightIndex;


    @Override
    public String toString() {
        return "BDD_Grid{" +
                "BddId=" + BddId +
                ", BddPhase=" + BddPhase +
                ", RightChild=" + RightChild +
                ", LeftChild=" + LeftChild +
                ", LeftIndex=" + LeftIndex +
                ", RightIndex=" + RightIndex +
                '}';
    }

    public BDD_Grid() {
    }

    public BDD_Grid(int bddId, int bddPhase, BDD_Grid rightChild, BDD_Grid leftChild, int leftIndex, int rightIndex) {
        BddId = bddId;
        BddPhase = bddPhase;
        RightChild = rightChild;
        LeftChild = leftChild;
        LeftIndex = leftIndex;
        RightIndex = rightIndex;
    }

    public int getBddId() {
        return BddId;
    }

    public void setBddId(int bddId) {
        BddId = bddId;
    }

    public int getBddPhase() {
        return BddPhase;
    }

    public void setBddPhase(int bddPhase) {
        BddPhase = bddPhase;
    }

    public BDD_Grid getRightChild() {
        return RightChild;
    }

    public void setRightChild(BDD_Grid rightChild) {
        RightChild = rightChild;
    }

    public BDD_Grid getLeftChild() {
        return LeftChild;
    }

    public void setLeftChild(BDD_Grid leftChild) {
        LeftChild = leftChild;
    }

    public int getLeftIndex() {
        return LeftIndex;
    }

    public void setLeftIndex(int leftIndex) {
        LeftIndex = leftIndex;
    }

    public int getRightIndex() {
        return RightIndex;
    }

    public void setRightIndex(int rightIndex) {
        RightIndex = rightIndex;
    }
}
