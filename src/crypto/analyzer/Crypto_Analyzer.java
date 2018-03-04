package crypto.analyzer;

import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.text.DecimalFormat;

public class Crypto_Analyzer {

    public static void main(String[] args) throws IOException{
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
        
        double coffeePrice = 5.00;
        
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
        
        PrintWriter writer = new PrintWriter("File.txt", "UTF-8");

        for(int i = 0; i < cryptos.length; i++){
            writer.println(cryptos[i] + ": " + crypto_val[i] * USD_to_CAD + " CAD");
            writer.println("Coffee: " + coffeePrice/(crypto_val[i] * USD_to_CAD) + " " + cryptos[i]);
        }
        writer.close();
        
    }
    
}
