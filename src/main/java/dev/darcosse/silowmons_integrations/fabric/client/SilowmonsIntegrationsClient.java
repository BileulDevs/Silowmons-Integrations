package dev.darcosse.silowmons_integrations.fabric.client;

import abeshutt.staracademy.world.data.save.SafariData;
import dev.darcosse.silowmons_integrations.fabric.network.SafariUpdateS2CPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SilowmonsIntegrationsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SafariUpdateS2CPacket.ID, (payload, context) -> {
            context.client().execute(() -> {
                // DEBUG: Supprime ces lignes une fois que ça marche
                System.out.println("Paquet reçu pour: " + payload.playerUuid() + " | Temps: " + payload.timeLeft());

                // 1. Création de l'entrée (Constructeur vide obligatoire)
                SafariData.Entry newEntry = new SafariData.Entry();

                // 2. On définit le temps (vérifie si c'est 'setTimeLeft' ou 'setTime')
                newEntry.setTimeLeft(payload.timeLeft());

                // 3. Stockage dans l'objet CLIENT
                // Si .put ne marche pas, c'est .getEntries().put()
                SafariData.CLIENT.getEntries().put(payload.playerUuid(), newEntry);
            });
        });
    }
}