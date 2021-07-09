public class Step {
    public Graph graph;
    public boolean isEndOfIteration;
    Step(Graph graph, boolean isEndOfIteration, StringBuilder logMes){
        this.graph = graph;
        this.isEndOfIteration = isEndOfIteration;
       // this.logMes.append(logMes);
    }
}
