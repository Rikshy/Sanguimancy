package tombenpotter.sanguimancy.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.blocks.*;
import tombenpotter.sanguimancy.blocks.items.*;

@Mod.EventBusSubscriber
public class BlocksRegistry {

    public static Block altarEmitter;
    public static Block altarDiviner;
    public static Block altarManipulator;

    public static Block bloodInterface;

    public static Block sacrificeTransfer;
    public static Block bloodCleaner;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        altarEmitter = new BlockAltarEmitter(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockAltarEmitter").setUnlocalizedName(Sanguimancy.modid + ".altarEmitter");
        registry.register(altarEmitter);

        altarDiviner = new BlockAltarDiviner(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockAltarDiviner").setUnlocalizedName(Sanguimancy.modid + ".altarDiviner");
        registry.register(altarDiviner);

        altarManipulator = new BlockAltarManipulator(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockAltarManipulator").setUnlocalizedName(Sanguimancy.modid + ".altarManipulator");
        registry.register(altarManipulator);

        bloodInterface = new BlockBloodInterface(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockBloodInterface").setUnlocalizedName(Sanguimancy.modid + ".interface");
        registry.register(bloodInterface);

        sacrificeTransfer = new BlockSacrificeTransfer(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockSacrificeTransfer").setUnlocalizedName(Sanguimancy.modid + ".sacrificeTransfer");
        registry.register(sacrificeTransfer);

        bloodCleaner = new BlockBloodCleaner(Material.IRON).setRegistryName(Sanguimancy.modid, "BlockBloodCleaner").setUnlocalizedName(Sanguimancy.modid + ".bloodCleaner");
        registry.register(bloodCleaner);
    }

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(new ItemBlockAltarEmitter(altarEmitter));
        registry.register(new ItemBlockAltarDiviner(altarDiviner));
        registry.register(new ItemBlockAltarManipulator(altarManipulator));
        registry.register(new ItemBlock(bloodInterface));
        registry.register(new ItemBlock(sacrificeTransfer));
        registry.register(new ItemBlockBloodCleaner(bloodCleaner));
    }
}
