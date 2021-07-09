import java.util.ArrayList;

class Graph {
    private ArrayList<LeftVertex> leftShare;
    private ArrayList<RightVertex> rightShare;
    private ArrayList<Edge> graphEdges;
    static StringBuilder log;

    public Graph() {
        leftShare = new ArrayList<LeftVertex>();
        rightShare = new ArrayList<RightVertex>();
        graphEdges = new ArrayList<Edge>();
    }

    public Graph(ArrayList<LeftVertex> lv, ArrayList<RightVertex> rv, ArrayList<Edge> ed){
        leftShare = lv;
        rightShare = rv;
        graphEdges=ed;
    }

    public void appendVertex(Vertex v) {
        v.putSelf();
    }

    public void appendEdge(Edge e) {
        graphEdges.add(e);
    }

    public ArrayList<LeftVertex> getLeftVertexes() {
        return leftShare;
    }
    public ArrayList<RightVertex> getRightVertexes() {
        return rightShare;
    }
    public ArrayList<Edge> getEdges() {
        return graphEdges;
    }
    public RightVertex getRightVertex(Vertex toBeCopied) {
        for (RightVertex rightVertex : rightShare)
            if (rightVertex.getName().equals(toBeCopied.getName()))
                return rightVertex;
        RightVertex rightV = new RightVertex(toBeCopied);
        appendVertex(rightV);
        return rightV;
    }

    public LeftVertex findLeftVertex(String vertID) {
        for (LeftVertex leftVertex : leftShare) {
            if (leftVertex.getName().equals(vertID))
                return leftVertex;
        }
            return null;
    }

    public RightVertex findRightVertex(String vertID) {
        for (RightVertex rightVertex : rightShare) {
            if (rightVertex.getName().equals(vertID))
                return rightVertex;
        }
        return null;
    }

    public Edge findEdges(Edge e) {
        for (Edge edge : graphEdges) {
            if (edge.equals(e))
                return edge;
        }
        return null;
    }


    public void updatePasses() {
        for (Vertex v : leftShare) v.setPassFalse();
        for (Vertex v : rightShare) {
            v.setPassFalse();
            v.unsetResearchingNow();
        }
        for (Edge e : graphEdges) e.unsetResearchingNow();

    }

    public void printMatching() {
        for (Edge e : graphEdges) if (e.isIncludeMatching()) System.out.println(e);
    }

    public Graph copyGraph() {
        Graph graph = new Graph();

        for (Vertex v : this.getLeftVertexes()) {
            System.out.println(v.getName() + ":" + v.edges.size());
            LeftVertex leftV = graph.new LeftVertex(v);
            graph.appendVertex(leftV);

            for (Edge e : v.edges) {
                System.out.print(e + ", ");
                RightVertex rightV = graph.getRightVertex(e.getRightVertex());
                Edge edge = new Edge(e, leftV, rightV);
                leftV.appendEdge(edge);
                rightV.appendEdge(edge);
                graph.appendEdge(edge);
                if(e.isIncludeMatching()) edge.doIncludeInMatching();
            }
        }
        System.out.println("%%%" + graph.getEdges().size());
        return graph;
    }


    abstract class Vertex {
        protected String name;
        public ArrayList<Edge> edges;
        protected boolean passed;
        protected Edge matchingEdge;
        protected boolean isResearchingNow;

        public Vertex(String name) {
            this.name = name;
            this.edges = new ArrayList<Edge>();
            this.passed = false;
            this.matchingEdge = null;
            this.isResearchingNow = false;
        }

        public Vertex(Vertex toBeCopied){
            this.name = new String(toBeCopied.name);
            this.edges = new ArrayList<Edge>();
            this.passed = toBeCopied.passed;
            this.matchingEdge = null;
            this.isResearchingNow = toBeCopied.isResearchingNow;

        }

        public void appendEdge(Edge edge) {
            edges.add(edge);
        }

        public String getName() {
            return name;
        }

        public Edge getMatchingEdge() {
            return matchingEdge;
        }
        public void setResearchingNow(){
            this.isResearchingNow = true;
        }
        public boolean getResearchingNow(){
            return  this.isResearchingNow;
        };
        public void unsetResearchingNow(){
            this.isResearchingNow = false;
        }

        public void setMatchingEdge(Edge e) {
            matchingEdge = e;
        }

        public boolean getPassed() {
            return passed;
        }

        public void setPassTrue() {
            passed = true;
        }

        public void setPassFalse() {
            passed = false;
        }

        public abstract void putSelf(); // кладёт вершину на граф
        public abstract Vertex getNeedV(LeftVertex left, RightVertex right);
    }

    class LeftVertex extends Vertex {
        public LeftVertex(String name) {
            super(name);
        }

        public LeftVertex(Vertex toBeCopied) {
            super(toBeCopied);
        }

        public void putSelf() {
            leftShare.add(this);
        }

        public Vertex getNeedV(LeftVertex leftV, RightVertex rightV) {
            return rightV;
        }
    }

     class RightVertex extends Vertex {
        public RightVertex(String number) {
            super(number);
        }

         public RightVertex(Vertex toBeCopied) {
             super(toBeCopied);
         }

        public void putSelf() {
            rightShare.add(this);
        }

        public Vertex getNeedV(LeftVertex leftV, RightVertex rightV) {
            return leftV;
        }
    }
}
