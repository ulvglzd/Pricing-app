/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pricing.helper;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.json.JSONObject;


/**
 *
 * @author Ulvi
 */
public class PriceConverter implements Serializable{

    private static final long serialVersionUID = 1L;
    private JSONObject json;
     
    
    private JSONObject getJsonResponse() throws MalformedURLException, IOException{
        URL url = new URL("https://v6.exchangerate-api.com/v6/64e1188f748e6861e8ba9811/latest/USD");
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        InputStream inputStream = (InputStream)request.getContent();
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        JSONObject json = new JSONObject(text);
        return json;
    }
    
    
    public Double convertPriceFromDollar(String currency) throws IOException{
        
        JSONObject rates = getJsonResponse().getJSONObject("conversion_rates");
        Double conversionRate = rates.getDouble(currency.toUpperCase());
       
        return conversionRate;
    }
    
    
    
    
}
