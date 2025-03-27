package utility;

import org.testng.annotations.DataProvider;
import payload.Bookingdata;
import payload.Bookingdates;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviderUtil {

    @DataProvider(name = "BookingDataProvider")
    public static Object[][] getBookingData() throws IOException {

        String excelFilePath = "src/test/resources/createbookingtestdata.xlsx"; // Update the path
        ExcelUtil excel = new ExcelUtil(excelFilePath, "Sheet1"); // Update sheet name
        int rowCount = excel.getRowCount();

        List<Bookingdata> bookingList = new ArrayList<>();

        for (int i = 1; i <= rowCount; i++) { // Skipping header (start from 1)
            Bookingdates bookingdates = new Bookingdates();
            bookingdates.setCheckin(excel.getCellData(i, 4));
            bookingdates.setCheckout(excel.getCellData(i, 5));

            Bookingdata bookingdata = new Bookingdata();
            bookingdata.setFirstname(excel.getCellData(i, 0));
            bookingdata.setLastname(excel.getCellData(i, 1));
            //bookingdata.setTotalprice((float) Double.parseDouble(excel.getCellData(i, 2)));
            bookingdata.setTotalprice(Float.parseFloat(excel.getCellData(i, 2)));
            bookingdata.setDepositpaid(Boolean.parseBoolean(excel.getCellData(i, 3)));
            bookingdata.setBookingdates(bookingdates);
            bookingdata.setAdditionalneeds(excel.getCellData(i, 6));

            bookingList.add(bookingdata);

            System.out.println ("++++++++++++=====================++++++++++++++++++++");
            System.out.println("Raw Data from Excel: " + excel.getCellData(i, 2));
            System.out.println("Parsed Float Value: " + Float.parseFloat(excel.getCellData(i, 2)));

        }

        excel.closeWorkbook();

        // Convert List to Object[][] for TestNG DataProvider
        Object[][] data = new Object[bookingList.size()][1];
        for (int i = 0; i < bookingList.size(); i++) {
            data[i][0] = bookingList.get(i);
        }
        return data;
    }
}
