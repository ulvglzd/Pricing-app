/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package price;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;



/**
 * REST Web Service
 *
 * @author Ulvi
 */
@Path("dailyPriceChange")
@RequestScoped
public class DailyPriceChangeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DailyPriceChangeResource
     */
    public DailyPriceChangeResource() {
    }

    /**
     * Retrieves representation of an instance of price.DailyPriceChangeResource
     * @return an instance of java.lang.Integer
     */
    @GET
    @Produces(jakarta.ws.rs.core.MediaType.TEXT_PLAIN)
    public Integer getText() {
        int priceChange;
        Calendar now = GregorianCalendar.getInstance();
        int weekOfMonth = now.get(Calendar.WEEK_OF_MONTH);
        int randomSeedValue = 0;
        
        switch (weekOfMonth){
            case 1:
                randomSeedValue = 1;
                break;
            case 2:
                randomSeedValue = 2;
                break;
            case 3:
                randomSeedValue = 3;
                break;
            case 4:
                randomSeedValue = 4;
                break;
        }
        
        Random random = new Random(randomSeedValue);
        
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        
        switch (dayOfWeek){
            case 2:
            case 3:
                priceChange = random.nextInt(1,6);
                break;
            case 4:
                priceChange = random.nextInt(1,4) * -1;
                break;
            case 5:
            case 6:
                priceChange = random.nextInt(1,3);
                break;
            case 7:
                priceChange = random.nextInt(1,6) * -1;
                break;
            default:
                priceChange = 0;
                break;
        }
                
        
        return priceChange;
    }

    /**
     * PUT method for updating or creating an instance of DailyPriceChangeResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(jakarta.ws.rs.core.MediaType.TEXT_PLAIN)
    public void putText(Integer content) {
    }
}
