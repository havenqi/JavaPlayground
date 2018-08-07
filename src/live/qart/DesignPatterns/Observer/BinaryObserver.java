package live.qart.DesignPatterns.Observer;

/**
 * Created by QArt on 19/03/2018.
 */
public class BinaryObserver extends ObserverQ {

    public BinaryObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Binary String: " + Integer.toBinaryString( subject.getState()));
    }
}