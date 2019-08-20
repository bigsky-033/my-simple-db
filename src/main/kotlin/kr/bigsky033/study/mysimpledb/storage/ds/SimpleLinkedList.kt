package kr.bigsky033.study.mysimpledb.storage.ds

class SimpleLinkedList<T> : SimpleList<T> {

    private var head: Node<T>? = null

    override fun first(): Node<T>? = this.head

    override fun last(): Node<T>? {
        var node = this.head
        if (node != null) {
            while (node?.next != null) {
                node = node.next
            }
        }
        return node
    }

    override fun isEmpty(): Boolean = this.head == null

    override fun count(): Int {
        var count = 0
        var node = this.head
        if (node != null) {
            count++
            while (node?.next != null) {
                node = node.next
                count++
            }
        }
        return count
    }

    override fun get(index: Int): Node<T>? {
        if (!isValidIndex(index)) {
            return null
        }
        var node = head
        var i = index
        while (node != null) {
            if (i == 0) return node
            i--
            node = node.next
        }
        return null
    }

    private fun isValidIndex(index: Int) = index >= 0 && index < count()

    override fun add(value: T) {
        val newNode = Node(value)
        var node = this.head
        while (node?.next != null) {
            node = node.next
        }
        if (node != null) {
            node.next = newNode
        } else {
            this.head = newNode
        }
    }

    override fun clear() {
        this.head = null
    }

}