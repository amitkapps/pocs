import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 3/15/11
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class CyclicPCMInvoker {
    final int workers;
    final int iterationsPerWorker;
    final CyclicBarrier startBarrier;
    final CyclicBarrier endBarrier;

    class PCMInvoker implements Runnable {

        int myNo;
        PCMInvoker(int row) {
            myNo = row;
            System.out.println("Invoker-"+myNo + " will sleep for " + 5*myNo + " secs");
        }

        public void run() {
            int count = 5;
            for(int i=0; i<count; i++) {

                try {
                    startBarrier.await();
                    System.out.println("Invoker-" + myNo + " started " + i + "th iteration");
                    Thread.currentThread().sleep(5000*myNo);
                    if(myNo==0 && i ==3){
                        System.out.println("Invoker-" + myNo + " stalling on " + i + "th iteration");

                        Thread.currentThread().sleep(120000);
                    }
                    System.out.println("Invoker-" + myNo + " ended " + i + "th iteration");
                    endBarrier.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    return;
                } catch (BrokenBarrierException ex) {
                    ex.printStackTrace();
                    return;
                }
            }
        }
    }

    public CyclicPCMInvoker(int workers, int iterations) {
        this.workers = workers;
        this.iterationsPerWorker = iterations;
        startBarrier = new CyclicBarrier(this.workers,
                new Runnable() {
                    int count =0;
                    public void run() {
                        System.out.println("Started invocation " + ++count);
                        endBarrier.reset();
                    }
                });

        endBarrier = new CyclicBarrier(this.workers,
                new Runnable() {
                    int count = 0;
                    public void run() {
                        System.out.println("Finished invocation " + ++count);
                        startBarrier.reset();
                    }
                });

        Thread[] t = new Thread[this.workers];
        for (int i = 0; i < this.workers; ++i){
            t[i] = new Thread(new PCMInvoker(i));
            t[i].start();
        }

        for (int i = 0; i < this.workers; ++i){
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

    }

    public static void main(String[] args){
        System.out.println("Starting CyclicPCMInvoker");
        new CyclicPCMInvoker(2, 5);

    }

}
