package model;

public interface IGraph<T> {
    public void addVertex(T data);
    public void addNotDirectedEdge(T init, T end) throws GraphException;
    public void addDirectedEdge(T init, T end) throws GraphException;
    public int getIndex(T val);
    public void dfs();
    public void bfs(T root) throws QueueException, GraphException;
}