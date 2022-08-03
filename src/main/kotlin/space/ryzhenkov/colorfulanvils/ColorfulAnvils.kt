package space.ryzhenkov.colorfulanvils

import net.fabricmc.api.ModInitializer
import java.util.logging.Logger

class ColorfulAnvils:ModInitializer {
    override fun onInitialize() {
        Logger.getLogger("ColorfulAnvils").info("Time to bring color juice for anvils!")
    }
}

