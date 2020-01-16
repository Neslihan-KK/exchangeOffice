package com.exchangeoffice.exchange.controller;

import com.exchangeoffice.exchange.model.CurrencyList;
import com.exchangeoffice.exchange.model.ExchangeRates;
import com.exchangeoffice.exchange.model.ExchangeRatesSearch;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

@Controller
public class MainController {

    // essential URL structure is built using constants
    public static final String ACCESS_KEY = "525a696fe18ee4865f993997062d6e7f";
    public static final String BASE_URL = "http://apilayer.net/api/";

    public Double sendLiveRequest(String endpoint, String url, String fromCurrency, String toCurrency){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(BASE_URL + endpoint + "?access_key=" + ACCESS_KEY + url);
        Double exchangeRate = 0.0;

        try {
            CloseableHttpResponse response =  httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            Double fromCurrencyRate = exchangeRates.getJSONObject("quotes").getDouble(exchangeRates.getString("source")+""+fromCurrency);
            Double toCurrencyRate = exchangeRates.getJSONObject("quotes").getDouble(exchangeRates.getString("source")+""+toCurrency);
            exchangeRate = toCurrencyRate/fromCurrencyRate;
            response.close();
            httpClient.close();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return exchangeRate;
    }

    public List<ExchangeRates> sendHistoricalRequest(String endpoint, String url){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(BASE_URL + endpoint + "?access_key=" + ACCESS_KEY + url);
        List<ExchangeRates> exchangeRates2 = new ArrayList<>();

        try {
            CloseableHttpResponse response =  httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject exchangeRates = new JSONObject(EntityUtils.toString(entity));

            for (int i = 0; i<exchangeRates.getJSONObject("quotes").length(); i++) {
                ExchangeRates exchangeRates1 = new ExchangeRates();
                exchangeRates1.setFromCurrency(exchangeRates.getString("source"));
                exchangeRates1.setToCurrency(exchangeRates.getJSONObject("quotes").names().get(i).toString().substring(3));
                exchangeRates1.setExchangeRate(exchangeRates.getJSONObject("quotes").getDouble(exchangeRates.getJSONObject("quotes").names().get(i).toString()));
                exchangeRates2.add(exchangeRates1);
            }
            response.close();
            httpClient.close();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return exchangeRates2;
    }

    public List<CurrencyList> currencyList(String endpoint){

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(BASE_URL + endpoint + "?access_key=" + ACCESS_KEY);
        List<CurrencyList> currencyList2 = new ArrayList<>();

        try {
            CloseableHttpResponse response =  httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONObject currencyList = new JSONObject(EntityUtils.toString(entity));

            for (int i = 0; i<currencyList.getJSONObject("currencies").length(); i++) {
                CurrencyList currencyList1 = new CurrencyList();
                currencyList1.setCurrency(currencyList.getJSONObject("currencies").names().get(i).toString());
                currencyList1.setCurrencyLabel(currencyList.getJSONObject("currencies").getString(currencyList.getJSONObject("currencies").names().get(i).toString()));
                currencyList2.add(currencyList1);
            }
            response.close();
            httpClient.close();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return currencyList2;
    }

    @RequestMapping(value = "/exchangerateslive", method = RequestMethod.GET)
    public String exchangeRatesLive(Model model) throws IOException{
        model.addAttribute("currencyList", currencyList("list"));
        model.addAttribute("exchangeRatesSearch", new ExchangeRatesSearch());
        return "exchangerateslive";
    }
    @RequestMapping(value = "searchLive", method = RequestMethod.POST)
    public String exchangeRatesLive1(ExchangeRatesSearch exchangeRatesSearch, Model model) throws IOException{
        Double exchangeRate = sendLiveRequest("live", "", exchangeRatesSearch.getFromCurrency(), exchangeRatesSearch.getToCurrency());
        exchangeRatesSearch.setExchangeRate(exchangeRate);
        model.addAttribute("currencyList", currencyList("list"));
        model.addAttribute("exchangeRatesSearch", exchangeRatesSearch);
        return "exchangerateslive";
    }

    @RequestMapping(value = "/exchangeratesconvert", method = RequestMethod.GET)
    public String exchangeRatesConvert(Model model) throws IOException{
        model.addAttribute("currencyList", currencyList("list"));
        model.addAttribute("exchangeRatesSearch", new ExchangeRatesSearch());
        return "exchangeratesconvert";
    }
    @RequestMapping(value = "searchConvert", method = RequestMethod.POST)
    public String exchangeRatesConvert1(ExchangeRatesSearch exchangeRatesSearch, Model model) throws IOException{
        Double exchangeRate = sendLiveRequest("live", "", exchangeRatesSearch.getFromCurrency(), exchangeRatesSearch.getToCurrency());
        exchangeRatesSearch.setToAmount(exchangeRate * exchangeRatesSearch.getFromAmount());
        model.addAttribute("currencyList", currencyList("list"));
        model.addAttribute("exchangeRatesSearch", exchangeRatesSearch);
        return "exchangeratesconvert";
    }

    @RequestMapping(value = "/exchangerateshistory", method = RequestMethod.GET)
    public String exchangeRatesHistory(Model model) throws IOException{
        model.addAttribute("exchangeRatesSearch", new ExchangeRatesSearch());
        return "exchangerateshistory";
    }

    @RequestMapping(value = "searchHistorical", method = RequestMethod.POST) //@RequestParam("exchangeDate") String date
    public String exchangeRatesHistory1(ExchangeRatesSearch exchangeRatesSearch, Model model) throws IOException{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(exchangeRatesSearch.getExchangeDate());
        List<ExchangeRates> exchangeRates = sendHistoricalRequest("historical", "&date="+formattedDate);
        model.addAttribute("exchangeRates", exchangeRates);
        model.addAttribute("exchangeRatesSearch", exchangeRatesSearch);
        return "exchangerateshistory";
    }

}
