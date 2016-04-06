/**
 * Created by tianxi on 16-4-6.
 */
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * @description 邻接矩阵模型类
 * @author beanlam
 * @time 2015.4.17
 */
public class AMWGraph {
    private ArrayList vertexList;//存储点的链表
    private int[][] edges;//邻接矩阵，用来存储边
    private int numOfEdges;//边的数目
    private int num;//节点的个数

    public AMWGraph(int n) {
        //初始化矩阵，一维数组，和边的数目
        edges=new int[n][n];
        vertexList=new ArrayList(n);
        numOfEdges=0;
        num = n;
    }

    //初始化
    public void init(int n){
        for(int i=0;i<num;i++)
            for(int j=0;j<num;j++)
                edges[i][j] = -1;
    }

    //得到结点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //得到边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //返回结点i的数据
    public Object getValueByIndex(int i) {
        return vertexList.get(i);
    }

    //返回v1,v2的权值
    public int getWeight(int v1,int v2) {
        return edges[v1][v2];
    }

    //插入结点
    public void insertVertex(Object vertex) {
        //自定义不包含重复元素
        if(!vertexList.contains(vertex))
            vertexList.add(vertexList.size(),vertex);
    }

    //遍历节点
    public void allVertex(){
        for(int i=0;i<vertexList.size();i++){
            System.out.println(vertexList.get(i).toString());
        }
    }

    //遍历边
    public void allEdge(){
        for(int i=0;i<num;i++){
            for(int j=0;j<num;j++)
                System.out.print(edges[i][j]+"  ");
            System.out.println();
        }

    }
    //插入边
    public void insertEdge(int v1,int v2,int weight) {
        edges[v1][v2]=weight;
        numOfEdges++;
    }

    //删除结点
    public void deleteEdge(int v1,int v2) {
        edges[v1][v2]=0;
        numOfEdges--;
    }

    //得到第一个邻接结点的下标
    public int getFirstNeighbor(int index) {
        //改进算法,根据权值选取最小的边
        int tempvalue = 0;
        int result =-1;
        for(int j=0;j<vertexList.size();j++) {
            if (edges[index][j]>0) {
                return j;
            }
        }
        return -1;
    }

    //根据前一个邻接结点的下标来取得下一个邻接结点
    public int getNextNeighbor(int v1,int v2) {
        for (int j=v2+1;j<vertexList.size();j++) {
            if (edges[v1][j]>0) {
                return j;
            }
        }
        return -1;
    }


    //定义终结点
    private int end;
    //私有函数，深度优先遍历
    private void depthFirstSearch(boolean[] isVisited,int  i) {
        //首先访问该结点，在控制台打印出来
//        System.out.print(getValueByIndex(i)+"  ");
        System.out.print(i+"  ");
        //置该结点为已访问
        isVisited[i]=true;

        //如果遇到终结点,停止返回
        if(i == end){
            System.out.println("结束,返回.");
//            System.exit(0);
            return;
        }
        int w=getFirstNeighbor(i);//
        while (w!=-1) {
            if (!isVisited[w]) {
                depthFirstSearch(isVisited,w);
            }
            w=getNextNeighbor(i, w);
        }
    }

    //对外公开函数，深度优先遍历，与其同名私有函数属于方法重载
    public void depthFirstSearch(int start,int end) {
        this.end = end;
        boolean[] isVisited=new boolean[getNumOfVertex()];
        //记录结点是否已经被访问的数组
        for (int i=0;i<getNumOfVertex();i++) {
            isVisited[i]=false;//把所有节点设置为未访问
        }
        for(int i=start;i<getNumOfVertex();i++) {
            //因为对于非连通图来说，并不是通过一个结点就一定可以遍历所有结点的。
            if (!isVisited[i]) {

                depthFirstSearch(isVisited,i);
            }
        }
    }

    //私有函数，广度优先遍历
    private void broadFirstSearch(boolean[] isVisited,int i) {
        int u,w;
        LinkedList queue=new LinkedList();

        //访问结点i
        System.out.print(i+"  ");
        isVisited[i]=true;
        //结点入队列
        queue.addLast(i);
        while (!queue.isEmpty()) {
            u=((Integer)queue.removeFirst()).intValue();
            w=getFirstNeighbor(u);
            while(w!=-1) {//这是遍历当前层
                if(!isVisited[w]) {
                    //访问该结点
//                    System.out.print(getValueByIndex(w)+"  ");
                    System.out.print(w+"  ");
                    //标记已被访问
                    isVisited[w]=true;
                    if(w == end){
                        System.out.println("分支");
                        break;
                    }
                    //入队列
                    queue.addLast(w);
                }
                //寻找下一个邻接结点
                w=getNextNeighbor(u, w);
            }
        }
    }

    //对外公开函数，广度优先遍历
    public void broadFirstSearch(int start,int end) {
        this.end = end;

        boolean[] isVisited=new boolean[getNumOfVertex()];
        for (int i=0;i<getNumOfVertex();i++) {
            isVisited[i]=false;
        }
        for(int i=start;i<getNumOfVertex();i++) {
            if(!isVisited[i]) {
                broadFirstSearch(isVisited, i);
            }
        }
    }
}
