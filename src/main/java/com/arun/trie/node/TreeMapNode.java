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


import java.util.Map;
import java.util.TreeMap;


public class TreeMapNode<V> extends AbstractMapNode<V> {

    public TreeMapNode(String word) {
        super(word);
    }

    @Override
    protected Map<String, AbstractMapNode<V>> onCreateMap() {
        return new TreeMap<>();
    }

    @Override
    protected AbstractMapNode<V> onCreateNewNode(String word) {
        return new TreeMapNode<>(word);
    }
}
