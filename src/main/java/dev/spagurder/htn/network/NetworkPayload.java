package dev.spagurder.htn.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface NetworkPayload
        //? >=1.21 {
        extends net.minecraft.network.protocol.common.custom.CustomPacketPayload
        //?}
{

    //? <1.21 {
    /*public FriendlyByteBuf getBuffer();
    *///?}

    public ResourceLocation id();

}
