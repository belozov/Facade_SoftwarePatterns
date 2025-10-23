class FreedomBank implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Payment was successful!\n" + amount + "₸ (FreedomBank)");
    }
}