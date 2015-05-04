package Objects;

import java.awt.*;

public class Individual {

    private Point position;

    public Individual(){
        this.position = new Point();
    }

    public Individual(Point position){
        this.position = position;
    }

    public Point getPosition(){
        return this.position;
    }

    public void setPosition(Point position){
        this.position = position;
    }

    public void setPosition(double x, double y){
        this.position.setLocation(x, y);
    }

}
