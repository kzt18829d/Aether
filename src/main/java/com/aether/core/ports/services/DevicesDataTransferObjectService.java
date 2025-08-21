package com.aether.core.ports.services;

import com.aether.core.types.devices.AbstractDevice;
import com.aether.dto.DeviceDTO;

import java.util.List;

public interface DevicesDataTransferObjectService {

    List<AbstractDevice> deserializeFromDTO(List<DeviceDTO> deviceDTOList);
    AbstractDevice deserializeFromDTO(DeviceDTO deviceDTO);

    DeviceDTO serializeToDTO(AbstractDevice device);
    List<DeviceDTO> serializeToDTO(List<AbstractDevice> deviceList);

}
