package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {
        try{
            System.out.print("Enter filename = ");
            Scanner scanner = new Scanner(System.in);
            String filename = scanner.nextLine();

            if(!filename.contains(".txt"))
                throw new Exception("Error file format");

            Lang lang = new Lang(filename);
            lang.findNonReachableNeterminals();
        }
        catch (Exception ex){
            System.out.println("EXCEPTION: " + ex.getMessage());
        }
    }
}
