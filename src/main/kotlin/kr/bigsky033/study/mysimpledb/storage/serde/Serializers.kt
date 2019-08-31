package kr.bigsky033.study.mysimpledb.storage.serde

import kr.bigsky033.study.mysimpledb.entity.Row

fun Row.toCsv(): String {
    return "$id,$username,$email"
}
