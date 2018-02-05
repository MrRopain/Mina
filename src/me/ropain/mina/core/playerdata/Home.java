package me.ropain.mina.core.playerdata;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class Home implements IStorable {

    private Location<World> location;

    @Override
    public String getKey() {
        return "home";
    }

    @Override
    public void store(ConfigurationNode node) {
        node.getNode("world").setValue(location.getExtent().getName());
        node.getNode("x").setValue(location.getBlockX());
        node.getNode("y").setValue(location.getBlockY());
        node.getNode("z").setValue(location.getBlockZ());
    }

    @Override
    public void load(ConfigurationNode node) {

        String worldName = node.getNode("world").getString();
        if (worldName == null) {
            location = null;
            return;
        }


        int x = node.getNode("x").getInt();
        int y = node.getNode("y").getInt();
        int z = node.getNode("z").getInt();
        location = new Location(Sponge.getServer().getWorld(worldName).get(), x, y, z);
    }

    public void setLocation(Location<World> location) {
        this.location = location;
    }

    public Location<World> getLocation() {
        return location;
    }
}
