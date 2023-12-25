package com.panopset.flywheel

/**
 * Enumeration of all commands.
 */
enum class Commands(
    val charCode: Char, commandName: String,
    val prototype: String
) {
    REPLACE('r', "r:Replace", "\${@r ?}"),
    FILE('f', "f:File", "\${@f ?}"),
    PUSH('p', "p:Push", "\${@p ?}?\${@q}"),
    LIST('l', "l:List", "\${@l ?}?\${@q}"),
    QUIT('q', "q:Quit", "\${@q}"),
    TEMPLATE('t', "t:Template", "\${@t ?}"),
    RAW('a', "a:Raw", "\${@a ?}, \${@a}\${@q}"),
    EXECUTE('e', "e:Execute", "\${@e ?}")

}
