<div align="center">
<h1>ResourceHack</h1>
Minecraft server resource pack protection tool

![Minecraft Version](https://img.shields.io/badge/Minecraft_Version-1.18.1-green?style=for-the-badge)

Other versions could be found [here](https://github.com/afn-ArcNode/ResourceHack/branches)

<img alt="paper" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/paper_vector.svg">
<img alt="fabric" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/fabric_vector.svg">
<img alt="forge" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/supported/forge_vector.svg">
</div>

<div align="center">

<a href="https://hangar.papermc.io/DvArcNode/ResourceHack"><img alt="hangar" height="40" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/compact/available/hangar_vector.svg"></a>
<a href="https://www.minebbs.com/resources/mod.9024/"><img alt="MineBBS" height="40" src="assets/minebbs_badge.svg"></a>

</div>

<!-- Part: Features -->
<div align="center"><h2>Features</h2></div>

- Remove server resource confirmation screen (Auto-confirm)
- Encrypt resource packs
- Prevent unnecessary resource reloading (Switching server behind a proxy)

<!-- Part: Usage -->
<div align="center"><h2>Usage</h2></div>

### Client
Install the mod, no more configurations needed

### Server
1. Install the server plugin and [packetevents](https://github.com/retrooper/packetevents)
2. Start server, edit `~/plugins/ResourceHack/config.yml` to configure an AES-256 cryptic key
3. Restart server or reload the plugin
4. Use `res-encrypt <path>` (Requires `reshack.encrypt` permission) command to encrypt your resource pack
5. Configure your server resource pack loading (ItemsAdder, Nova, JResourcePack, server.properties, etc.)
