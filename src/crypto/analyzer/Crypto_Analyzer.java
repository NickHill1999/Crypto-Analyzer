/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto.analyzer;

import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.text.DecimalFormat;
/**
 *
 * @author Nikhil
 */

public class Crypto_Analyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String cryptos[] = {"bitcoin","ethereum","ripple","litecoin","dogecoin"};
        double crypto_val[] = new double[cryptos.length];
        DecimalFormat cash = new DecimalFormat("$#,###,##0.00####");
        URL url = new URL("https://coinmarketcap.com/currencies/bitcoin/");
        URLConnection urlConn = url.openConnection();
        InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
        BufferedReader buff = new BufferedReader(inStream);
        
        double price = 0;
        int start = 0;
        int end = 0;
        
        //finding crypto values
        for(int i = 0; i < cryptos.length; i++){
            url = new URL("https://coinmarketcap.com/currencies/" + cryptos[i] +  "/");
            urlConn = url.openConnection();
            inStream = new InputStreamReader(urlConn.getInputStream());
            buff = new BufferedReader(inStream);
            String line = buff.readLine();
            while(line!=null){
                if(line.contains("data-currency-value>")){
                    end = line.indexOf("/") - 1;
                    start = line.indexOf(">") + 1;
                    price = Double.parseDouble(line.substring(start, end));
                    break;
                }
                line = buff.readLine();
            }
            crypto_val[i] = price;
        }
        
        //class='uccResultAmount
        url = new URL("http://www.xe.com/currencyconverter/convert/?Amount=1&From=USD&To=CAD");
        urlConn = url.openConnection();
        inStream = new InputStreamReader(urlConn.getInputStream());
        buff = new BufferedReader(inStream);
        String line = buff.readLine();
        while(line!=null){
            if(line.contains("class='uccResultAmount")){
                end = line.indexOf("</span><span class='uccToCurrencyCode'>") - 1;
                start = line.indexOf("<span class='uccResultAmount'>") + "<span class='uccResultAmount'>".length();
                price = Double.parseDouble(line.substring(start, end));
                break;
            }
            line = buff.readLine();
        }
        
        double USD_to_CAD = price;
        System.out.println(USD_to_CAD);
        
        
        for(int i = 0; i < cryptos.length; i++){
            System.out.println(cryptos[i] + ": " + crypto_val[i] * USD_to_CAD + " CAD");
        }
        
    }
}
