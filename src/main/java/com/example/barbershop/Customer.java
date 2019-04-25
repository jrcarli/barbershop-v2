package com.example.barbershop;

import java.util.concurrent.ThreadLocalRandom;

public class Customer extends Thread {

    private static final int COME_BACK_UPPER_BOUND = 3;

    private boolean verbose;
    private boolean needHaircut;
    private Seats seats;
    private Barber barber;

    public Customer(Seats seats, Barber barber, boolean verbose) {
        this.needHaircut = true;
        this.seats = seats;
        this.barber = barber;
        this.verbose = verbose;
    }

    @Override
    public void run() {
        while(needHaircut) {
            if(seats.takeSeat()) {
                getHaircut();
            } else {
                comeBack();
            }
        }
    }

    private void comeBack() {
        try {
            int period = ThreadLocalRandom.current().nextInt(COME_BACK_UPPER_BOUND);
            if(verbose) {
                System.out.println(Thread.currentThread().getName() + " is coming back in " + period + " seconds.");
            }
            Thread.sleep(1000 * period);
        } catch(InterruptedException e) {
            // noop
        }
    }

    private void getHaircut() {
        barber.haircut();
        needHaircut = false;
        seats.leaveSeat();
    }

}
