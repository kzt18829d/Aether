package com.aether.devices.SubTypes;

import com.aether.business.Exceptions.InvalidDeviceNameException;
import com.aether.business.devices.SubTypes.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

    @Test
    @DisplayName("Тест создания объекта с правильным именем (not numeric).")
    void trueName1() {
        String trueName1 = "deviceTrueName";
        Name nameTest1 = new Name(trueName1);
        assertNotNull(nameTest1);
        assertEquals(trueName1, nameTest1.getName());
    }

    @Test
    @DisplayName("Тест создания объекта с правильным именем (numeric).")
    void trueName2() {
        String trueName2 = "deviceTrueName2";
        Name nameTest2 = new Name(trueName2);
        assertNotNull(nameTest2);
        assertEquals(trueName2, nameTest2.getName());
    }

    @Test
    @DisplayName("Тест создания объекта с полностью циферным именем.")
    void numericName() {
        String string = "1123";
        Exception exception = assertThrows(InvalidDeviceNameException.class, () -> { new Name(string); });
        assertEquals("The device name consists entirely of numbers.", exception.getMessage());
    }

    @Test
    @DisplayName("Тест на пустое имя")
    void emptyName() {
        String string = "";
        Exception exception = assertThrows(InvalidDeviceNameException.class, () -> { new Name(string); });
        assertEquals("Assigned empty name to device.", exception.getMessage());
    }

}
