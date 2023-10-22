package seedu.financialplanner.list;

import seedu.financialplanner.enumerations.ExpenseType;
import seedu.financialplanner.enumerations.IncomeType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public abstract class Cashflow {

    protected static double balance = 0;
    protected double amount;
    protected int recur;

    public Cashflow(double amount, int recur) {
        this.amount = amount;
        this.recur = recur;
    }
    public void deleteCashflowvalue() {
    }

    //@author mhadidg-reused
    //Reused from https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    //@author mhadidg

    //@author Nick Bolton-reused
    //Reused from
    //https://stackoverflow.com/questions/1892765/how-to-capitalize-the-first-character-of-each-word-in-a-string
    public String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
    //@author Nick Bolton

    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");

        String string = "   Amount: " + decimalFormat.format(round(amount, 2));

        if (recur != 0) {
            string += System.lineSeparator() + "   Recurring every: " + recur + " days";
        }

        return string;
    }

    public String formatBalance() {
        DecimalFormat decimalFormat = new DecimalFormat("####0.00");

        return decimalFormat.format(round(Cashflow.balance, 2));
    }

    public double getAmount() {
        return this.amount;
    }

    public static double getBalance() {
        return balance;
    }

    public String formatString() {
        return " | " + this.recur;
    }

    public abstract ExpenseType getExpenseType();

    public abstract IncomeType getIncomeType();
}
