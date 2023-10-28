package seedu.financialplanner.commands;

import seedu.financialplanner.enumerations.CashflowCategory;
import seedu.financialplanner.cashflow.Budget;
import seedu.financialplanner.cashflow.CashflowList;
import seedu.financialplanner.utils.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCashflowCommand extends Command {
    private static final Logger logger = Logger.getLogger("Financial Planner Logger");
    protected CashflowCategory category = null;
    protected int index;
    protected CashflowList cashflowList = CashflowList.getInstance();

    public DeleteCashflowCommand(RawCommand rawCommand) throws IllegalArgumentException {
        String stringIndex;
        String stringCategory = null;

        if (rawCommand.args.size() == 1) {
            stringIndex = rawCommand.args.get(0);
        } else if (rawCommand.args.size() == 2) {
            stringCategory = rawCommand.args.get(0);
            handleInvalidCategory(stringCategory);
            stringIndex = rawCommand.args.get(1);
        } else {
            throw new IllegalArgumentException("Incorrect arguments.");
        }

        try {
            logger.log(Level.INFO, "Parsing index as integer");
            index = Integer.parseInt(stringIndex);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid argument for index");
            throw new IllegalArgumentException("Index must be an integer");
        }

        if (index == 0) {
            logger.log(Level.WARNING, "Invalid value for index");
            throw new IllegalArgumentException("Index must be within the list");
        }
    }

    private void handleInvalidCategory(String stringCategory) {
        try {
            logger.log(Level.INFO, "Parsing CashflowCategory");
            category = CashflowCategory.valueOf(stringCategory.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, "Invalid arguments for CashflowCategory");
            throw new IllegalArgumentException("Entry must be either income or expense");
        }
    }

    @Override
    public void execute() {
        if (category == null) {
            handleDeleteCashflowWithoutCategory();
            return;
        }

        assert category.equals(CashflowCategory.INCOME) || category.equals(CashflowCategory.EXPENSE);
        assert index != 0;

        switch (category) {
        case INCOME:
        case EXPENSE:
            handleDeleteCashflowWithCategory();
            break;
        default:
            logger.log(Level.SEVERE, "Unreachable default case reached");
            Ui.getInstance().showMessage("Unidentified entry.");
            break;
        }
    }

    private void handleDeleteCashflowWithoutCategory() {
        try {
            logger.log(Level.INFO, "Deleting cashflow without category");
            double amount = cashflowList.deleteCashflowWithoutCategory(index);
            if (Budget.hasBudget()) {
                Budget.updateCurrentBudget(amount);
            }
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of list");
            throw new IllegalArgumentException("Index must be within the list");
        }
    }

    private void handleDeleteCashflowWithCategory() {
        try {
            logger.log(Level.INFO, "Deleting cashflow with category");
            double amount = cashflowList.deleteCashflowWithCategory(category, index);
            if (Budget.hasBudget()) {
                Budget.updateCurrentBudget(amount);
            }
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of list");
            throw new IllegalArgumentException("Index must be within the list");
        }
    }
}
