<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BDD_Gen_net</title>
</head>

<body>
    <script>

        function BDDGen(k, n, phase, phaseArr) {
            let index = 0;
            let count = 0;
            let countX = 0;
            let BDD = [];


            for (let y = 0; y <= k - 1; y++) {
                for (let x = 0; x <= n - k; x++) {
                    if (x == n - k) {
                        RightChild = null
                    } else {
                        RightChild = index + 1
                        ++count
                    };

                    if (y == k - 1) {
                        LeftChild = null
                    } else {
                        LeftChild = index + n - k + 1
                    };

                    let node = {
                        BddPhase: phase,
                        BddId: phaseArr[countX++],
                        RightChild: RightChild,
                        LeftChild: LeftChild
                    };
                    BDD[index] = node;

                    index += 1;
                }
                countX -= count
                count = 0
            }

            // console.log(BDD)
            return BDD
        }


        let componentsArr = [[1, 2], [3, 5], [10, 11]];
        let kArr = [1, 1, 2];
        let nArr = [2, 2, 2];

        function BddPhasesGen(kArr, nArr, componentsArr) {

            let BddPhases = {};

            for (let i = 0; i < componentsArr.length; i++) {

                temBDD = BDDGen(kArr[i], nArr[i], i + 1, componentsArr[i])

                BddPhases[i + 1] = temBDD
            }
            return BddPhases
        }

        // 对 BddPhases 操作---将节点合并为一个嵌套结构
        function BddOne(BDD) {
            for (let i = 0; i < BDD.length; i++) {

                if (BDD[i].RightChild != null) {
                    BDD[i].RightChild = BDD[BDD[i].RightChild]
                }
                if (BDD[i].LeftChild != null) {
                    BDD[i].LeftChild = BDD[BDD[i].LeftChild]
                }
            }
            return BDD[0]
        }

        function BddMore() {

        }




































    </script>
</body>

</html>