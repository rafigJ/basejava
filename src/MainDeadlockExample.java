public class MainDeadlockExample {
    private static class Class1 {
        public synchronized Class2 getClass2(Class2 class2) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " working in getClass2");
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
            return this.getName() + " Class1";
        }
    }

    private static class Class2 {
        public synchronized Class1 getClass1(Class1 class1) {
            String name = Thread.currentThread().getName();
            System.out.println(name + " working in getClass1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " try getName class1");
            String s = class1.getName();
            return class1;
        }

        public synchronized String getName(){
            return this.getName() + " Class2";
        }
    }

    public static void main(String[] args) {
        Class1 class1 = new Class1();
        Class2 class2 = new Class2();
        new Thread(() -> class1.getClass2(class2)).start();
        new Thread(() -> class2.getClass1(class1)).start();
    }
}
