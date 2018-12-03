/*
 * Copyright (c) 2018-2022 Caratacus, (caratacus@qq.com).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package org.crown.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * Map工具类
 * http://minborgsjavapot.blogspot.com/2014/12/java-8-initializing-maps-in-smartest-way.html
 *
 * @author Per-Åke Minborg
 */
public class Maps {

    private Maps() {
    }

    public static <K, V> MapBuilder<K, V> builder() {
        return builder(HashMap::new);
    }

    /**
     * Maps.<String, String>builder(TreeMap::new)
     *
     * @param mapSupplier
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> MapBuilder<K, V> builder(Supplier<Map<K, V>> mapSupplier) {
        return new MapBuilder<>(mapSupplier.get());
    }

    public static <K, V> ConcurrentMapBuilder<K, V> concurrentBuilder() {
        return concurrentBuilder(ConcurrentHashMap::new);
    }

    public static <K, V> ConcurrentMapBuilder<K, V> concurrentBuilder(Supplier<ConcurrentMap<K, V>> mapSupplier) {
        return new ConcurrentMapBuilder<>(mapSupplier.get());
    }

    private static class BaseBuilder<M extends Map<K, V>, K, V> {

        protected final M map;

        public BaseBuilder(M map) {
            this.map = map;
        }

        public BaseBuilder<M, K, V> put(K key, V value) {
            map.put(key, value);
            return this;
        }

        public M build() {
            return map;
        }
    }

    public static class MapBuilder<K, V> extends BaseBuilder<Map<K, V>, K, V> {

        private boolean unmodifiable;

        public MapBuilder(Map<K, V> map) {
            super(map);
        }

        @Override
        public MapBuilder<K, V> put(K key, V value) {
            super.put(key, value);
            return this;
        }

        public MapBuilder<K, V> unmodifiable(boolean unmodifiable) {
            this.unmodifiable = unmodifiable;
            return this;
        }

        public MapBuilder<K, V> unmodifiable() {
            return unmodifiable(true);
        }

        @Override
        public Map<K, V> build() {
            if (unmodifiable) {
                return Collections.unmodifiableMap(super.build());
            } else {
                return super.build();
            }
        }
    }

    public static class ConcurrentMapBuilder<K, V> extends BaseBuilder<ConcurrentMap<K, V>, K, V> {

        public ConcurrentMapBuilder(ConcurrentMap<K, V> map) {
            super(map);
        }

        @Override
        public ConcurrentMapBuilder<K, V> put(K key, V value) {
            super.put(key, value);
            return this;
        }
    }

}
