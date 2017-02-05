package com.strywn.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntryPoint {
	
	private final static String INVALID_INPUT_INT_MESSAGE = "Argument entry not valid, try something in the range from 0 - 9.";
	private final static String INVALID_INPUT_STRING_MESSAGE = "Argument entry not valid, try with a non empty string.";
	private final static String INVALID_INPUT_INT_STRING_MESSAGE = "The number entered for evaluation %s is larger than 2147483647, program currently handling just integers.";
 
    private static List<String> extractElements(String inputString) {
        
        inputString = inputString.replaceAll("[^\\sA-Za-z0-9]", "");
        String[] stringArray = inputString.split("[\\s]+");        
        
        return Arrays.asList(stringArray);        
    }
    
    private static List<String> generateIntegerList(List<String> elements) {
        
        List<String> integers = new ArrayList<String>();
        elements.forEach(element -> {
        	String integer = checkForInteger(element);
            if(!"".equals(integer)) {                
                integers.add(integer);
            }
        });
        
        return integers;
    }
    
    private static String checkForInteger(String element) {
        
        element = element.replaceAll("[^0-9]", "");
        
        return element;
    }
    
    private static List<String> getMultiplesFromIntegerList(List<String> generateIntegerList, int inputInt) {
        
    	List<String> integers = generateIntegerList(generateIntegerList);
        List<String> multiples = new ArrayList<String>();
        integers.forEach(integer -> {
            Integer evaluateInteger = checkIfMultiple(integer, inputInt);
            if(evaluateInteger.intValue() != -1) {
                multiples.add(evaluateInteger.toString());
            }
        });
        
        return multiples;
    }
    
    private static Integer checkIfMultiple(String integer, int inputInt) {        
        
        Integer multiple = -1;
        StringBuilder numberTracker = new StringBuilder();
        for(int i = 0; i < integer.length(); i++) {
            numberTracker.append(integer.charAt(i));
            try {
            	Integer currentInteger = new Integer(numberTracker.toString());
            	if(currentInteger % inputInt == 0) {
                    multiple = new Integer(numberTracker.toString());
                }
            } catch (NumberFormatException exception) {
            	String message = String.format(INVALID_INPUT_INT_STRING_MESSAGE, integer);
            	System.out.println(message);
            	multiple = -1;
            	break;
            }            
        }
        
        return multiple;
    }
    
    private static List<String> generatePalindromeList(List<String> elements) {
        
        List<String> palindromes = new ArrayList<String>();
        elements.forEach(element -> {
            if(!"".equals(element) && checkForPalindrome(element)) {
                palindromes.add(element.toUpperCase());
            }
        });
        
        return palindromes;        
    }
    
    private static boolean checkForPalindrome(String element) {
    	
    	if(element.length() == 1) {
    		return false;
    	}
        
        int lastPosition = element.length() - 1;
        int middlePosition = element.length() / 2;
        for(int i = 0; i < middlePosition; i++, lastPosition--) {
            if(element.charAt(i) != element.charAt(lastPosition)) {
                return false;
            }                
        }
        
        return true;
    }
    
    private static String[] handleRequest(int inputInt, String inputString) {
    	
    	if(inputInt < 0 || inputInt > 9) {
    		throw new IllegalArgumentException(INVALID_INPUT_INT_MESSAGE);
    	}
    	
    	if(inputString.length() == 0) {
    		throw new IllegalArgumentException(INVALID_INPUT_STRING_MESSAGE);
    	}
        
        List<String> response = new ArrayList<String>();        
        List<String> elements = extractElements(inputString);
        response.addAll(getMultiplesFromIntegerList(elements, inputInt));
        response.addAll(generatePalindromeList(elements));        
      
        return response.stream().toArray(String[] :: new);
    }
    
    public static void testFunction(int inputInt, String inputString) {
    	
    	String[] response = handleRequest(inputInt, inputString);
     	StringBuilder printResponseArray = new StringBuilder();
 
    	int arraySize = response.length - 1;
    	printResponseArray.append("[");
    	for(int i = 0; i <= arraySize;  i++) {
    		printResponseArray.append(response[i]);
    		if(i < arraySize) {
    			printResponseArray.append(", ");
    		}
    	}
    	printResponseArray.append("]");
    	
    	System.out.println(printResponseArray.toString());
    }

    public static void main(String[] args) {
        
        testFunction(8, "ANNA BAKES 80 CAKES IN THE NOON, 989216011");
        //testFunction(3, "  avanava 300   ");
        //testFunction(1, "dhjkwhsd jhdsehd ihdiuheu3hd njhw 5658390281 2147483647 2147483648");
        //testFunction(-10, "ANNAf BAKES 80 CAKES IN THE NOONjj, 989216011");
        //testFunction(0, ""); 
    	//testFunction(1, "a");
    }

}
