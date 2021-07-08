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

    public void appendVertex(Vertex v) {
        v.putSelf();
    }

    public void appendEdge(Edge e) {
        graphEdges.add(e);
    }

    public ArrayList<LeftVertex> getLeftVertexes() {
        return leftShare;
    }

    public RightVertex getRightVertex(String vertID) {
        for (RightVertex rightVertex : rightShare)
            if (rightVertex.getName().equals(vertID))
                return rightVertex;
        RightVertex rightV = new RightVertex(vertID);
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

    /*public boolean checkShares(Graph.LeftVertex l, Graph.RightVertex r){
        for(LeftVertex i : leftShare) {
            if (r.name.equals(i.name))
                return false;
        }
        for (RightVertex i: rightShare) {
            if (l.name.equals(i.name))
                return false;
        }
         return true;
    }*/


    public void updatePasses() {
        for (Vertex v : leftShare) v.setPassFalse();
        for (Vertex v : rightShare) v.setPassFalse();
    }

    public void printMatching() {
        for (Edge e : graphEdges) if (e.isIncludeMatching()) System.out.println(e);
    }


    abstract class Vertex {
        protected String name;
        public ArrayList<Edge> edges;
        private boolean passed;
        protected Edge matchingEdge;

        public Vertex(String name) {
            this.name = name;
            this.edges = new ArrayList<Edge>();
            this.passed = false;
            this.matchingEdge = null;
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

        public void putSelf() {
            rightShare.add(this);
        }

        public Vertex getNeedV(LeftVertex leftV, RightVertex rightV) {
            return leftV;
        }
    }
}
