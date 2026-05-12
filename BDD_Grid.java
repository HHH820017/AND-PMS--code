package BDDTest;

public class BDDTree {

    int BddId;

    int BddPhase;

    BDDTree RightChild;

    BDDTree LeftChild;

    public BDDTree() {
    }

    public BDDTree(int bddId, int bddPhase, BDDTree rightChild, BDDTree leftChild) {
        BddId = bddId;
        BddPhase = bddPhase;
        RightChild = rightChild;
        LeftChild = leftChild;
    }

    @Override
    public String toString() {
        return "BDDTree{" +
                "BddId=" + BddId +
                ", BddPhase=" + BddPhase +
                ", RightChild=" + RightChild +
                ", LeftChild=" + LeftChild +
                '}';
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

    public BDDTree getRightChild() {
        return RightChild;
    }

    public void setRightChild(BDDTree rightChild) {
        RightChild = rightChild;
    }

    public BDDTree getLeftChild() {
        return LeftChild;
    }

    public void setLeftChild(BDDTree leftChild) {
        LeftChild = leftChild;
    }
}
