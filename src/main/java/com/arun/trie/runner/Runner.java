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

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Runner {

    public static void main(String[] args) throws IOException {
        final MapTrie<String> mapTrie = new SortedMapTrie<>();

        //final List<String> words = getWords();
        final List<String> words = getRulesFromFile();
        for (String word : words) {
            mapTrie.insert(word, word);
        }



//        System.out.println(mapTrie.getKeySuggestions("yo ba"));
//        mapTrie.print();
//        System.out.println("*******************************************");
        mapTrie.print();
        FileWriter fileWriter = new FileWriter(new File("results.txt").getName());
        fileWriter.write("*******************TRIE DATA STRUCTURE RESULTS************************\n\n");
        fileWriter.write("Sentence\t\t\t\t\t\t\t\t\t\t|Pattern Found\n\n");
        for (String sentence: loadTestWordFromFile()) {
            if (sentence != null){
                    boolean found = mapTrie.getValueSuggestions(sentence).size() > 0? true:false;
                    fileWriter.write(sentence+"\t\t\t\t\t\t|"+found+"\n");
            }
        }
        fileWriter.close();


    }

    private static List<String> loadTestWordFromFile() {
        final List<String> words = new ArrayList<>();
        final File file = new File("examples.txt");
        return getStrings(words, file);
    }

    private static List<String> getStrings(List<String> words, File file) {
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

    private static List<String> getRulesFromFile() {
        final List<String> words = new ArrayList<>();
        final File file = new File("patterns.txt");
        return getStrings(words, file);
    }

    private static HashMap<String,String[]> tags = new HashMap<>();

    static void print(String value){
        System.out.println(value);
    }
}
