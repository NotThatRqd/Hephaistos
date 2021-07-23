package nbt;

import org.jglrxavpok.hephaistos.nbt.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeepClone {

    @Test
    public void list() {
        NBTList<NBTString> list = new NBTList<>(NBTTypes.TAG_String);
        list.add(new NBTString("Some text"));
        list.add(new NBTString("Some more text"));

        assertEquals(list, list.deepClone());
        assertNotSame(list, list.deepClone());
//        assertNotSame(list.get(0), list.deepClone().get(0));
//        assertNotSame(list.get(1), list.deepClone().get(1));
    }
}
