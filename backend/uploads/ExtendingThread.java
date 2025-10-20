class Thread1 extends Thread{
    public void run(){
        for(int i=2;i<=10;i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " is interrupted");
                    break;
                }
            }
        }
    }
}

class Thread2 extends Thread{
    public void run(){
        for(int i=1;i<=9;i++){
            if(i%2!=0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " is interrupted");
                    break;
                }
            }
        }
    }
}

public class ExtendingThread {
    public static void main(String[] args){
        Thread1 t1= new Thread1();
        Thread2 t2= new Thread2();

        t1.setName("Even");
        t2.setName("Odd");

        t1.start();
        t2.start();

    }

}
