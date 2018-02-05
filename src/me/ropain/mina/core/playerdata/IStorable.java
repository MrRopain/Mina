package me.ropain.mina.core.playerdata;

import ninja.leaping.configurate.ConfigurationNode;

public interface IStorable {

    String getKey();

    void store(ConfigurationNode node);

    void load(ConfigurationNode node);
}
