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

package com.arun.trie.base;

import com.arun.trie.node.AbstractMapNode;

import java.util.Collection;

public interface TrieNode<V> {

    V getValue();

    void setValue(V value);

    String getString();

    TrieNode<V> addChild(final String word);

    TrieNode<V> getChild(final String word);

    void removeChild(final String word);

    Collection<AbstractMapNode<V>> getChildren();

    boolean isEnd();

    boolean isKey();

    void setKey(boolean isKey);

    void print();

    boolean containsChild(String word);

    void clear();
}
