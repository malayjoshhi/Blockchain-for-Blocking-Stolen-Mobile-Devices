// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

/**
 * @title DeviceRegistry
 * @notice Minimal, self-contained registry for devices keyed by SHA-256(IMEI) as bytes32.
 *         Supports: Register, Transfer Ownership, Mark Stolen/Recovered, and Status Queries.
 *
 * @dev
 *  - DO NOT put raw IMEI or owner names on-chain. Only store the SHA-256(IMEI) hash (bytes32) and owner address.
 *  - Hash the IMEI in your Android app (SHA-256) and pass the 32-byte value to these functions.
 *  - Example: bytes32 imeiHash = bytes32(hexStringToBytes("...64 hex chars of SHA-256..."));
 */
contract DeviceRegistry {
    struct Device {
        address owner;        // current owner
        bool    stolen;       // true if reported lost/stolen
        uint64  registeredAt; // block timestamp (seconds)
        uint64  updatedAt;    // last status/transfer update
        bool    exists;       // internal guard to check registration
    }

    // imeiSha256 => Device
    mapping(bytes32 => Device) private _devices;

    // -------- Events --------
    event DeviceRegistered(bytes32 indexed imeiSha256, address indexed owner);
    event DeviceTransferred(bytes32 indexed imeiSha256, address indexed from, address indexed to);
    event DeviceStolenStatusChanged(bytes32 indexed imeiSha256, bool stolen, address indexed by);

    // -------- Modifiers --------
    modifier deviceExists(bytes32 imeiSha256) {
        require(_devices[imeiSha256].exists, "Device not registered");
        _;
    }

    modifier onlyDeviceOwner(bytes32 imeiSha256) {
        require(_devices[imeiSha256].owner == msg.sender, "Caller is not device owner");
        _;
    }

    // =========================================================
    // ================  CORE WRITE FUNCTIONS  =================
    // =========================================================

    /**
     * @notice Register a device to msg.sender. Fails if already registered.
     * @param imeiSha256 SHA-256(IMEI) as bytes32 (32-byte value).
     */
    function registerDevice(bytes32 imeiSha256) external {
        require(imeiSha256 != bytes32(0), "Invalid hash");
        Device storage d = _devices[imeiSha256];
        require(!d.exists, "Already registered");

        d.owner        = msg.sender;
        d.stolen       = false;
        d.registeredAt = uint64(block.timestamp);
        d.updatedAt    = uint64(block.timestamp);
        d.exists       = true;

        emit DeviceRegistered(imeiSha256, msg.sender);
    }

    /**
     * @notice Transfer device ownership to a new address.
     * @dev Reverts if the device is currently marked stolen.
     * @param imeiSha256 SHA-256(IMEI) as bytes32.
     * @param newOwner   New owner address (cannot be zero).
     */
    function transferOwnership(bytes32 imeiSha256, address newOwner)
        external
        deviceExists(imeiSha256)
        onlyDeviceOwner(imeiSha256)
    {
        require(newOwner != address(0), "New owner cannot be zero");
        Device storage d = _devices[imeiSha256];
        require(!d.stolen, "Cannot transfer while stolen");

        address prevOwner = d.owner;
        d.owner     = newOwner;
        d.updatedAt = uint64(block.timestamp);

        emit DeviceTransferred(imeiSha256, prevOwner, newOwner);
    }

    /**
     * @notice Mark a device as stolen or recovered (stolen=false).
     * @dev Only the current owner can change this status.
     * @param imeiSha256 SHA-256(IMEI) as bytes32.
     * @param stolen     true => mark stolen, false => mark recovered.
     */
    function setStolen(bytes32 imeiSha256, bool stolen)
        external
        deviceExists(imeiSha256)
        onlyDeviceOwner(imeiSha256)
    {
        Device storage d = _devices[imeiSha256];
        d.stolen    = stolen;
        d.updatedAt = uint64(block.timestamp);

        emit DeviceStolenStatusChanged(imeiSha256, stolen, msg.sender);
    }

    // =========================================================
    // ==================  READ / VIEW API  ====================
    // =========================================================

    /**
     * @notice Check whether a device is registered.
     */
    function isRegistered(bytes32 imeiSha256) external view returns (bool) {
        return _devices[imeiSha256].exists;
    }

    /**
     * @notice Returns the current owner of a device.
     * @dev Reverts if the device does not exist.
     */
    function ownerOf(bytes32 imeiSha256)
        external
        view
        deviceExists(imeiSha256)
        returns (address)
    {
        return _devices[imeiSha256].owner;
    }

    /**
     * @notice Returns true if the device is marked stolen.
     * @dev Reverts if the device does not exist.
     */
    function isStolen(bytes32 imeiSha256)
        external
        view
        deviceExists(imeiSha256)
        returns (bool)
    {
        return _devices[imeiSha256].stolen;
    }

    /**
     * @notice Returns full device data: (owner, stolen, registeredAt, updatedAt).
     * @dev Reverts if the device does not exist.
     */
    function getDevice(bytes32 imeiSha256)
        external
        view
        deviceExists(imeiSha256)
        returns (address owner, bool stolen, uint64 registeredAt, uint64 updatedAt)
    {
        Device storage d = _devices[imeiSha256];
        return (d.owner, d.stolen, d.registeredAt, d.updatedAt);
    }
}
