package live.qart.DesignPatterns.Observer;

/**
 * Created by QArt on 19/03/2018.
 */
public class HexObserver extends ObserverQ{

    public HexObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Hex String: " + Integer.toHexString( subject.getState() ).toUpperCase() );
    }
}