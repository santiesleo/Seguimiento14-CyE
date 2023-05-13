package model;
import java.util.ArrayList;

public class Graph<T> implements IGraph<T>{
    private ArrayList<ArrayList<Vertex<T>>> adjList; // Lista de adyacencia que almacena las conexiones entre vértices
    public ArrayList<Vertex<T>> vertexs; // Lista de vértices del grafo
    private int time = 0; // Variable para el tiempo en el recorrido DFS

    public Graph() {
        vertexs = new ArrayList<>(); // Inicializa la lista de vértices vacía
        adjList = new ArrayList<>(); // Inicializa la lista de adyacencia vacía
    }

    @Override
    public void addVertex(T data) {
        Vertex<T> vertex = new Vertex<>(data); // Crea un nuevo vértice con el valor especificado
        vertexs.add(vertex); // Agrega el vértice a la lista de vértices
        adjList.add(new ArrayList<>()); // Crea una nueva lista de adyacencia vacía para el nuevo vértice
    }

    public int vertexStartExist(T start) throws GraphException {
        int startIndex = getIndex(start); // Obtiene el índice del vértice de inicio en la lista de vértices
        if (startIndex == -1) {
            throw new GraphException("VERTEX DOES NOT EXIST"); // Verifica el vértice de inicio existe en la lista de vértices
        }
        return startIndex;
    }

    public int vertexEndExist(T end) throws GraphException {
        int endIndex = getIndex(end); // Obtiene el índice del vértice de fin en la lista de vértices

        if (endIndex == -1) {
            throw new GraphException("VERTEX DOES NOT EXIST"); // Verifica si el vertice de fin existe en la lista de vértices
        }
        return endIndex;

    }


    @Override
    public void addNotDirectedEdge(T start, T end) {
        try {
            int startIndex = vertexStartExist(start); // Obtiene el índice del vértice de inicio en la lista de vértices
            int endIndex = vertexEndExist(end); // Obtiene el índice del vértice de fin en la lista de vértices

            Vertex<T> startVertex = vertexs.get(startIndex); // Obtiene el vértice de inicio
            Vertex<T> endVertex = vertexs.get(endIndex); // Obtiene el vértice de fin

            adjList.get(startIndex).add(endVertex); // Agrega el vértice de fin a la lista de adyacencia del vértice de inicio
            adjList.get(endIndex).add(startVertex); // Agrega el vértice de inicio a la lista de adyacencia del vértice de fin

        } catch (GraphException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Agrega una nueva arista dirigida entre dos vértices
    @Override
    public void addDirectedEdge(T start, T end) {
        try {
            int startIndex = vertexStartExist(start);
            int endIndex = vertexEndExist(end);

            // Obtiene el vértice de inicio y el vértice final
            Vertex<T> endVertex = vertexs.get(endIndex);

            // Agrega el vértice final a la lista de adyacencia del vértice de inicio
            adjList.get(startIndex).add(endVertex);

        } catch (GraphException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Realiza el recorrido en anchura (BFS) a partir del vértice raíz dado
    @Override
    public void bfs(T root) throws QueueException, GraphException {
        int rootIndex = getIndex(root);

        // Verifica si el vértice raíz existe
        if (rootIndex == -1) {
            throw new GraphException("VERTEX DOES NOT EXIST");
        }

        // Inicializa todos los vértices como no visitados (blanco) con distancia 1 y sin vértice previo
        for (Vertex<T> v : vertexs) {
            v.setColor(Colors.WHITE);
            v.setDistance(1);
            v.setPrevious(null);
        }

        // Inicializa el vértice raíz como visitado (gris) con distancia 0 y sin vértice previo
        Vertex<T> initVertex = vertexs.get(rootIndex);
        initVertex.setColor(Colors.GRAY);
        initVertex.setDistance(0);
        initVertex.setPrevious(null);

        // Crea una cola para realizar BFS
        Queue<Vertex<T>> queue = new Queue<>();
        queue.enqueue(initVertex);

        // Realiza BFS hasta que la cola esté vacía
        while (!queue.isEmpty()) {
            // Desencola el vértice actual y lo procesa
            Vertex<T> current = queue.dequeue();
            System.out.println(current.getValue() + " its distance is: [" + current.getDistance() + "]\n");

            // Obtiene los vértices adyacentes al vértice actual
            ArrayList<Vertex<T>> adjs = adjList.get(vertexs.indexOf(current));

            // Visita cada vértice adyacente
            for (Vertex<T> adj: adjs){
                if (adj.getColor() == Colors.WHITE){
                    // Marca el vértice adyacente como visitado (gris) con una distancia actualizada y un vértice previo
                    adj.setColor(Colors.GRAY);
                    adj.setDistance(current.getDistance() + 1);
                    adj.setPrevious(current);
                    // Encola el vértice adyacente para explorarlo más adelante
                    queue.enqueue(adj);
                }
            }

            // Marca el vértice actual como explorado (negro)
            current.setColor(Colors.BLACK);
        }
    }

    // Realiza el recorrido en profundidad (DFS)
    @Override
    public void dfs() {
        // Inicializa todos los vértices como no visitados (blanco) y sin vértice previo
        for (Vertex<T> vertex : vertexs) {
            vertex.setColor(Colors.WHITE);
            vertex.setPrevious(null);
        }

        this.time = 0; // Inicializa el contador de tiempo

        // Realiza DFS para cada vértice no visitado
        for (Vertex<T> vertex : vertexs) {
            if (vertex.getColor() == Colors.WHITE) {
                dfs(vertex);
            }
        }
    }

    // Función auxiliar para realizar el DFS recursivo
    private void dfs(Vertex<T> vertex) {
        time++; // Incrementa el tiempo
        vertex.setInitialTime(time); // Establece el tiempo inicial del vértice
        vertex.setColor(Colors.GRAY); // Marca el vértice como visitado (gris)

        // Explora los vértices adyacentes al vértice actual
        for (Vertex<T> v : adjList.get(vertexs.indexOf(vertex))) {
            if (v.getColor() == Colors.WHITE) {
                v.setPrevious(vertex); // Establece el vértice actual como vértice previo del vértice adyacente
                dfs(v); // Realiza DFS en el vértice adyacente
            }
        }

        vertex.setColor(Colors.BLACK); // Marca el vértice como explorado (negro)
        time++; // Incrementa el tiempo
        vertex.setFinalTime(time); // Establece el tiempo final del vértice
    }

    @Override
    public int getIndex(T value) {
        for (int i = 0; i < vertexs.size(); i++) {
            if (vertexs.get(i).getValue().equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<ArrayList<Vertex<T>>> getAdjList() {
        return adjList;
    }

    public void setAdjList(ArrayList<ArrayList<Vertex<T>>> adjList) {
        this.adjList = adjList;
    }

    public ArrayList<Vertex<T>> getVertexs() {
        return vertexs;
    }

    public void setVertexs(ArrayList<Vertex<T>> vertexs) {
        this.vertexs = vertexs;
    }

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
}