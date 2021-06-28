package com.github.oxidemc.serverblacklist.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerAddress;

import com.github.oxidemc.serverblacklist.ServerBlacklistMod;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class MixinConnectScreen {
  @Shadow
  Screen parent;

  @Inject(method = "connect(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;)V", at = @At("HEAD"), cancellable = true)
  private void onConnect(MinecraftClient client, ServerAddress address, CallbackInfo ci) {
    String blacklist = ServerBlacklistMod.blacklist();
    if (address.getAddress().matches(blacklist)) {
      ServerBlacklistMod.logger().info("Blocking server " + address.getAddress());
      client.openScreen(this.parent);
      ci.cancel();
    }
  }
}
