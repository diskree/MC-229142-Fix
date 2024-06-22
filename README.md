# MC-229142 Fix

A client-side mod that fixes a bug preventing resource packs to change inventory textures for trident and spyglass. This issue is tracked here: https://bugs.mojang.com/browse/MC-229142.

### Technical description

Trident and spyglass has forced replacement of the model in two spaces in the code, but only the model in the hand has texture overrides applying, but the model in the inventory does not have this.

Fix adds this missing overrides applying.