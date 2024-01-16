package space.ryzhenkov.colorfulanvils;

import net.fabricmc.api.ModInitializer;
import org.slf4j.LoggerFactory;

public class ColorfulAnvils implements ModInitializer {
    public void onInitialize() {
        LoggerFactory.getLogger("ColorfulAnvils").info("Time to bring color juice for anvils!");
    }
}
