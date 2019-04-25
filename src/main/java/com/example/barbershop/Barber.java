package com.example.barbershop;

import java.util.concurrent.ThreadLocalRandom;

public class Barber {

    private static final int HAIRCUT_LOWER_BOUND = 1;
    private static final int HAIRCUT_UPPER_BOUND = 4;

    public Barber() { }

    public synchronized void haircut() {
        // Wait until our name is called
        // Note: our name can only be called after we've taken a seat (otherwise our name isn't on the list)
        while(!Barbershop.customerOrder[Barbershop.currentCustomer].equals(Thread.currentThread().getName())) {
            try {
                wait();
            } catch(InterruptedException e) {
                System.err.println("Caught InterruptedException in haircut wait loop");
            }
        }

        try {
            Thread.sleep(1000 * (ThreadLocalRandom.current().nextInt(HAIRCUT_LOWER_BOUND, HAIRCUT_UPPER_BOUND+1)));
        } catch(InterruptedException e) {
            System.err.println("Caught InterruptedException in haircut");
        }
        System.out.println(Thread.currentThread().getName() + " has gotten a haircut.");
        Barbershop.currentCustomer++;
        notifyAll();
    }
}
