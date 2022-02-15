package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.BookAFlight.FlightAndPackagePage;
import com.qa.pages.BookAFlight.RoundTripPage;
import com.relevantcodes.extentreports.LogStatus;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import java.io.IOException;
import java.lang.reflect.Method;

public class BookAFlightTests extends BaseTest {

    RoundTripPage roundTripPage;
    FlightAndPackagePage flightAndPackagePage;
    JSONObject flightBookDetails;

    @BeforeMethod
    public void beforeMethod(Method m) throws IOException {
        roundTripPage = new RoundTripPage();
        flightAndPackagePage = new FlightAndPackagePage();
        String dataFileName = "data/BookAFlightTests.json";
        flightBookDetails = BaseTest.getData(dataFileName).getJSONObject("bookAFlight");
    }

    @Test
    public void bookAFlight() {
            extentTest = extent.startTest("Book a flight and skip payment");
            roundTripPage.enterOrigin(flightBookDetails.getString("origin"));
            extentTest.log(LogStatus.INFO, "Select origin in Round Trip");
            roundTripPage.enterDestination(flightBookDetails.getString("destination"));
            extentTest.log(LogStatus.INFO, "Select destination in Round Trip");
            roundTripPage.selectTravelClass("Premium Economy");
            extentTest.log(LogStatus.INFO, "Select Premium Economy class");
            roundTripPage.addPassengers(flightBookDetails.getString("passengerGroupName"), flightBookDetails.getString("passengersCount"));
            extentTest.log(LogStatus.INFO, "Add two adults ");
            roundTripPage.clickContinue()
                         .chooseDepartureDate(flightBookDetails.getString("departureDateInDaysToAdvance"));
            extentTest.log(LogStatus.INFO, "Choose travel dates");
            roundTripPage.chooseReturnDate(flightBookDetails.getString("returnDateInDaysToAdvance"));
            flightAndPackagePage.chooseCheapestOutboundFlight();
            extentTest.log(LogStatus.INFO, "Choose the cheapest flight for origin");
            flightAndPackagePage.choosePackage(flightBookDetails.getString("packageName"));
            extentTest.log(LogStatus.INFO, "Choose the standard package for origin");
            flightAndPackagePage.chooseCheapestOutboundFlight();
            extentTest.log(LogStatus.INFO, "Choose the cheapest flight for return");
            flightAndPackagePage.choosePackage(flightBookDetails.getString("packageName"));
            extentTest.log(LogStatus.INFO, "Choose the standard package for return");
            Assert.assertEquals(flightAndPackagePage.getOriginDetails(), flightBookDetails.getString("origin"));
            Assert.assertEquals(flightAndPackagePage.getDestinationDetails(), flightBookDetails.getString("destination"));
            extentTest.log(LogStatus.PASS, " Booked a flight with Origin as " + flightBookDetails.getString("origin") + " and destination as " + flightBookDetails.getString("destination"));
    }
}
