// Middle Man Example
class Worker {
    public void doWork() {
        System.out.println("Worker is doing work...");
    }
}

class Manager {
    private Worker worker = new Worker();

    // Manager does almost nothing except delegate
    public void startWork() {
        worker.doWork(); // delegation
    }

    public void supervise() {
        worker.doWork(); // delegation
    }
}
