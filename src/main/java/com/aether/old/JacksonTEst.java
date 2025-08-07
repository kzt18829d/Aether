package com.aether.old;

import com.aether.business.constaints.Debug;
import com.aether.business.constaints.Terminal;
import com.aether.business.devices.Device;
import com.aether.business.devices.Light;
import com.aether.business.devices.SmartLock;
import com.aether.business.filemanagement.DeviceDataManager;
import com.aether.business.types.Brightness;
import com.aether.business.types.ColorTemperature;
import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.johncsinclair.consoletable.ColumnFormat;
import com.johncsinclair.consoletable.ConsoleTable;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class JacksonTEst {

    private Device createDevice() {
        return new Light(new Name("TemplateDeviceName"), new Location("TemplateLocation"), new Brightness(50), new ColorTemperature(3000));
    }

    private void save() {

    }

    private void createDeviceInfo(Device device) {
        if (device == null) {
            throw new RuntimeException("Device is null");
        }

        ConsoleTable SystemStatusReportTable = new ConsoleTable();
        String Header1 = "Device UUID";
        String Header2 = "Device name";
        String Header3 = "Device Location";
        String Header4 = "Device Status";
        String Header5 = "Light Brightness";
        String Header6 = "Light Color Temperature";
        String Header7 = "SmartLock LockStatus";
        SystemStatusReportTable.setHeaders(Header1, Header2, Header3, Header4).withAlignment(ColumnFormat.Aligned.CENTRE);

        SystemStatusReportTable.addRow(
                device.getDeviceUUID(),
                device.getDeviceName_string(),
                device.getDeviceLocation_string(),
                device.getPowerStatus().toString()
        );

        Debug.base("Device information:\n" + SystemStatusReportTable.toString());
    }



    public void test1() {
        DeviceTest2();
    }


    private void testTask1()  {
        try {
            Book b1 = new Book("Title1", "Author1", 2000);
            Book b2 = new Book("Title2", "Author1", 2001);
            Book b3 = new Book("Title3", "Author2", 2002);

            List<Book> books = new ArrayList<>(3);
            books.add(b1);
            books.add(b2);
            books.add(b3);

            ObjectMapper objectMapper = new ObjectMapper();

            File booksFile = new File("BOOKS.json");
            FileWriter fileWriter = new FileWriter(booksFile);

            String tablo = objectMapper.writeValueAsString(books);
            objectMapper.writeValue(fileWriter, books);
            Debug.base(tablo);

            List<Book> booksSerialized = objectMapper.readValue(booksFile, new TypeReference<List<Book>>(){});

            for (Book b : booksSerialized) {
                Debug.base(b.getTitle() + " ");
            }

        } catch (Exception e) {
            Debug.error(e.getMessage());
        }

    }
    private void DeviceTest1() {
        Debug.info("Start " + this.getClass().toString() + " testing....");
        Device deviceLight1 = createDevice();
        Device deviceLight2 = createDevice();

        Map<UUID, Device> deviceMapS = new HashMap<>(2);
        deviceMapS.putIfAbsent(deviceLight1.getDeviceUUID(), deviceLight1);
        deviceMapS.putIfAbsent(deviceLight2.getDeviceUUID(), deviceLight2);
        createDeviceInfo(deviceLight1);
        createDeviceInfo(deviceLight2);
        File file = new File("DeviceJSONTemp.json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            String toJSON = objectMapper.writeValueAsString(deviceMapS);
            objectMapper.writeValue(fileWriter, deviceMapS);
            Debug.base("JSON String:\n" + toJSON);

//            fileWriter.write(toJSON);
//            fileWriter.close();
            Debug.info("Write to file " + file.getName());
        } catch (IOException e) {
            Debug.error(e.getMessage());
        }
    }

    private void DeviceTest2() {
        DeviceDataManager deviceDataManager = new DeviceDataManager();

        Map<UUID, Device> deviceMap = new HashMap<>();
        Device device1 = new Light(new Name("Light1"), new Location("Loc1"), 10, 2701);
        Device device2 = new SmartLock(new Name("Lock1"), new Location("Loc1"));

        deviceMap.putIfAbsent(device1.getDeviceUUID(), device1);
        deviceMap.putIfAbsent(device2.getDeviceUUID(), device2);
        try {
            deviceDataManager.binarySave(deviceMap);

        } catch (Exception e) {
            Debug.error(e.getMessage());
        }

        try {
            Map<UUID, Device> deviceMap2 = deviceDataManager.binaryRead();
            for (Map.Entry<UUID, Device> deviceSet: deviceMap2.entrySet()) {
                Device device = deviceSet.getValue();
                Terminal.base(device.getDeviceName() + " \n" + device.getDeviceUUID() + "\n" + device.getDeviceLocation() + "\n" + device.getClass().toString());
            }
        } catch (Exception e) {
            Debug.error(e.getMessage());
        }
    }

}
