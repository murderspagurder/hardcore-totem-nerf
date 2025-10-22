package dev.spagurder.htn.network;

import dev.spagurder.htn.HardcoreTotemNerf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

//? >=1.21 {
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
//?}

public record C2SHelloPayload(String modVersion) implements NetworkPayload {

    public static final ResourceLocation HELLO_PAYLOAD_ID =
    //? >=1.21 {
            ResourceLocation.fromNamespaceAndPath(HardcoreTotemNerf.MOD_ID, "hello");
    //?} else {
            /*new ResourceLocation(HardcoreTotemNerf.MOD_ID, "hello");
    *///?}

    //? >=1.21 {
    public static final CustomPacketPayload.Type<C2SHelloPayload> TYPE =
            new CustomPacketPayload.Type<>(HELLO_PAYLOAD_ID);
    public static final StreamCodec<FriendlyByteBuf, C2SHelloPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            C2SHelloPayload::modVersion,
            C2SHelloPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    //?} else {
    /*public FriendlyByteBuf getBuffer() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeUtf(modVersion());
        return buf;
    }
    *///?}

    public ResourceLocation id() {
        return HELLO_PAYLOAD_ID;
    }

}
