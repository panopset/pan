package com.panopset.compat

fun filter(panop: Panop, input: String, filter: String): String {
    val sb = StringBuffer()
    FOR@ for (inp in Stringop.stringToList(panop, input)) {
        for (fs in Stringop.stringToList(panop, filter)) {
            if (inp == fs) {
                continue@FOR
            }
        }
        sb.append(inp)
        sb.append(Stringop.getEol())
    }
    return sb.toString()
}
