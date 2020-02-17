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

package com.arun.trie;

import com.arun.trie.base.AbstractTrie;
import com.arun.trie.base.TrieNode;
import com.arun.trie.node.HashMapNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class MapTrie<V> implements AbstractTrie<V> {

    protected TrieNode<V> root;

    public MapTrie() {
        root = createRootNode();
    }

    private TrieNode<V> createRootNode() {
        return onCreateRootNode();
    }

    protected TrieNode<V> onCreateRootNode() {
        return new HashMapNode<>(ROOT_KEY);
    }

    @Override
    public void print() {
        root.print();
    }

    @Override
    public void insert(String key, V value) {
        if (key == null) {
            return;
        }
        key = trimLowercaseString(key);
        String words[] = key.split(" ");
        TrieNode<V> crawler = root;
        for (int i = 0; i < words.length; i++) {
            final String word = words[i];
            if (!crawler.containsChild(word)) {
                crawler = crawler.addChild(word);
            } else {
                crawler = crawler.getChild(word);
            }
        }
        crawler.setKey(true);
        crawler.setValue(value);
    }

    @Override
    public void deleteKey(final String key) {
        if (key == null) {
            return;
        }
        deleteKey(root, trimLowercaseString(key), 0);
    }

    @Override
    public boolean contains(String key) {
        if (key == null) {
            return false;
        }
        key = trimLowercaseString(key);

        TrieNode crawler = root;
        if (crawler.containsChild(key)) {
            crawler = crawler.getChild(key);
        } else {
            return false;
        }
        return crawler.isKey();
    }

    @Override
    public V get(String key) {
        if (key == null) {
            return null;
        }
        key = trimLowercaseString(key);

        TrieNode<V> crawler = root;
        if (crawler.containsChild(key)) {
            crawler = crawler.getChild(key);
        }

        if (crawler.isKey()) {
            return crawler.getValue();
        }
        return null;
    }

    @Override
    public List<String> getKeySuggestions(String key) {
        if (key == null) {
            return Collections.emptyList();
        }
        key = trimLowercaseString(key);

        final StringBuilder prefix = new StringBuilder();

        TrieNode<V> crawler = root;
        if (crawler.containsChild(key)) {
            prefix.append(key);
            crawler = crawler.getChild(key);
        }

        final List<String> strings = new LinkedList<>();
        findKeySuggestions(crawler, prefix, strings);
        return strings;
    }

    @Override
    public List<String> keys() {
        return getKeySuggestions(String.valueOf(ROOT_KEY));
    }

    @Override
    public List<V> getValueSuggestions(String prefix) {
        if (prefix == null) {
            return Collections.emptyList();
        }
        prefix = trimLowercaseString(prefix);

        TrieNode<V> crawler = root;
        if (crawler.containsChild(prefix)) {
            crawler = crawler.getChild(prefix);
        }

        final List<V> suggestions = new LinkedList<>();
        findValueSuggestions(crawler, suggestions);
        return suggestions;
    }

    @Override
    public List<V> values() {
        return getValueSuggestions(String.valueOf(ROOT_KEY));
    }

    @Override
    public int size() {
        return size(root);
    }

    @Override
    public void clear() {
        root.clear();
    }

    @Override
    public void fastClear() {
        root = createRootNode();
    }

    @Override
    public String toString() {
        return root.toString();
    }

    private int size(final TrieNode<V> node) {
        int sum = 0;
        if (node.isKey()) {
            sum = 1;
        }
        for (final TrieNode<V> child : node.getChildren()) {
            sum += size(child);
        }
        return sum;
    }

    private void findKeySuggestions(final TrieNode<V> trieNode, final StringBuilder prefix,
                                    final List<String> words) {
        if (trieNode == null) {
            return;
        }
        if (trieNode.isKey()) {
            words.add(prefix.toString());
        }
        if (trieNode.isEnd()) {
            return;
        }
        for (final TrieNode<V> child : trieNode.getChildren()) {
            findKeySuggestions(child, new StringBuilder(prefix).append(child.getString()), words);
        }
    }

    private void findValueSuggestions(final TrieNode<V> trieNode, final List<V> suggestions) {
        if (trieNode == null) {
            return;
        }
        if (trieNode.isKey()) {
            final V value = trieNode.getValue();
            if (value != null) {
                suggestions.add(value);
            } else {
                System.out.println("Null value for a key encountered");
            }
        }
        if (trieNode.isEnd()) {
            return;
        }
        for (final TrieNode<V> child : trieNode.getChildren()) {
            findValueSuggestions(child, suggestions);
        }
    }

    private boolean deleteKey(TrieNode<V> node, final String word, int index) {
        if (word == null) {
            return false;
        }
        for (final TrieNode<V> child : node.getChildren()) {
            if (child.getString() == word) {
                if (deleteKey(child, word, index + 1) && child.isEnd() && !child.isKey()) {
                    node.removeChild(child.getString());
                    return true;
                }
            }
        }
        return false;
    }

    protected String trimLowercaseString(String key) {
        return key.toLowerCase().trim();
    }
}
