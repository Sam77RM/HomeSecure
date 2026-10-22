package com.utp.homesecure.monitoring.application.services;

import com.utp.homesecure.monitoring.application.ports.AccountStatusPort;
import com.utp.homesecure.monitoring.domain.aggregates.Device;
import com.utp.homesecure.monitoring.domain.enums.DeviceType;
import com.utp.homesecure.monitoring.domain.repositories.DeviceRepository;
import com.utp.homesecure.monitoring.domain.valueobjects.OwnerAccountId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeviceRegistrationService {
    private final DeviceRepository devices;
    private final AccountStatusPort accountStatus;

    public DeviceRegistrationService(DeviceRepository devices, AccountStatusPort accountStatus) {
        this.devices = devices;
        this.accountStatus = accountStatus;
    }

    @Transactional
    public Device register(String userAccountId, String code, String name, String description,
                          DeviceType type, String zone, String streamUrl) {
        OwnerAccountId owner = new OwnerAccountId(userAccountId);
        if (!accountStatus.existsAndIsActive(owner.value())) {
            throw new IllegalArgumentException("The owner account does not exist or is inactive");
        }
        if (devices.existsByCode(code)) throw new IllegalStateException("Device code already exists");
        Device device = new Device(owner, code, name, description, type, zone, streamUrl);
        return devices.save(device);
    }
}
