public class MainDeadlockExample {
    private static class DeadClass {
        public synchronized DeadClass getDead(DeadClass class2) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " working in getDead");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " try getName class2");
            String s = class2.getName();
            return class2;
        }

        public synchronized String getName(){
            return this.toString();
        }
    }


    public static void main(String[] args) {
        DeadClass class1 = new DeadClass();
        DeadClass class2 = new DeadClass();

        new Thread(() -> class1.getDead(class2)).start();
        new Thread(() -> class2.getDead(class1)).start();
    }
}
