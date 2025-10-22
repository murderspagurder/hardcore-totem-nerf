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

public record S2CInsightsPayload(long totemCooldown, int remainingUsages) implements NetworkPayload {

    public static final ResourceLocation INSIGHTS_PAYLOAD_ID =
    //? >=1.21 {
            ResourceLocation.fromNamespaceAndPath(HardcoreTotemNerf.MOD_ID, "insights");
    //?} else {
            /*new ResourceLocation(HardcoreTotemNerf.MOD_ID, "insights");
    *///?}

    //? >=1.21 {
    public static final CustomPacketPayload.Type<S2CInsightsPayload> TYPE =
            new CustomPacketPayload.Type<>(INSIGHTS_PAYLOAD_ID);
    public static final StreamCodec<FriendlyByteBuf, S2CInsightsPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_LONG,
            S2CInsightsPayload::totemCooldown,
            ByteBufCodecs.VAR_INT,
            S2CInsightsPayload::remainingUsages,
            S2CInsightsPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    //?} else {
    /*public FriendlyByteBuf getBuffer() {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeLong(totemCooldown());
        buf.writeInt(remainingUsages());
        return buf;
    }
    *///?}

    public ResourceLocation id() {
        return INSIGHTS_PAYLOAD_ID;
    }

}
