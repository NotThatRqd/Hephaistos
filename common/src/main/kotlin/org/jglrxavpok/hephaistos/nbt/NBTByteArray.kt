package org.jglrxavpok.hephaistos.nbt

import org.jglrxavpok.hephaistos.collections.ImmutableByteArray
import java.io.DataInputStream
import java.io.DataOutputStream

class NBTByteArray internal constructor(val value: ImmutableByteArray) : NBT, Iterable<Byte> {

    val size get() = value.size

    override val ID = NBTTypes.TAG_Int_Array

    override fun writeContents(destination: DataOutputStream) {
        destination.writeInt(size)
        destination.write(value.copyArray())
    }

    operator fun get(index: Int) = value[index]

    override fun toSNBT(): String {
        val list = value.joinToString(",") { "${it}B" }
        return "[B;$list]"
    }

    override fun toString() = toSNBT()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NBTByteArray

        if (value contentEquals other.value) return false

        return true
    }

    override fun hashCode() = value.hashCode()

    override fun iterator() = value.iterator()

    companion object : NBTReaderCompanion<NBTByteArray> {

        val EMPTY = NBTByteArray(ImmutableByteArray.EMPTY)

        override fun readContents(source: DataInputStream): NBTByteArray {
            val length = source.readInt()
            val value = ImmutableByteArray(length) { source.readByte() }
            return NBTByteArray(value)
        }
    }
}
