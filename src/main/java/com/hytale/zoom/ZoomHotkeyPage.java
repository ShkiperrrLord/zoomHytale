package com.hytale.zoom;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class ZoomHotkeyPage extends InteractiveCustomUIPage<ZoomHotkeyPage.HotkeyEventData> {
    private static final String KEY_FIELD = "Key";
    private static final String KEY_SELECTOR = "*";
    private static final String KEY_DATA_SOURCE = "@Key";

    private final ZoomPlugin plugin;

    public ZoomHotkeyPage(ZoomPlugin plugin, PlayerRef playerRef) {
        super(playerRef, CustomPageLifetime.CanDismiss, HotkeyEventData.CODEC);
        this.plugin = plugin;
    }

    @Override
    public void build(
            Ref<EntityStore> ref,
            UICommandBuilder commands,
            UIEventBuilder events,
            Store<EntityStore> store
    ) {
        events.addEventBinding(
                CustomUIEventBindingType.KeyDown,
                KEY_SELECTOR,
                EventData.of(KEY_FIELD, KEY_DATA_SOURCE),
                false
        );
    }

    @Override
    public void handleDataEvent(
            Ref<EntityStore> ref,
            Store<EntityStore> store,
            HotkeyEventData data
    ) {
        if (data == null || data.key == null) {
            return;
        }
        String expectedKey = plugin.getSettings().getHotkey();
        if (expectedKey.equalsIgnoreCase(data.key)) {
            plugin.toggleZoom(playerRef);
        }
    }

    static final class HotkeyEventData {
        static final BuilderCodec<HotkeyEventData> CODEC = BuilderCodec.builder(HotkeyEventData.class, HotkeyEventData::new)
                .append(new KeyedCodec<>("Key", Codec.STRING), (value, entry) -> entry.key = value, entry -> entry.key)
                .build();

        private String key;
    }
}
