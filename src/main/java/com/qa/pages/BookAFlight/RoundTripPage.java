package com.qa.pages.BookAFlight;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import java.util.List;

public class RoundTripPage extends BaseTest {

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/origin_field")
    private MobileElement originField;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/search_src_text")
    private MobileElement placeSearchField;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/first_line_text")
    private MobileElement selectPlace;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/destination_field")
    private MobileElement destinationField;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/cabin_selection_field")
    private MobileElement selectCabin;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/list_dialog_item_text")
    private List<MobileElement> selectTravelClass;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/add_passenger_card")
    private MobileElement btnAddPassenger;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/passenger_selection_text")
    private List<MobileElement> passengerGroup;

    @AndroidFindBy(className = "android.widget.ImageButton")
    private List<MobileElement> btnAddPassengerGroup;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/pax_selection_footer_confirm_button")
    private MobileElement btnContinue;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/button_confirm")
    private MobileElement btnChooseDates;

    @AndroidFindBy(id = "com.afklm.mobile.android.gomobile.klm:id/calendar_footer_confirm_button")
    private MobileElement btnConfirmDates;

    public RoundTripPage enterOrigin(String origin) {
        clear(originField);
        click(originField);
        sendKeys(placeSearchField, origin);
        if (getText(selectPlace).equals(origin))
            click(selectPlace);
        return this;
    }

    public RoundTripPage enterDestination(String destination) {
        clear(destinationField);
        click(destinationField);
        sendKeys(placeSearchField, destination);
        if (getText(selectPlace).equals(destination))
            click(selectPlace);
        return this;
    }

    public RoundTripPage selectTravelClass(String travelClass) {
        click(selectCabin);
        for (MobileElement selectTravel : selectTravelClass) {
            if (getText(selectTravel).equals(travelClass)) {
                click(selectTravel);
                break;
            }
        }
        return this;
    }

    public RoundTripPage addPassengers(String passengerGroupName, String pCount) {
        int count = Integer.parseInt(pCount);
        click(btnAddPassenger);
        for (int i = 0; i < passengerGroup.size(); i++) {
            if (passengerGroup.get(i).getText().contains(passengerGroupName)) {
                for (int j = 1; j <= count; j++) {
                    click(btnAddPassengerGroup.get(i * 2 + 2));
                }
            }
        }
        return this;
    }

    public RoundTripPage clickContinue() {
        click(btnContinue);
        return this;
    }

    public RoundTripPage chooseDepartureDate(String daysToAdvanceData) {
        int daysToAdvance = Integer.parseInt(daysToAdvanceData);
        click(btnChooseDates);
        int date = TestUtils.getNextDate(daysToAdvance);
        List departureElement = driver.findElements(By.xpath("//android.widget.TextView[@text='" + date + "']"));
        click((MobileElement) departureElement.get(0));
        return this;
    }

    public RoundTripPage chooseReturnDate(String daysToAdvanceData) {
        int daysToAdvance = Integer.parseInt(daysToAdvanceData);
        int date = TestUtils.getNextDate(daysToAdvance);
        List returnDateElement = driver.findElements(By.xpath("//android.widget.TextView[@text='" + date + "']"));
        click((MobileElement) returnDateElement.get(0));
        click(btnConfirmDates);
        return this;
    }
}
