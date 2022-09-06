package org.jglrxavpok.hephaistos.nbt

import org.jglrxavpok.hephaistos.collections.ImmutableLongArray
import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.ByteBuffer

class NBTLongArray constructor(override val value: ImmutableLongArray) : NBT, Iterable<Long> {

    val size get() = value.size

    override val ID = NBTType.TAG_Long_Array

    constructor(vararg numbers: Long): this(ImmutableLongArray(*numbers))

    override fun writeContents(destination: DataOutputStream) {
        destination.writeInt(size)

        val buffer = ByteBuffer.allocate(size * 8)
        val longBuffer = buffer.asLongBuffer()
        value.forEach { longBuffer.put(it) }

        destination.write(buffer.array())
    }

    operator fun get(index: Int) = value[index]

    override fun toSNBT(): String {
        val list = value.joinToString(",") { "${it}L" }
        return "[L;$list]"
    }

    override fun toString() = toSNBT()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NBTLongArray

        if (!(value contentEquals other.value)) return false

        return true
    }

    override fun hashCode() = value.hashCode()

    override fun iterator() = value.iterator()

    companion object : NBTReaderCompanion<NBTLongArray> {
        @JvmField
        val EMPTY = NBTLongArray()

        override fun readContents(source: DataInputStream): NBTLongArray {
            val length = source.readInt()
            val inArray = source.readNBytes(length * 8)
            val outArray = LongArray(length)

            val padding = outArray.size * 8 - inArray.size
            val buffer = ByteBuffer.allocate(inArray.size + padding).put(ByteArray(padding)).put(inArray)
            buffer.flip()
            buffer.asLongBuffer().get(outArray)

            val value = ImmutableLongArray(*outArray)
            return NBTLongArray(value)
        }
    }
}
