package com.company;

import java.io.*;
import java.util.*;

public class Lang {
    private final Map<Character, List<String>> grammar;

    public Lang(String filename){
        grammar = new LinkedHashMap<>();
        readFromFile(filename);
    }

    private void readFromFile(String filename){
        try {
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                splitRule(line);
                line = reader.readLine();
            }

            System.out.println(grammar);

        } catch (Exception e) {
            System.out.println("Exception! " + e.getMessage());
        }
    }

    public void splitRule(String line) throws Exception {
        List<String> parts = Arrays.asList(line.split("->"));
        if(parts.size() != 2)
            throw new Exception("Error in rule");

        String[] rules = parts.get(1).split("\\|");

        Character nonTerm = parts.get(0).charAt(0);
        grammar.put(nonTerm, new ArrayList<>());
        for(String rule : rules){
            rule = rule.replaceAll("\\s+","");
            grammar.get(nonTerm).add(rule);
        }
    }

    public void findNonReachableNeterminals(){
        List<Character> reachNeterminals = new ArrayList<>();
        List<Character> keys = new ArrayList<>(grammar.keySet());

        reachNeterminals.add(keys.get(0)); // axioma

        for(int i = 0; i < reachNeterminals.size(); i++){
            Character key = reachNeterminals.get(i);

            if(grammar.containsKey(key))
                for(String rule : grammar.get(key))
                    for(int j = 0; j < rule.length(); j++)
                        if(Character.isUpperCase(rule.charAt(j)) && !reachNeterminals.contains(rule.charAt(j)))
                            reachNeterminals.add(rule.charAt(j));
        }

        //System.out.println(reachNeterminals);

        List<Character> res = new ArrayList<>(grammar.keySet());
        res.removeAll(reachNeterminals);

        if(res.size() != 0)
            System.out.println("Правила з недосяжними нетерміналами");
        for (Character re : res) {
            var rules = grammar.get(re);
            System.out.print(re + "->");
            for (int i = 0; i < rules.size(); i++){
                System.out.print(rules.get(i));
                if(i != rules.size() - 1)
                    System.out.print('|');
            }
            System.out.println();
        }
    }
}
