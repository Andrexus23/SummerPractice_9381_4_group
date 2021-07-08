import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogButtons extends JPanel {
    public Button BuildGraph = new Button("Build graph");
    public Button Reset = new Button("Reset");
    public Button AlgSimulation = new Button("Algorithm simulation");
    public Button AlgStepFurther = new Button("Step further");
    public Button AlgStepBack = new Button("Step back");
    public Button AlgIterFurther = new Button("Iteration further");
    public Button AlgIterBack = new Button("Iteration back");
    public JLabel Iteration = new JLabel("Iteration: ");
    public JTextArea LogField = new JTextArea("Log field...");
    public JScrollPane scroll;

    public Graph graph;
    public GraphInfo info;

    public LogButtons(Graph g, GraphInfo gi){
        graph=g;
        info=gi;
        LogField.setEditable(false);
        LogField.setLineWrap(true);

        blockUnblock(true);

        this.setBorder(new LineBorder(Color.black,1));
        setLayout(new GridLayout(1,1));
        this.setBackground(Color.white);
        JPanel back =new JPanel();
        back.setBackground(Color.white);
        back.setBorder(new LineBorder(Color.WHITE,10));
        back.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel buttonsUp =new JPanel();

        JPanel buttonsDown = new JPanel();
        buttonsUp.setBackground(Color.WHITE);
        buttonsUp.setLayout(new GridLayout(2,1,10,10));
        buttonsDown.setBackground(Color.WHITE);
        buttonsDown.setLayout(new GridLayout(6,1,10,10));
        ImproveButtonGraphics(BuildGraph);
        buttonsUp.add(BuildGraph);
        ImproveButtonGraphics(Reset);
        buttonsUp.add(Reset);
        ImproveButtonGraphics(AlgSimulation);
        buttonsDown.add(AlgSimulation);
        ImproveButtonGraphics(AlgStepFurther);
        buttonsDown.add(AlgStepFurther);
        ImproveButtonGraphics(AlgStepBack);
        buttonsDown.add(AlgStepBack);
        ImproveButtonGraphics(AlgIterFurther);
        buttonsDown.add(AlgIterFurther);
        ImproveButtonGraphics(AlgIterBack);
        buttonsDown.add(AlgIterBack);

        Iteration.setFont(new Font("Arial",Font.BOLD,20));

        buttonsDown.add(Iteration);

        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx=2;
        gbc.weighty=2;
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        back.add(buttonsUp,gbc);
        gbc.weightx=2;
        gbc.weighty=2;
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridheight=1;
        gbc.gridwidth=1;
        back.add(buttonsDown,gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx=8;
        gbc.weighty=8;
        gbc.gridx=1;
        gbc.gridy=0;
        gbc.gridheight=2;
        gbc.gridwidth=3;

        scroll = new JScrollPane(LogField);
        scroll.setBorder(new LineBorder(Color.BLACK,1));
        LogField.setBorder(new LineBorder(Color.WHITE,10));
        back.add(scroll,gbc);

        BuildGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Draw();
            }
        });

        Reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clear();
            }
        });

        add(back);
    }
    void ImproveButtonGraphics(Button b){
        b.setFont(new Font("Arial",Font.BOLD,20));
        b.setBackground(Color.LIGHT_GRAY);
    }

    void Draw(){
        switch(checkInfo()){
            case NO_VERTEX_FIRST:
                JOptionPane.showMessageDialog(null,"There are no vertices in first graph part!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case NO_VERTEX_SECOND:
                JOptionPane.showMessageDialog(null,"There are no vertices in second graph part!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case EMPTY_VERTEX_FIRST:
                JOptionPane.showMessageDialog(null,"There is an uninitialized vertex in first graph part!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case EMPTY_VERTEX_SECOND:
                JOptionPane.showMessageDialog(null,"There is an uninitialized vertex in second graph part!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case SIMILAR_IN_BOTH:
                JOptionPane.showMessageDialog(null,"There are similar vertices in both graph parts!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case SIMILAR_IN_FIRST:
                JOptionPane.showMessageDialog(null,"There are similar vertices in first graph part!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case SIMILAR_IN_SECOND:
                JOptionPane.showMessageDialog(null,"There are similar vertices in second graph part!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case NO_EDGES:
                JOptionPane.showMessageDialog(null,"There are no edges in graph!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case SIMILAR_EDGES:
                JOptionPane.showMessageDialog(null,"There are similar edges in graph!","Error!",JOptionPane.ERROR_MESSAGE);
                return;
            case NONE:
                break;
        }
        graph.setVisible(true);
        blockUnblock(false);
        graph.repaint();
    }

    void Clear(){
        graph.setVisible(false);
        blockUnblock(true);
        info.first.num.setText("");
        info.second.num.setText("");
        for(int i=0;i<info.first.MAX_SIZE;i++){
            info.first.vertex[i].text.setText("");
        }
        for(int i=0;i<info.second.MAX_SIZE;i++){
            info.second.vertex[i].text.setText("");
        }
    }

    Errors checkInfo(){
        if(info.first.showing==0){
            return Errors.NO_VERTEX_FIRST;
        }
        if(info.second.showing==0){
            return Errors.NO_VERTEX_SECOND;
        }
        if(info.edges.showing==0){
            return Errors.NO_EDGES;
        }
        for(int i=0;i<info.first.showing;i++){
            if(info.first.vertex[i].text.getText().equals("")){
                return Errors.EMPTY_VERTEX_FIRST;
            }
            for(int j=i+1;j<info.first.showing;j++){
                if(info.first.vertex[i].text.getText().equals(info.first.vertex[j].text.getText())){
                    return Errors.SIMILAR_IN_FIRST;
                }
            }
        }
        for(int i=0;i<info.second.showing;i++){
            if(info.second.vertex[i].text.getText().equals("")){
                return Errors.EMPTY_VERTEX_SECOND;
            }
            for(int j=i+1;j<info.second.showing;j++){
                if(info.second.vertex[i].text.getText().equals(info.second.vertex[j].text.getText())){
                    return Errors.SIMILAR_IN_SECOND;
                }
            }
        }
        for(int i=0;i<info.first.showing;i++){
            for(int j=0;j<info.second.showing;j++){
                if(info.first.vertex[i].text.getText().equals(info.second.vertex[j].text.getText())){
                    return Errors.SIMILAR_IN_BOTH;
                }
            }
        }
        for(int i=0;i<info.edges.showing;i++){
            for(int j=i+1;j<info.edges.showing;j++){
                if(info.edges.edges[i].first.getSelectedIndex()==info.edges.edges[j].first.getSelectedIndex()&&
                        info.edges.edges[i].second.getSelectedIndex()==info.edges.edges[j].second.getSelectedIndex()){
                    return Errors.SIMILAR_EDGES;
                }
            }
        }
        return Errors.NONE;
    }

    void blockUnblock(boolean b){
        info.first.num.setEditable(b);
        for(int i=0;i<info.first.MAX_SIZE;i++){
            info.first.vertex[i].text.setEditable(b);
        }
        info.second.num.setEditable(b);
        for(int i=0;i<info.second.MAX_SIZE;i++){
            info.second.vertex[i].text.setEditable(b);
        }
        info.edges.num.setEditable(b);
        for(int i=0;i<info.edges.MAX_SIZE;i++){
            info.edges.edges[i].first.setEnabled(b);
            info.edges.edges[i].second.setEnabled(b);
        }
        AlgSimulation.setEnabled(!b);
        AlgStepFurther.setEnabled(!b);
        AlgStepBack.setEnabled(!b);
        AlgIterFurther.setEnabled(!b);
        AlgIterBack.setEnabled(!b);
    }
}
