package com.panopset.compat

class SortedMapKey(val sorter: String, val name: String) : Comparable<SortedMapKey?> {

    override fun compareTo(o: SortedMapKey?): Int {
        if (o != null) {
            return sorter.compareTo(o.sorter)
        }
        return -1
    }
}
