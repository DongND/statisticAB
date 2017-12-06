package samples.employeedirectory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 11/18/2017.
 */

public class Utils {

    private static Map<String, Integer> mapDefinition;

    public static void init(){
        Map<String, Integer> mapInitial = new HashMap<String, Integer>();
        mapInitial.put("AAAA",1);
        mapInitial.put("AAAB",2);
        mapInitial.put("AABA",3);
        mapInitial.put("AABB",4);
        mapInitial.put("ABAA",5);
        mapInitial.put("ABAB",6);
        mapInitial.put("ABBA",7);
        mapInitial.put("ABBB",8);
        mapInitial.put("BAAA",9);
        mapInitial.put("BAAB",10);
        mapInitial.put("BBAA",11);
        mapInitial.put("BBAB",12);
        mapInitial.put("BBBA",13);
        mapInitial.put("BBBB",14);
        mapInitial.put("BABA",15);
        mapInitial.put("BABB",16);

        mapDefinition = mapInitial;
    }

    /**
     * Convert from list String with One Char to List with Four Chars
     * @param raw
     * @return
     */
    public static List<String> fromRawToCombine(List<String> raw, int number){
        List<String> ret = new ArrayList<String>();
        if (raw != null && !raw.isEmpty() && raw.size() >=number){
            String initElement = "";

            for (int i=0 ; i<number; i++){

                initElement += raw.get(i);
            }

            ret.add(initElement);
            for (int i=number; i <raw.size(); i ++){
                String nextElement = getNext(initElement, raw.get(i));
                ret.add(nextElement);
                initElement = nextElement;
            }
        }
        return ret;
    }

    /**
     * Calculate Percentage for Layer1
     * @param listInput
     * @return
     */
    public static Map<String, Long> calculatePercentage( List<String> listInput){

        Map<String, Long> ret = new HashMap<String, Long>();


        List<String> listCombine5 = fromRawToCombine(listInput,5);
        List<String> listCombine6 = fromRawToCombine(listInput,6);
        List<String> listCombine7 = fromRawToCombine(listInput,7);
        String last4A = getLastInput(listInput, 4) + "A";
        String last5A = getLastInput(listInput, 5) + "A";
        String last6A = getLastInput(listInput, 6) + "A";

        String last4B = getLastInput(listInput, 4) + "B";
        String last5B = getLastInput(listInput, 5) + "B";
        String last6B = getLastInput(listInput, 6) + "B";

        int count4A = 0;
        int count4B = 0;
        int count5A = 0;
        int count5B = 0;
        int count6A = 0;
        int count6B = 0;
        for( String element : listCombine5){
            if (element.equalsIgnoreCase(last4A)){
                count4A +=1;
            } else if (element.equalsIgnoreCase(last4B)){
                count4B +=1;
            }
        }
        ret.put("1A", round((double)count4A/ (listCombine5.size()) * 100 ));
        ret.put("1B", round((double)count4B/ (listCombine5.size()) * 100 ));

        for( String element : listCombine6){
            if (element.equalsIgnoreCase(last5A)){
                count5A +=1;
            } else if (element.equalsIgnoreCase(last5B)){
                count5B +=1;
            }
        }
        ret.put("2A", round((double)count5A/ (listCombine6.size()) * 100 ));
        ret.put("2B", round((double)count5B/ (listCombine6.size()) * 100 ));

        for( String element : listCombine7){
            if (element.equalsIgnoreCase(last6A)){
                count6A +=1;
            } else if (element.equalsIgnoreCase(last6B)){
                count6B +=1;
            }
        }

        ret.put("3A", round((double)count6A/ (listCombine7.size()) * 100 ));
        ret.put("3B", round((double)count6B/ (listCombine7.size()) * 100 ));


        return ret;
    }


    public static String getNext(String input, String nextChar){
        String ret = input;
        if (input != null){
            int len = input.length();
            if (len>=1){

                String s = input.substring(1,len);
                ret = s + nextChar;
            }
        }

        return ret;
    }


    public static String getLastInput(List<String> lstInput, int number){
        String ret = "";
        if (lstInput !=null && !lstInput.isEmpty()){

            int startIndex = 0;
            if (lstInput.size() >= number){
                startIndex = lstInput.size() - number;
            }
            for (int i= startIndex ; i<lstInput.size(); i++){
                ret += lstInput.get(i);
            }

        }
        return ret;
    }

    public static String buildTextDisplay (List<String> listInput){
        init();
        String textDisplay = Utils.getLastInput(listInput, 4);
        Integer meaningText = (Integer) mapDefinition.get(textDisplay);
        if(meaningText != null) {
            textDisplay = meaningText + " (" + textDisplay  + ")";
        }

        return textDisplay;
    }

    public static List<String> getSublist(List<String> input, int startIndex, int endIndex){
        List<String> ret = new ArrayList<String>();
        if (input != null && !input.isEmpty()){
            for (int i = startIndex; i < endIndex; i++){
                ret.add(input.get(i));
            }
        }
        return ret;
    }


    public static long round(Double input){
        return Math.round(input);
    }
}
