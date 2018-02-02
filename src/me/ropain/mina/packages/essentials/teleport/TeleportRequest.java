package me.ropain.mina.packages.essentials.teleport;

import me.ropain.mina.core.Mina;
import me.ropain.mina.core.l10n.L10n;
import me.ropain.mina.core.l10n.LocalizableValues;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Contains functionality to send, accept and cancel teleport requests.
 *
 * @author MrRopain
 */
public class TeleportRequest {

    public static final TeleportRequest NONE = new TeleportRequest();

    private static final int REQUEST_TIMEOUT = 30;
    private static final String PATH_RESPONSE_CANCEL_FROM = "other/teleport_cancel/player_from";
    private static final String PATH_RESPONSE_CANCEL_TO = "other/teleport_cancel/player_to";

    private static final List<TeleportRequest> requests = new ArrayList<>();

    public final Player from;
    public final Player to;

    /**
     * Only used to instantiate the None type.
     */
    private TeleportRequest() {
        from = null;
        to = null;
    }

    /**
     * Private to prevent instantiation from outside.
     */
    private TeleportRequest(Player from, Player to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Accepts a request.
     */
    public void accept() {
        Teleporter.teleport(from, to);
        requests.remove(this);
    }

    /**
     * Cancels a request.
     */
    public void cancel() {
        requests.remove(this);

        from.sendMessage(ChatTypes.CHAT, Text.of(L10n.localize(PATH_RESPONSE_CANCEL_FROM, LocalizableValues.builder()
                .add("player", to.getName())
                .build())));

        to.sendMessage(ChatTypes.CHAT, Text.of(L10n.localize(PATH_RESPONSE_CANCEL_TO, LocalizableValues.builder()
                .add("player", from.getName())
                .build())));
    }

    /**
     * Creates a new teleport request and then sends it.
     */
    public static void send(Player from, Player to) {
        TeleportRequest request = new TeleportRequest(from, to);

        Task.builder()
                .delay(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .execute(() -> request.cancel())
                .submit(Mina.getInstance());

        requests.add(request);
    }

    /**
     * Returns whether or not a matching request already exists.
     */
    public static boolean exists(Player from, Player to) {
        return getByPlayerFrom(from) == to;
    }

    /**
     * Tries to find a request sent by the given player.
     * Returns none if none is found.
     */
    public static TeleportRequest getByPlayerFrom(Player player) {

        for (TeleportRequest request : requests) {
            if (request.from == player) {
                return request;
            }
        }

        return NONE;
    }

    /**
     * Tries to find a request received by the given player.
     * Returns none if none is found.
     */
    public static TeleportRequest getByPlayerTo(Player player) {

        for (TeleportRequest request : requests) {
            if (request.to == player) {
                return request;
            }
        }

        return NONE;
    }
}
