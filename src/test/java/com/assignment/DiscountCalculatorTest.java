package com.assignment;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.assignment.discountcalculators.DiscountCalculator;
import com.assignment.discountcalculators.PercentageDiscountCalculator;
import com.assignment.discountcalculators.TotalValueDiscountCalculator;
import com.assignment.model.Bill;
import com.assignment.model.Item;
import com.assignment.model.User;
import com.assignment.roles.AffiliateRole;
import com.assignment.roles.CustomerRole;
import com.assignment.roles.EmployeeRole;
import com.assignment.roles.Role;
import org.joda.time.DateTime;
import org.junit.Test;

import static com.assignment.model.Item.ItemType.FURNITURE;
import static com.assignment.model.Item.ItemType.GROCERY;
import static com.assignment.model.Item.ItemType.MISC;
import static java.math.BigDecimal.ROUND_CEILING;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.CEILING;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.Assert.assertTrue;

public class DiscountCalculatorTest {

    private static final List<Item> FURNITURE_AND_GROCERY_ITEMS_1050_DOLLARS = asList(
            new Item("Chair", valueOf(1000d), FURNITURE),
            new Item("Soap", valueOf(50d), GROCERY));

    private static final List<Item> GROCERY_AND_MISC_ITEMS_60_DOLLARS = asList(
            new Item("Scissor", valueOf(50d), MISC),
            new Item("Soap", valueOf(10d), GROCERY));

    private static final List<Item> ALL_GROCERY_ITEMS_110_DOLLARS = asList(
            new Item("Bread", valueOf(50d), GROCERY),
            new Item("Soap", valueOf(50d), GROCERY),
            new Item("Milk", valueOf(10d), GROCERY));

    private static final List<Item> ALL_GROCERY_ITEMS_60_DOLLARS = asList(
            new Item("Soap", valueOf(50d), GROCERY),
            new Item("Milk", valueOf(10d), GROCERY));

    private static final DateTime date = new DateTime(2014, 5, 5, 0, 0, 0);

    private static final List<User> ALL_USERS = asList(
            new User(singletonList((Role) new EmployeeRole(true))),
            new User(singletonList((Role) new CustomerRole(date.toDate(), true))),
            new User(singletonList((Role) new AffiliateRole(true))));

    /**
     * Furniture: 1000$
     * Grocery: 50$
     * Total: 1050$
     * Discount: 0$ (No discount calculators supplied)
     */
    @Test
    public void shouldGenerateBillWithoutDiscount() {
        //given
        final ArrayList<DiscountCalculator> zeroDiscountCalculators = new ArrayList<DiscountCalculator>(0);
        Bill bill = new Bill(FURNITURE_AND_GROCERY_ITEMS_1050_DOLLARS);
        bill.setDiscountCalculators(zeroDiscountCalculators);

        //when
        BigDecimal finalBill = bill.getTotalValueAfterDiscount();

        //then
        assertTrue(valueOf(1050d).setScale(2, ROUND_CEILING).equals(finalBill));
    }

    /**
     * For all users.
     * <p/>
     * Grocery: 60$
     * Total: 60$
     * Discount: 0$
     * <p/>
     * Note: No Total value discount added as value is less than 100$ and No Percentage discount added as all items are grocery items.
     */
    @Test
    public void shouldNotApplyAnyDiscountForAllUsers() {
        for (User user : ALL_USERS) {
            //given
            Bill bill = new Bill(ALL_GROCERY_ITEMS_60_DOLLARS);

            final List<DiscountCalculator> perItemAndTotalValueDiscountCalculators = asList(new PercentageDiscountCalculator(user, bill.getItems()),
                    new TotalValueDiscountCalculator(user, bill.getTotalValueBeforeDiscount()));

            bill.setDiscountCalculators(perItemAndTotalValueDiscountCalculators);

            //when
            BigDecimal finalBill = bill.getTotalValueAfterDiscount();
            bill.setDiscountCalculators(perItemAndTotalValueDiscountCalculators);

            //then
            assertTrue(valueOf(60d).setScale(2, CEILING).equals(finalBill));
        }
    }

