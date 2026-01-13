package com.hytale.zoom;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class ZoomCommand extends AbstractPlayerCommand {
    private static final Message ZOOM_ENABLED_MESSAGE = Message.raw("Zoom enabled.");
    private static final Message ZOOM_DISABLED_MESSAGE = Message.raw("Zoom disabled.");

    private final ZoomPlugin plugin;

    public ZoomCommand(ZoomPlugin plugin) {
        super("zoom", "Toggle camera zoom");
        this.plugin = plugin;
    }

    @Override
    protected void execute(
            CommandContext context,
            Store<EntityStore> entityStore,
            Ref<EntityStore> senderRef,
            PlayerRef player,
            World world
    ) {
        boolean enabled = plugin.toggleZoom(player);
        if (enabled) {
            context.sendMessage(ZOOM_ENABLED_MESSAGE);
        } else {
            context.sendMessage(ZOOM_DISABLED_MESSAGE);
        }
    }
}
