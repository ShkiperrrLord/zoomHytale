package com.hytale.zoom;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ZoomPlugin extends JavaPlugin {
    private static ZoomPlugin instance;
    private final Set<UUID> zoomedPlayers = ConcurrentHashMap.newKeySet();
    private ZoomSettings settings;

    public ZoomPlugin(JavaPluginInit init) {
        super(init);
    }

    public static ZoomPlugin getInstance() {
        return instance;
    }

    @Override
    protected void setup() {
        instance = this;
        settings = ZoomSettings.load(getDataDirectory());
        getCommandRegistry().registerCommand(new ZoomCommand(this));
        getEventRegistry().register(
                com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent.class,
                this,
                event -> ZoomUiBinder.attachHotkeyPage(this, event)
        );
    }

    public boolean isZoomed(UUID playerId) {
        return zoomedPlayers.contains(playerId);
    }

    public void setZoomed(UUID playerId, boolean enabled) {
        if (enabled) {
            zoomedPlayers.add(playerId);
        } else {
            zoomedPlayers.remove(playerId);
        }
    }

    public ZoomSettings getSettings() {
        return settings;
    }

    public void setZoomState(com.hypixel.hytale.server.core.universe.PlayerRef player, boolean enabled) {
        if (enabled) {
            ZoomCameraService.applyZoom(player);
            setZoomed(player.getUuid(), true);
        } else {
            ZoomCameraService.clearZoom(player);
            setZoomed(player.getUuid(), false);
        }
    }

    public boolean toggleZoom(com.hypixel.hytale.server.core.universe.PlayerRef player) {
        boolean enableZoom = !isZoomed(player.getUuid());
        setZoomState(player, enableZoom);
        return enableZoom;
    }
}
