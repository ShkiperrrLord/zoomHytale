package com.hytale.zoom;

import com.hypixel.hytale.protocol.ClientCameraView;
import com.hypixel.hytale.protocol.Direction;
import com.hypixel.hytale.protocol.MovementForceRotationType;
import com.hypixel.hytale.protocol.PositionDistanceOffsetType;
import com.hypixel.hytale.protocol.RotationType;
import com.hypixel.hytale.protocol.ServerCameraSettings;
import com.hypixel.hytale.protocol.Vector3f;
import com.hypixel.hytale.protocol.packets.camera.SetServerCamera;
import com.hypixel.hytale.server.core.io.PacketHandler;
import com.hypixel.hytale.server.core.universe.PlayerRef;

public final class ZoomCameraService {
    private static final float ZOOM_DISTANCE = 0.2f;
    private static final float LERP_SPEED = 0.2f;

    private ZoomCameraService() {
    }

    public static void applyZoom(PlayerRef player) {
        ServerCameraSettings settings = new ServerCameraSettings();
        settings.positionLerpSpeed = LERP_SPEED;
        settings.rotationLerpSpeed = LERP_SPEED;
        settings.distance = ZOOM_DISTANCE;
        settings.displayCursor = false;
        settings.displayReticle = true;
        settings.isFirstPerson = true;
        settings.allowPitchControls = true;
        settings.eyeOffset = true;
        settings.positionDistanceOffsetType = PositionDistanceOffsetType.DistanceOffset;
        settings.movementForceRotationType = MovementForceRotationType.AttachedToHead;
        settings.movementForceRotation = new Direction(0.0f, 0.0f, 0.0f);
        settings.rotationType = RotationType.AttachedToPlusOffset;
        settings.rotationOffset = new Direction(0.0f, 0.0f, 0.0f);
        settings.movementMultiplier = new Vector3f(1.0f, 1.0f, 1.0f);

        PacketHandler handler = player.getPacketHandler();
        handler.writeNoCache(new SetServerCamera(ClientCameraView.Custom, true, settings));
    }

    public static void clearZoom(PlayerRef player) {
        PacketHandler handler = player.getPacketHandler();
        handler.writeNoCache(new SetServerCamera(ClientCameraView.Custom, false, null));
    }
}