    /**
     * For all users
     * <p/>
     * Grocery: 110$
     * Total: 110$
     * Discount: 5$
     * <p/>
     * Note: Total value discount applied as amount more than 100$.
     */
    @Test
    public void shouldApplyOnlyTotalValueDiscountForAllUsers() {
        for (User user : ALL_USERS) {
            //given
            Bill bill = new Bill(ALL_GROCERY_ITEMS_110_DOLLARS);

            final List<DiscountCalculator> perItemAndTotalValueDiscountCalculators = asList(new PercentageDiscountCalculator(user, bill.getItems()),
                    new TotalValueDiscountCalculator(user, bill.getTotalValueBeforeDiscount()));

            bill.setDiscountCalculators(perItemAndTotalValueDiscountCalculators);

            //when
            BigDecimal finalBill = bill.getTotalValueAfterDiscount();

            //then
            assertTrue(valueOf(105d).setScale(2, ROUND_CEILING).equals(finalBill));
        }
    }

    /**
     * For employee
     * <p/>
     * Grocery: 10$
     * Misc: 50$
     * Total: 60$
     * Discount: 15$
     * <p/>
     * Note: Percentage discount applied on miscellaneous item. Total value discount not applied as total value is less than 100$.
     */
    @Test
    public void shouldApply30PercentDiscountForEmployee() {
        //given
        User employee = new User(singletonList((Role) new EmployeeRole(true)));

        Bill bill = new Bill(GROCERY_AND_MISC_ITEMS_60_DOLLARS);

        final List<DiscountCalculator> discountCalculators = asList(new PercentageDiscountCalculator(employee, bill.getItems()),
                new TotalValueDiscountCalculator(employee, bill.getTotalValueBeforeDiscount()));

        bill.setDiscountCalculators(discountCalculators);

        //when
        BigDecimal billAmount = bill.getTotalValueAfterDiscount();

        //then
        assertTrue(valueOf(45d).setScale(2, ROUND_CEILING).equals(billAmount));
    }

    /**
     * For Affiliate
     * <p/>
     * Grocery: 10$
     * Misc: 50$
     * Total: 60$
     * Discount: 5$
     * <p/>
     * Note: Percentage discount applied on miscellaneous item. Total value discount not applied as total value is less than 100$.
     */
    @Test
    public void shouldApply10PercentDiscountForAffiliate() {
        //given
        User affiliate = new User(singletonList((Role) new AffiliateRole(true)));
        Bill bill = new Bill(GROCERY_AND_MISC_ITEMS_60_DOLLARS);

        final List<DiscountCalculator> discountCalculators = asList(new PercentageDiscountCalculator(affiliate, bill.getItems()),
                new TotalValueDiscountCalculator(affiliate, bill.getTotalValueBeforeDiscount()));

        bill.setDiscountCalculators(discountCalculators);

        //when
        BigDecimal billAmount = bill.getTotalValueAfterDiscount();

        //then
        assertTrue(valueOf(55d).setScale(2, ROUND_CEILING).equals(billAmount));
    }

    /**
     * For Affiliate
     * <p/>
     * Grocery: 10$
     * Misc: 50$
     * Total: 60$
     * Discount: 2.5$
     * <p/>
     * Note: Percentage discount applied on miscellaneous item. Total value discount not applied as total value is less than 100$.
     */
    @Test
    public void shouldApply5PercentDiscountForCustomer() {
        //given
        User customer = new User(singletonList((Role) new CustomerRole(getMembershipDateTwoYearsOld(), true)));

        Bill bill = new Bill(GROCERY_AND_MISC_ITEMS_60_DOLLARS);

        final List<DiscountCalculator> discountCalculators = asList(new PercentageDiscountCalculator(customer, bill.getItems()),
                new TotalValueDiscountCalculator(customer, bill.getTotalValueBeforeDiscount()));

        bill.setDiscountCalculators(discountCalculators);

        //when
        BigDecimal billAmount = bill.getTotalValueAfterDiscount();

        //then
        assertTrue(valueOf(57.5).setScale(2, ROUND_CEILING).equals(billAmount));
    }

