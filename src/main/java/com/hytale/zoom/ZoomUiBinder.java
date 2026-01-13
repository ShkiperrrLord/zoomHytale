package com.hytale.zoom;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.PageManager;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class ZoomUiBinder {
    private ZoomUiBinder() {
    }

    public static void attachHotkeyPage(ZoomPlugin plugin, PlayerConnectEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        PageManager pageManager = player.getPageManager();
        if (pageManager == null) {
            return;
        }

        World world = event.getWorld();
        if (world == null) {
            return;
        }

        EntityStore entityStore = world.getEntityStore();
        Store<EntityStore> store = entityStore.getStore();
        Ref<EntityStore> ref = entityStore.getRefFromUUID(player.getUuid());
        if (store == null || ref == null) {
            return;
        }

        PlayerRef playerRef = event.getPlayerRef();
        if (playerRef == null) {
            return;
        }

        pageManager.openCustomPage(ref, store, new ZoomHotkeyPage(plugin, playerRef));
    }
}
