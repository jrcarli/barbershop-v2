package com.example.barbershop;

public class Seats {

    private int totalSeats;
    private int freeSeats;

    public Seats(int totalSeats) {
        this.totalSeats = totalSeats;
        this.freeSeats = this.totalSeats;
    }

    public synchronized boolean takeSeat() {
        // Look around for a free seat; leave if none are free
        while(freeSeats <= 0) {
            try {
                wait(0, 1);
            } catch(InterruptedException e) {
                System.err.println("takeSeat interrupt");
                return false;
            }

            // Check if we've woken up because someone called notify, or because of timeout (still no seats)
            if(freeSeats <= 0) {
                // Leave and come back
                return false;
            }
        }

        // Claim a seat
        freeSeats--;

        // Add our name to the customer order array
        Barbershop.customerOrder[Barbershop.customerCounter] = Thread.currentThread().getName();

        // Increment the customer counter so the next customer's name goes in the correct spot in the array
        Barbershop.customerCounter++;

        System.out.println(Thread.currentThread().getName() + " has taken a seat.");

        // Signal to caller that we've taken a seat and don't need to come back later
        return true;
    }

    public synchronized void leaveSeat() {
        // Free up a seat
        freeSeats++;

        // Let other Customers know that a seat has freed up
        notifyAll();
    }

}
