package diningphilosophers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {

    private static int stickCount = 0;
    private boolean iAmFree = true;
    private final int myNumber;
	private final Lock verrou = new ReentrantLock();

    public ChopStick() {
        myNumber = ++stickCount;
    }

    synchronized public boolean tryTake(int delay) throws InterruptedException {
        if (verrou.tryLock()) {
            try {
                if (!iAmFree) {
                    Thread.sleep(delay);
                    if (!iAmFree) {
                        return false; // Échec
                    }
                }
                iAmFree = false;
                return true; // Succès
            } finally {
                verrou.unlock();
            }
        } else {
            return false; // Échec
        }
    }

    synchronized public void release() {
        verrou.lock();
        try {
            iAmFree = true;
            System.out.println("Stick " + myNumber + " Released");
        } finally {
            verrou.unlock();
        }
    }

    @Override
    public String toString() {
        return "Stick#" + myNumber;
    }
}

