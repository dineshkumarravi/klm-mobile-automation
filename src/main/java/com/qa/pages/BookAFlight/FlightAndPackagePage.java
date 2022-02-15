package com.qa.pages.BookAFlight;

import com.qa.BaseTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Choose Flight and package page class method implementation using page factory.
 * @author Dineshkumar.
 *
 */
public class FlightAndPackagePage extends BaseTest {

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/flight_list_recycler_view")
    private MobileElement flightList;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Choose your package\")")
    private MobileElement txtChoosePackage;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/name")
    private MobileElement packageName;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/branded_fares_select_button")
    private MobileElement selectButton;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/trip_origin")
    private MobileElement txtOrigin;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/trip_destination")
    private MobileElement txtDestination;

    /**
     * Find minimum flight price by compare with flight prices list for choose the cheapest flight .
     *
     */
    public FlightAndPackagePage chooseCheapestOutboundFlight() {
        //get all the prices from the flight list
        waitForVisibility(flightList);
        List flgList = flightList.findElements(By.className("android.view.ViewGroup"));
        System.out.println(flgList.size());
        //put all the prices into array list and get the lowest prices
        ArrayList<Integer> flightPrices = new ArrayList<Integer>();
        for (int i = 2; i < flgList.size() - 1; i++) {
            MobileElement ele = (MobileElement) flgList.get(i);
            MobileElement eleflist = ele.findElement(By.className("android.view.ViewGroup"));
            MobileElement fprice = (MobileElement) eleflist.findElement(By.xpath("//android.widget.TextView[@resource-id='com.afklm.mobile.android.gomobile.klm:id/flight_card_price']"));
            String[] flightPrice = getText(fprice).split(" ");
            Integer fpriceInt = Integer.valueOf(flightPrice[1]);
            System.out.println("Prices are " + fpriceInt);
            flightPrices.add(fpriceInt);
        }

        Integer minPrice = Collections.min(flightPrices);
        System.out.println("Min Flight Price in GBP is " + minPrice);

        //compare all the prices with lowest price and select the cheapest flight
        for (int i = 2; i < flgList.size() - 1; i++) {
            MobileElement ele = (MobileElement) flgList.get(i);
            MobileElement eleflist = ele.findElement(By.className("android.view.ViewGroup"));
            MobileElement fprice = (MobileElement) eleflist.findElement(By.xpath("//android.widget.TextView[@resource-id='com.afklm.mobile.android.gomobile.klm:id/flight_card_price']"));
            String[] flightPrice = getText(fprice).split(" ");
            Integer fpriceInt = Integer.valueOf(flightPrice[1]);
            if (fpriceInt.equals(minPrice)) {
                System.out.println("Prices are " + fpriceInt);
                click(eleflist);
                break;
            }
        }
        return this;
    }

    /**
     * Choose the package based on the package name provided from test .
     * @param Package Package name from test.
     */

    public FlightAndPackagePage choosePackage(String Package) {
        waitForVisibility(txtChoosePackage);
        swipeVertical();
        if (getText(packageName).equals(Package)) {
            click(selectButton);
        }
        return this;
    }

    /**
     * Get the origin details on passenger page .
     * @return this origin name.
     */
    public String getOriginDetails() {
        waitForVisibility(txtOrigin);
        String origin = getText(txtOrigin);
        return origin;
    }

    /**
     * Get the destination details on passenger page .
     * @return this destination name.
     */
    public String getDestinationDetails() {
        String destination = getText(txtDestination);
        return destination;
    }
}
