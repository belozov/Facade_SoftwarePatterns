//Builder

class Bank {
    private String name = "CustomBank";
    private String creditType = "Standard";

    public Bank setName(String name) {
        this.name = name;
        return this;
    }

    public Bank setCreditType(String creditType) {
        this.creditType = creditType;
        return this;
    }

    public Payment buildBank() {
        return new Payment() {
            @Override
            public void pay(double amount) {
                System.out.println("Payment in the amount of: " + amount + "₸ was made through the user's card "
                        + name + "(" + creditType + ")");
            }
        };
    }
}