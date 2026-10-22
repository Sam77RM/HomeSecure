package com.utp.homesecure.monitoring.domain.aggregates;

import com.utp.homesecure.monitoring.domain.events.DeviceRegisteredEvent;
import com.utp.homesecure.monitoring.domain.enums.DeviceStatus;
import com.utp.homesecure.monitoring.domain.enums.DeviceType;
import com.utp.homesecure.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.utp.homesecure.monitoring.domain.valueobjects.OwnerAccountId;

import java.time.Instant;
import java.util.Locale;

public class Device extends AbstractDomainAggregateRoot<Device> {
    private Long id;
    private final String code;
    private OwnerAccountId ownerAccountId;
    private String name;
    private String description;
    private final DeviceType type;
    private String zone;
    private String streamUrl;
    private DeviceStatus status;
    private boolean enabled;
    private Instant lastCommunicationAt;
    private final Instant createdAt;

    public Device(OwnerAccountId ownerAccountId, String code, String name, String description, DeviceType type, String zone, String streamUrl) {
        this(code, name, description, type, zone, streamUrl);
        if (ownerAccountId == null) throw new IllegalArgumentException("Owner account id is required");
        this.ownerAccountId = ownerAccountId;
    }

    public Device(String code, String name, String description, DeviceType type, String zone, String streamUrl) {
        this(null, code, name, description, type, zone, streamUrl, DeviceStatus.INACTIVE, true, null, Instant.now());
        registerDomainEvent(new DeviceRegisteredEvent(id, this.code, type, Instant.now()));
    }

    public Device(Long id, OwnerAccountId ownerAccountId, String code, String name, String description, DeviceType type, String zone,
                  String streamUrl, DeviceStatus status, boolean enabled,
                  Instant lastCommunicationAt, Instant createdAt) {
        this(id, code, name, description, type, zone, streamUrl, status, enabled, lastCommunicationAt, createdAt);
        if (ownerAccountId == null) throw new IllegalArgumentException("Owner account id is required");
        this.ownerAccountId = ownerAccountId;
    }

    public Device(Long id, String code, String name, String description, DeviceType type, String zone,
                  String streamUrl, DeviceStatus status, boolean enabled,
                  Instant lastCommunicationAt, Instant createdAt) {
        this.id = id;
        this.code = required(code, "Device code").toUpperCase(Locale.ROOT);
        this.name = required(name, "Device name");
        this.description = description == null ? "" : description.trim();
        if (type == null) throw new IllegalArgumentException("Device type is required");
        if (status == null) throw new IllegalArgumentException("Device status is required");
        if (createdAt == null) throw new IllegalArgumentException("Created at is required");
        this.type = type;
        this.zone = zone == null ? "" : zone.trim();
        this.streamUrl = validStreamUrl(type, streamUrl);
        this.status = status;
        this.enabled = enabled;
        this.lastCommunicationAt = lastCommunicationAt;
        this.createdAt = createdAt;
    }

    public void updateDetails(String name, String description, String zone, String streamUrl) {
        this.name = required(name, "Device name");
        this.description = description == null ? "" : description.trim();
        this.zone = zone == null ? "" : zone.trim();
        this.streamUrl = validStreamUrl(type, streamUrl);
    }

    public void enable() {
        if (enabled) throw new IllegalStateException("Device is already enabled");
        enabled = true;
        status = DeviceStatus.INACTIVE;
    }

    public void disable() {
        if (!enabled) throw new IllegalStateException("Device is already disabled");
        enabled = false;
        status = DeviceStatus.INACTIVE;
    }

    /** Called every time the device communicates with the server (e.g. MQTT message). */
    public void reportHeartbeat() {
        if (!enabled) throw new IllegalStateException("A disabled device cannot report its status");
        status = DeviceStatus.ACTIVE;
        lastCommunicationAt = Instant.now();
    }

    public void markDisconnected() {
        if (!enabled) throw new IllegalStateException("A disabled device cannot be marked as disconnected");
        status = DeviceStatus.DISCONNECTED;
    }

    public boolean isCamera() {
        return type == DeviceType.CAMERA;
    }

    public boolean isOnline() {
        return enabled && status == DeviceStatus.ACTIVE;
    }

    private static String validStreamUrl(DeviceType type, String value) {
        if (type != DeviceType.CAMERA) return null;
        String url = required(value, "Camera stream url");
        if (!url.toLowerCase(Locale.ROOT).startsWith("rtsp://"))
            throw new IllegalArgumentException("Camera stream url must use the RTSP protocol");
        return url;
    }

    private static String required(String value, String name) {
        if (value == null || value.isBlank()) throw new IllegalArgumentException(name + " is required");
        return value.trim();
    }

    public Long getId() {
        return id;
    }

    public OwnerAccountId getOwnerAccountId() { return ownerAccountId; }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public DeviceType getType() {
        return type;
    }

    public String getZone() {
        return zone;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Instant getLastCommunicationAt() {
        return lastCommunicationAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
