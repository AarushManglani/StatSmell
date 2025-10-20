import java.util.Random;

class RaceCar extends Thread {
    private String carName;
    private int distanceCovered = 0;
    private static final int FINISH_LINE = 100;
    private Random rand = new Random();

    public RaceCar(String name) {
        this.carName = name;
    }

    @Override
    public void run() {
        while (distanceCovered < FINISH_LINE) {
            int step = 5 + rand.nextInt(11);
            distanceCovered += step;
            if (distanceCovered > FINISH_LINE) {
                distanceCovered = FINISH_LINE;
            }
            System.out.println(carName + " is at " + distanceCovered + " meters.");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
        System.out.println(carName + " finished the race!");
    }
}

public class CarRace {
    public static void main(String[] args) {
        RaceCar carA = new RaceCar("Car A");
        RaceCar carB = new RaceCar("Car B");
        RaceCar carC = new RaceCar("Car C");

        carA.start();
        carB.start();
        carC.start();
    }
}