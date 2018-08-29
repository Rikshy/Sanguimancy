package tombenpotter.sanguimancy.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tombenpotter.sanguimancy.tiles.*;

public class TileRegistry {

    public static void registerTEs() {
        GameRegistry.registerTileEntity(TileAltarEmitter.class, new ResourceLocation("TileAltarEmitter"));
        GameRegistry.registerTileEntity(TileAltarDiviner.class, new ResourceLocation("TileAltarDiviner"));
        GameRegistry.registerTileEntity(TileAltarManipulator.class, new ResourceLocation("TileAltarManipulator"));
        GameRegistry.registerTileEntity(TileBloodInterface.class, new ResourceLocation("TileBloodInterface"));
        GameRegistry.registerTileEntity(TileSacrificeTransfer.class, new ResourceLocation("TileSacrificeTransfer"));
        GameRegistry.registerTileEntity(TileBloodCleaner.class, new ResourceLocation("TileBloodCleaner"));
    }
}
