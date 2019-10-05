package kr.bigsky033.study.learningtest.tree

import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class BinarySearchTree<K : Comparable<K>, V> {

    private var root: Node? = null

    inner class Node(
        val key: K,
        var value: V,
        var left: Node? = null,
        var right: Node? = null,
        var n: Int = 0
    )

    fun size(): Int {
        return size(root)
    }

    private fun size(x: Node?): Int {
        return x?.n ?: 0
    }

    fun get(key: K): V? {
        return get(root, key)
    }

    private fun get(x: Node?, key: K): V? {
        if (x == null) return null
        val cmp = key.compareTo(x.key)
        return when {
            cmp < 0 -> get(x.left, key)
            cmp > 0 -> get(x.right, key)
            else -> x.value
        }
    }

    fun put(key: K, value: V) {
        root = put(root, key, value)
    }

    private fun put(x: Node?, key: K, value: V): Node? {
        if (x == null) return Node(key = key, value = value, n = 1)
        val cmp = key.compareTo(x.key)
        when {
            cmp < 0 -> x.left = put(x.left, key, value)
            cmp > 0 -> x.right = put(x.right, key, value)
            else -> x.value = value
        }
        x.n = size(x.left) + size(x.right) + 1
        return x
    }

    fun min(): K? {
        return min(root)?.key
    }

    private fun min(x: Node?): Node? {
        if (x == null) return null
        if (x.left == null) return x
        return min(x.left)
    }

    fun max(): K? {
        return max(root)?.key
    }

    private fun max(x: Node?): Node? {
        if (x == null) return null
        if (x.right == null) return x
        return max(x.right)
    }


    // returns the greatest element less than or equal to key, or null if there is no such element.
    // input key 와 비교했을 때 작은 것 중 제일 큰 것
    fun floor(key: K): K? {
        val x = floor(root, key) ?: return null
        return x.key
    }

    private fun floor(x: Node?, key: K): Node? {
        if (x == null) return null
        val cmp = key.compareTo(x.key)
        if (cmp == 0) return x
        if (cmp < 0) return floor(x.left, key)
        return floor(x.right, key) ?: x
    }

    // input key 와 비교했을 때 큰 것 중 가장 작은 것
    fun ceiling(key: K): K? {
        val x = ceiling(root, key) ?: return null
        return x.key
    }

    private fun ceiling(x: Node?, key: K): Node? {
        if (x == null) return null
        val cmp = key.compareTo(x.key)
        if (cmp == 0) return x
        return if (cmp < 0) ceiling(x.left, key) ?: x
        else ceiling(x.right, key)
    }

    fun select(k: Int): K? {
        return select(root, k)?.key
    }

    private fun select(x: Node?, k: Int): Node? {
        if (x == null) return null
        val t = size(x.left)
        return when {
            t > k -> select(x.left, k)
            t < k -> select(x.right, k - t - 1)
            else -> x
        }
    }

    fun rank(key: K): Int {
        return rank(key, root)
    }

    private fun rank(key: K, x: Node?): Int {
        if (x == null) return 0
        val cmp = key.compareTo(x.key)
        return when {
            cmp < 0 -> rank(key, x.left)
            cmp > 0 -> 1 + size(x.left) + rank(key, x.right)
            else -> size(x.left)
        }
    }

    fun deleteMin() {
        root = deleteMin(root)
    }

    private fun deleteMin(x: Node?): Node? {
        x!!
        if (x.left == null) return x.right
        x.left = deleteMin(x.left)
        x.n = size(x.left) + size(x.right) + 1
        return x
    }

    fun deleteMax() {
        root = deleteMax(root)
    }

    private fun deleteMax(x: Node?): Node? {
        x!!
        if (x.right == null) return x.left
        x.right = deleteMax(x.right)
        x.n = size(x.left) + size(x.right) + 1
        return x
    }

    fun delete(key: K) {
        root = delete(root, key)
    }

    private fun delete(x: Node?, key: K): Node? {
        if (x == null) return null
        var current = x
        val cmp = key.compareTo(x.key)
        // 현재 노드의 키가 삭제하려는 키보다 작으면 왼쪽으로 가서 삭제 진행
        when {
            cmp < 0 -> x.left = delete(x.left, key)
            // 현재 노드의 키가 삭제하려는 키보다 작으면 오른쪽으로 가서 삭제 진행
            cmp > 0 -> x.right = delete(x.right, key)
            else -> {
                // 실제 삭제
                if (current.right == null) return current.left
                if (current.left == null) return current.right
                val t = current
                current = min(t.right)!!
                current.right = deleteMin(t.right)
                current.left = t.left
            }
        }
        current.n = size(current.left) + size(current.right) + 1
        return current
    }

    fun keys(): Iterable<K> {
        return keys(min()!!, max()!!)
    }

    fun keys(lo: K, hi: K): Iterable<K> {
        val queue = LinkedBlockingQueue<K>()
        keys(root, queue, lo, hi)
        return queue
    }

    private fun keys(x: Node?, queue: Queue<K>, lo: K, hi: K) {
        if (x == null) return
        val cmpLo = lo.compareTo(x.key)
        val cmpHi = hi.compareTo(x.key)
        if (cmpLo < 0) keys(x.left, queue, lo, hi)
        if (cmpLo <= 0 && cmpHi >= 0) queue.add(x.key)
        if (cmpHi > 0) keys(x.right, queue, lo, hi)
    }

}

