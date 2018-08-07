package live.qart.DesignPatterns.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QArt on 19/03/2018.
 */
public class Subject {

    private List<ObserverQ> observers = new ArrayList<ObserverQ>();

    private int state;

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(ObserverQ observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (ObserverQ observer : observers) {
            observer.update();
        }
    }



}
