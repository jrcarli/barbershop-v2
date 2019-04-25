package com.example.barbershop;

public class Barbershop {

    protected final static int NUM_SEATS = 3;
    protected final static int DEFAULT_NUM_CUSTOMERS = 15;

    // These are used to track the order of customers once they've taken a seat
    public static String[] customerOrder;
    public static volatile int currentCustomer;
    public static volatile int customerCounter;

    public static void main(String[] args) {
        int numCustomers = DEFAULT_NUM_CUSTOMERS;
        boolean verbose = false;

        // Allow for the user to specify the number of customers
        if(args.length == 1) {
            try {
                numCustomers = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                System.out.println("Unsupported number of customers (param must be an integer)");
                System.exit(1);
            }
        } else if(args.length > 2) {
            System.out.println("Unsupported parameters (one optional param: number of customers).");
            System.exit(1);
        }

        // Basic input checking
        if(numCustomers < 0) {
            System.err.println("You must specify 0 or more customers for the day.");
            System.exit(1);
        }

        // Instantiate our customer order tracking list
        Barbershop.customerOrder = new String[numCustomers];
        Barbershop.currentCustomer = 0;
        Barbershop.customerCounter = 0;

        // Create the Seats
        Seats seats = new Seats(NUM_SEATS);

        // Create the Barber
        Barber barber = new Barber();

        System.out.println("Opening barbershop for the day ... looks like " + numCustomers + " customers ...");
        Customer[] customers = new Customer[numCustomers];

        for(int i=0; i<numCustomers; i++) {
            customers[i] = new Customer(seats, barber, verbose);
            customers[i].start();
        }

        for(int i=0; i<numCustomers; i++) {
            try {
                customers[i].join();
            } catch(InterruptedException e) {
                break;
            }
        }
        System.out.println("The barber has cut the hair of " + numCustomers + " customers.");
    }

}
