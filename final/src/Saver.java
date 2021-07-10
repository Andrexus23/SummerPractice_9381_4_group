import java.util.ArrayList;

public class Saver  {
    private ArrayList<Step> steps;
    private Graph initial;
    private int index;

    public Saver() {
        steps = new ArrayList<Step>();
        index = 0;
    }

    public void appendGraph(Graph graph, boolean isEndOfIteration){
        steps.add(new Step(graph.copyGraph(), isEndOfIteration));
    }

    public void setInitial(Graph graph){
        this.initial = graph.copyGraph();
    }

    public Graph getInitial(){
        return this.initial;
    }

    public Graph stepForward(){
        if (index == steps.size() - 1) return null;
        return steps.get(++index).graph;
    }

    public Graph stepBack(){
        if(index == 0) return null;
        return steps.get(--index).graph;
    }

    public Step getCurrent(){
        //if((index > graphs.size() - 1) || (index < 0)) return null;
        return steps.get(index);
    }

    public Graph iterForward(){
        do {
            if (stepForward() == null) return null;
        } while(!getCurrent().isEndOfIteration);
        return getCurrent().graph;
    }

    public Graph iterBack(){
        do {
            if (stepBack() == null) return null;
        } while(!getCurrent().isEndOfIteration && index > 0);
        return getCurrent().graph;
    }

    public Graph reSet() {
        index = 0;
        return steps.get(index).graph;
    }

    public Graph moveToEnd() {
        index = steps.size() - 1;
        return steps.get(index).graph;
    }

}
