package modules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import payload.Bookingdata;
import payload.Bookingdates;



public class PayloadManager {

    ObjectMapper objectmapper;

        public  String createpayload () throws JsonProcessingException {

        objectmapper = new ObjectMapper();

        Bookingdata bookingdata = new Bookingdata();
        bookingdata.setFirstname("Sriman");
        bookingdata.setLastname("Sree");
        bookingdata.setTotalprice(76.78f);
        bookingdata.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2020-01-01");
        bookingdates.setCheckout("2020-01-02");
        bookingdata.setBookingdates(bookingdates);
        bookingdata.setAdditionalneeds("Breakfast");

        String payload = objectmapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingdata);
        System.out.println(payload);
        return payload;


    }




    public  String updatepayload () throws JsonProcessingException {

        objectmapper = new ObjectMapper();

        Bookingdata bookingdata = new Bookingdata();
        bookingdata.setFirstname("Narendra");
        bookingdata.setLastname("Suddala");
        bookingdata.setTotalprice(96.76f);
        bookingdata.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("1996-11-28");
        bookingdates.setCheckout("1996-11-29");
        bookingdata.setBookingdates(bookingdates);
        bookingdata.setAdditionalneeds("LUNCH");

        String payload = objectmapper.writerWithDefaultPrettyPrinter().writeValueAsString(bookingdata);
        System.out.println(payload);
        return payload;


    }
}
