package br.com.twinsflammer.api.spigot.listeners.general;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by @SrGutyerrez
 */
public class WeatherChangeListener implements Listener {
    @EventHandler
    public void onChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) event.setCancelled(true);
    }
}
