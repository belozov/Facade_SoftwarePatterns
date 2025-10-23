class Discount extends PaymentDecorator {
    private double discount;

    public Discount(Payment wrappee, double discount) {
        super(wrappee);
        this.discount = discount;
    }

    @Override
    public void pay(double amount) {
        double discounted = amount * (1 - discount / 100);
        System.out.println("Discount operation was successful!\nNew Total: " + discounted + "₸");
        wrappee.pay(discounted);
    }
}