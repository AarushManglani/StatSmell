class Worker extends Thread{
    public void run(){
        for(int i=1;i<=5;i++){
            System.out.println(i);
            try {
                Thread.sleep(200);
            }catch(InterruptedException e){
                System.out.println("Interrupted");
                break;
            }
        }
    }
}

public class Alive {
    public static void main(String[] args){
        Worker w1 = new Worker();
        w1.start();
        System.out.println(w1.isAlive());
        try{
            w1.join();
        }catch(InterruptedException e){
            System.out.println("Interrupted");
        }
        System.out.println(w1.isAlive());
    }
}
