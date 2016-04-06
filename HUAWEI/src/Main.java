import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
        public static void main(String args[]) {
            //测试,一共20个节点
            AMWGraph graph = new AMWGraph(20);

            //初始化
            graph.init(0);

            //读取csv文件
            try {
                BufferedReader reader = new BufferedReader(new FileReader("/home/tianxi/testcase/topo.csv"));//换成你的文件名
//                reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
                String line = null;
                while((line=reader.readLine())!=null){
                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    //插入节点
                    graph.insertVertex(item[1]);
                    graph.insertVertex(item[2]);

                    //插入边
                    graph.insertEdge(Integer.valueOf(item[1]),Integer.valueOf(item[2]),Integer.valueOf(item[3]));

                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            //深度
//            graph.depthFirstSearch(2,19);

            //广度
            System.out.println("广度优先搜索序列为：");
            graph.broadFirstSearch(2,19);
            System.out.println();

//            graph.allVertex();
            System.out.println();
//            graph.allEdge();


        }

    public static void justtest(){
        int n=8,e=9;//分别代表结点个数和边的数目
        String labels[]={"1","2","3","4","5","6","7","8"};//结点的标识
        AMWGraph graph=new AMWGraph(n);
        for(String label:labels) {
            graph.insertVertex(label);//插入结点
        }
        //插入九条边
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);
        graph.insertEdge(1, 0, 1);
        graph.insertEdge(2, 0, 1);
        graph.insertEdge(3, 1, 1);
        graph.insertEdge(4, 1, 1);
        graph.insertEdge(7, 3, 1);
        graph.insertEdge(7, 4, 1);
        graph.insertEdge(4, 2, 1);
        graph.insertEdge(5, 2, 1);
        graph.insertEdge(6, 5, 1);

        System.out.println("深度优先搜索序列为：");
//        graph.depthFirstSearch();
        System.out.println();
        System.out.println("广度优先搜索序列为：");
//        graph.broadFirstSearch();
        System.out.println();
    }
}
