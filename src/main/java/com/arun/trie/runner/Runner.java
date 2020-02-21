/*
 * Copyright 2017 Arunkumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arun.trie.runner;

import com.arun.trie.MapTrie;
import com.arun.trie.SortedMapTrie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Runner {

    public static void main(String[] args) {
        final MapTrie<String> mapTrie = new SortedMapTrie<>();
        addTags();
        //final List<String> words = getWords();
        final List<String> words = getWordsFromFile();
        for (String word : words) {
            mapTrie.insert(word, word);
        }



//        System.out.println(mapTrie.getKeySuggestions("yo ba"));
//        mapTrie.print();
//        System.out.println("*******************************************");
        mapTrie.print();
        while (true){
            Scanner input = new Scanner(System.in);
            print("Enter your setswana sentence to check");
            String sentence = input.nextLine();
            if (sentence != null){
                if (mapTrie.getValueSuggestions(taggWord(sentence).toString()).size() > 0)
                    System.out.println("Pattern found from Trie Data Structure");
                else System.out.println("Couldn\'t recognize sentence pattern!");
                System.out.println(mapTrie.getValueSuggestions(taggWord(sentence).toString()).toString());
            }

        }

    }

    private static List<String> getWords() {
        final List<String> words = new ArrayList<>();
        words.add("yo o sa leleng");

        return words;
    }

    private static List<String> getWordsFromFile() {
        final List<String> words = new ArrayList<>();
        final File file = new File("patterns.txt");
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                words.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    private static HashMap<String,String[]> tags = new HashMap<>();

    private static void addTags(){
        String[] cc = {
                "yo o",
                "tse di",
                "se se",
                "o o",
                "le le",
                "e e",
                "a a",
                "lo lo",
                "se se",
                "ba ba"
        };
        String[] verbs = {
                "ruta",
                "bereke",
                "lela",
                "bona",
                "rutang",
                "berekeng",
                "lelang",
                "bonang",
                "ratang",
                "tlhola",
                "tsamaileng",
                "reka",
                "lapeng",
                "leleng"

        };

        tags.putIfAbsent("cc",cc);
        tags.putIfAbsent("verbs",verbs);

    }

    private static StringBuilder taggWord(String sentence){
        String[] words = sentence.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        String prev_word = "";

        for (int i = 0; i < words.length - 1; i++) {
            boolean found = false;
            for (int j = 0; j < tags.get("cc").length; j++) {
                String cc = words[i]+" "+words[i+1];
                if (cc.equals(tags.get("cc")[j])){
                    stringBuilder.append(words[i]+":cc ");
                    stringBuilder.append(words[i+1]+":cc ");
                    prev_word = words[i+1];
                    found = true;
                    break;
                }else if (prev_word.equals(words[i+1])){
                    stringBuilder.append(prev_word+":cc ");
                    found = true;
                    break;

                }else if ((prev_word.equals("o") && words[i].equals("a"))){
                    stringBuilder.append(words[i]+":cc ");
                    found = true;
                    break;
                }
                else
                    stringBuilder.append(words[i]);

            }
            for (String verb: tags.get("verbs")) {
                if (verb.equals(words[i+1])){
                    if (words[i+1].endsWith("ng")){
                        stringBuilder.append(words[i+1]+":verb+ng ");
                        found = true;
                        break;
                    }

                    else{
                        stringBuilder.append(words[i+1]+":verb ");
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
                stringBuilder.append(words[i+1]+" ");
        }
        print(stringBuilder.toString());
        return stringBuilder;
    }

    static void print(String value){
        System.out.println(value);
    }
}