    /**
     * For Customer less than two years old.
     * <p/>
     * Furniture: 1000$
     * Grocery: 50$
     * Total: 1050$
     * Discount: 50$
     * <p/>
     * Note: Percentage discount not applied as customer less than two years old. Total value discount applied as value is greater than 100$.
     */
    @Test
    public void shouldApplyOnlyTotalValueDiscountForCustomerLessThanTwoYears() {
        //given
        User customer = new User(singletonList((Role) new CustomerRole(getMembershipDateOneYearOld(), true)));

        Bill bill = new Bill(FURNITURE_AND_GROCERY_ITEMS_1050_DOLLARS);

        final List<DiscountCalculator> discountCalculators = asList(new PercentageDiscountCalculator(customer, bill.getItems()),
                new TotalValueDiscountCalculator(customer, bill.getTotalValueBeforeDiscount()));

        bill.setDiscountCalculators(discountCalculators);

        //when
        BigDecimal billAmount = bill.getTotalValueAfterDiscount();

        //then
        assertTrue(valueOf(1000d).setScale(2, ROUND_CEILING).equals(billAmount));
    }

    /**
     * For Customer more than two years old.
     * <p/>
     * Furniture: 1000$
     * Grocery: 50$
     * Total: 1050$
     * Discount: 100$
     * <p/>
     * Note: Percentage discount applied as customer more than two years old. Total value discount applied as value is greater than 100$.
     */
    @Test
    public void shouldApply5PercentDiscountAndTotalValueDiscountForCustomerMoreThanTwoYears() {
        //given
        User customer = new User(singletonList((Role) new CustomerRole(getMembershipDateTwoYearsOld(), true)));

        Bill bill = new Bill(FURNITURE_AND_GROCERY_ITEMS_1050_DOLLARS);

        final List<DiscountCalculator> discountCalculators = asList(new PercentageDiscountCalculator(customer, bill.getItems()),
                new TotalValueDiscountCalculator(customer, bill.getTotalValueBeforeDiscount()));

        bill.setDiscountCalculators(discountCalculators);

        //when
        BigDecimal finalBill = bill.getTotalValueAfterDiscount();

        //then
        assertTrue(valueOf(950d).setScale(2, ROUND_CEILING).equals(finalBill));
    }

    /**
     * If the user is both Customer and Employee, apply discount rules for only configured roles.
     */
    @Test
    public void shouldApplyOnlySinglePercentageDiscount() {
        //given
        User customer = new User(asList(new CustomerRole(getMembershipDateTwoYearsOld(), false), new EmployeeRole(true)));

        Bill bill = new Bill(FURNITURE_AND_GROCERY_ITEMS_1050_DOLLARS);

        final List<DiscountCalculator> discountCalculators = asList(new PercentageDiscountCalculator(customer, bill.getItems()),
                new TotalValueDiscountCalculator(customer, bill.getTotalValueBeforeDiscount()));

        bill.setDiscountCalculators(discountCalculators);
        //when
        BigDecimal finalBill = bill.getTotalValueAfterDiscount();

        //then
        assertTrue(valueOf(650d).setScale(2, ROUND_CEILING).equals(finalBill));
    }

    private Date getMembershipDateOneYearOld() {
        return getMembershipDate(2015, Calendar.JUNE, 1);
    }

    private Date getMembershipDate(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return calendar.getTime();
    }

    private Date getMembershipDateTwoYearsOld() {
        return getMembershipDate(2014, Calendar.JUNE, 1);
    }
}