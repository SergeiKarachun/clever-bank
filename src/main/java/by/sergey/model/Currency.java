package by.sergey.model;

public enum Currency {
    USD("Dollar USA"),
    EUR("Euro"),
    BYN("Ruble BLR");

    private final String name;

    Currency(String name) {
        this.name = name;
    }

    public static Currency typeOfIndex(Integer index) {
        return Currency.values()[index];
    }

    public static void showAll() {
        BankUtil.createMessage("Available 3 types of currencies");
        System.out.println(Currency.USD);
        System.out.println(Currency.EUR);
        System.out.println(Currency.BYN);
    }

    public static Currency typeInIndex(Integer i) {
        return Currency.values()[i];
    }
}
