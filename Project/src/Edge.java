
class Edge {
    private Graph.LeftVertex leftV;
    private Graph.RightVertex rightV;
    private boolean includeMatching; // флаг входит ли ребро в паросочетание

    public Edge(Graph.LeftVertex leftV, Graph.RightVertex rightV) {
        this.leftV = leftV;
        this.rightV = rightV;
        this.includeMatching = false;
    }

    public Graph.Vertex go(Graph.Vertex preV) {
        return preV.getNeedV(leftV, rightV);
    }

    public boolean isIncludeMatching() {
        return includeMatching;
    }

    public void doIncludeInMatching() {
        includeMatching = true;
        leftV.setMatchingEdge(this);
        rightV.setMatchingEdge(this);
    }

    public void doExcludeFromMatching() {
        includeMatching = false;
        leftV.setMatchingEdge(null);
        rightV.setMatchingEdge(null);
    }

    public String toString() {
        return leftV.getName() + "--" + rightV.getName();
    }
}
