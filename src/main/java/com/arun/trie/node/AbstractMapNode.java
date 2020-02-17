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

package com.arun.trie.node;


import com.arun.trie.base.AbstractTrieNode;
import com.arun.trie.base.TrieNode;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractMapNode<V> extends AbstractTrieNode<V> {

    private final Map<String, AbstractMapNode<V>> children;

    AbstractMapNode(String word) {
        super(word);
        children = createMap();
    }

    private Map<String, AbstractMapNode<V>> createMap() {
        return onCreateMap();
    }

    private AbstractMapNode<V> createNode(String word) {
        return onCreateNewNode(word);
    }

    protected abstract Map<String, AbstractMapNode<V>> onCreateMap();

    protected abstract AbstractMapNode<V> onCreateNewNode(String word);

    @Override
    public TrieNode<V> addChild(final String word) {
        final AbstractMapNode<V> leafNode = createNode(word);
        children.put(word, leafNode);
        return leafNode;
    }

    @Override
    public TrieNode<V> getChild(final String word) {
        if (children.containsKey(word)) {
            return children.get(word);
        } else {
            return null;
        }
    }

    @Override
    public void removeChild(final String word) {
        TrieNode<V> removedNode = children.remove(word);
        if (removedNode != null) {
            removedNode.clear();
            removedNode = null;
        }
    }

    @Override
    public boolean containsChild(final String  word) {
        return children.containsKey(word);
    }

    @Override
    public Collection<AbstractMapNode<V>> getChildren() {
        return children.values();
    }

    @Override
    public boolean isEnd() {
        return children.values().isEmpty();
    }

    @Override
    public void clear() {
        super.clear();
        children.clear();
    }
}
