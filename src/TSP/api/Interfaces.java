package TSP.api;

import TSP.GV;
import TSP.graph.Edge;
import TSP.graph.Node;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class Interfaces {
    public final static Movable moveEdge = (e) -> {
        {
            //if this has a line or not
            if(((Edge)e[0]).isContainsLine())
            {
                ((Edge)e[0]).getWeightLabel().setLayoutX((((Edge)e[0]).getFrom().getLocation().getX() + ((Edge)e[0]).getTo().getLocation().getX())/2 - ((Edge)e[0]).getWeightLabel().getWidth()/2);
                ((Edge)e[0]).getWeightLabel().setLayoutY((((Edge)e[0]).getFrom().getLocation().getY() + ((Edge)e[0]).getTo().getLocation().getY())/2 - ((Edge)e[0]).getWeightLabel().getHeight()/2);
                ((Edge)e[0]).getWeightLabel().setText(Node.distanceStr(((Edge)e[0]).getFrom() , ((Edge)e[0]).getTo()));
                ((Edge)e[0]).setStartX(((Edge)e[0]).getTo().getLocation().getX());
                ((Edge)e[0]).setStartY(((Edge)e[0]).getTo().getLocation().getY());
                ((Edge)e[0]).setEndX(((Edge)e[0]).getFrom().getLocation().getX());
                ((Edge)e[0]).setEndY(((Edge)e[0]).getFrom().getLocation().getY());
            }
            else {
                ((Edge)e[1]).getWeightLabel().setLayoutX((((Edge)e[1]).getFrom().getLocation().getX() + ((Edge)e[1]).getTo().getLocation().getX())/2 - ((Edge)e[1]).getWeightLabel().getWidth()/2);
                ((Edge)e[1]).getWeightLabel().setLayoutY((((Edge)e[1]).getFrom().getLocation().getY() + ((Edge)e[1]).getTo().getLocation().getY())/2 - ((Edge)e[1]).getWeightLabel().getHeight()/2);
                ((Edge)e[1]).getWeightLabel().setText(Node.distanceStr(((Edge)e[1]).getFrom() , ((Edge)e[1]).getTo()));
                ((Edge)e[1]).setStartX(((Edge)e[1]).getFrom().getLocation().getX());
                ((Edge)e[1]).setStartY(((Edge)e[1]).getFrom().getLocation().getY());
                ((Edge)e[1]).setEndX(((Edge)e[1]).getTo().getLocation().getX());
                ((Edge)e[1]).setEndY(((Edge)e[1]).getTo().getLocation().getY());

            }

        }
    };

    public final static Movable moveNode  = (node)-> {

        node[0].setLayoutX(GV.mousePosition.getX() - GV.radius);
        node[0].setLayoutY(GV.mousePosition.getY()- GV.radius);
        ((Node)node[0]).getLocation().set(GV.mousePosition.getX() , GV.mousePosition.getY());
    };




}

interface Movable
{
    //if e doesnt contain the line in borderPane edge does so we work with edge then
    void move(javafx.scene.Node... nodes);
}
