# zoomHytale
Hytale zoom mod.

## Overview
This plugin provides a `/zoom` command and a configurable hotkey (default `Z`) that toggle a server-driven camera zoom for the player. It uses the server camera settings to mimic an Optifine-style zoom while keeping the client unmodified.

Because Hytale currently does not expose a dedicated FOV or client-side zoom API, this implementation relies on server camera overrides. That means the effect is a best-effort approximation until a true client zoom hook is available.

## Build
```bash
gradle jar
```

The resulting jar can be placed in your server's plugin/mod directory. If you add a Gradle wrapper, you can use `./gradlew jar` instead.

## Usage
* `/zoom` — toggles zoom on/off for the executing player.
* Press the configured hotkey (default `Z`) to toggle zoom.

### Hotkey settings
On first run, the plugin writes `zoom-settings.json` to the plugin data directory with a `hotkey` field. Edit that field to change the key.

## Implementation notes
* Zoom state is tracked per-player server-side.
* The camera settings are sent via the `SetServerCamera` packet and can be reset by running `/zoom` again.
