package dev.darcosse.silowmons_integrations.fabric.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record SafariUpdateS2CPacket(UUID playerUuid, long timeLeft) implements CustomPayload {
    public static final Id<SafariUpdateS2CPacket> ID = new Id<>(Identifier.of("academy", "safari_update"));

    // Codec pour transformer l'objet en octets (Network)
    public static final PacketCodec<RegistryByteBuf, SafariUpdateS2CPacket> CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC, SafariUpdateS2CPacket::playerUuid,
            PacketCodecs.VAR_LONG, SafariUpdateS2CPacket::timeLeft,
            SafariUpdateS2CPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}