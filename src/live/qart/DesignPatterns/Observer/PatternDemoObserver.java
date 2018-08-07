package live.qart.DesignPatterns.Observer;

public class PatternDemoObserver {

    public static void main(String[] args) {

        Subject subject = new Subject();
        new BinaryObserver(subject);
        new HexObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);

        System.out.println("First state change: 5");
        subject.setState(5);


    }
}
