package kr.bigsky033.study.mysimpledb.storage.ds

class SimpleLinkedList<T> : SimpleList<T> {

    private var head: Node<T>? = null

    private var numberOfCurrentItems: Int = 0

    override fun first(): Node<T>? = head

    override fun last(): Node<T>? {
        var node = head
        if (node != null) {
            while (node?.next != null) {
                node = node.next
            }
        }
        return node
    }

    override fun isEmpty(): Boolean = head == null

    override fun size(): Int = numberOfCurrentItems

    override fun get(index: Int): T? {
        if (!isValidIndex(index)) {
            return null
        }
        var node = head
        var i = index
        while (node != null) {
            if (i == 0) return node.value
            i--
            node = node.next
        }
        return null
    }

    private fun isValidIndex(index: Int) = index >= 0 && index < size()

    override fun add(value: T) {
        val newNode = Node(value)
        var node = head
        while (node?.next != null) {
            node = node.next
        }
        if (node != null) {
            node.next = newNode
        } else {
            head = newNode
        }
        numberOfCurrentItems++
    }

    override fun clear() {
        head = null
        numberOfCurrentItems = 0
    }

    override fun iterator(): Iterator<T> {
        return SimpleLinkedListIterator(head)
    }

    inner class SimpleLinkedListIterator<T>(first: Node<T>?) : Iterator<T> {

        private var current: Node<T>? = first

        override fun hasNext(): Boolean = current != null

        override fun next(): T {
            val value = current!!.value
            current = current!!.next
            return value
        }

    }

}
