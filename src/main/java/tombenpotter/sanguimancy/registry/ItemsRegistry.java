package tombenpotter.sanguimancy.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.items.ItemBloodAmulet;
import tombenpotter.sanguimancy.items.ItemPlayerSacrificer;
import tombenpotter.sanguimancy.items.ItemResource;

@Mod.EventBusSubscriber
public class ItemsRegistry {

    public static Item playerSacrificer;
    public static Item bloodAmulet;
    public static Item resource;

    /*
    public static Item wand;
    public static Item corruptedSword;
    public static Item corruptedPickaxe;
    public static Item corruptedShovel;
    public static Item corruptedAxe;
    */

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        playerSacrificer = new ItemPlayerSacrificer().setRegistryName(Sanguimancy.modid, "playerSacrificer");
        registry.register(playerSacrificer);

        bloodAmulet = new ItemBloodAmulet().setRegistryName(Sanguimancy.modid, "bloodAmulet");
        registry.register(bloodAmulet);

        resource = new ItemResource().setRegistryName(Sanguimancy.modid, "resource");
        registry.register(resource);


        /*
        wand = new ItemWand();
        GameRegistry.registerItem(wand, "wand");
        corruptedSword = new ItemCorruptedSword(32);
        GameRegistry.registerItem(corruptedSword, "corruptedSword");

        corruptedPickaxe = new ItemCorruptedPickaxe(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedPickaxe, "corruptedPickaxe");

        corruptedShovel = new ItemCorruptedShovel(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedShovel, "corruptedShovel");

        corruptedAxe = new ItemCorruptedAxe(RandomUtils.corruptedMaterial);
        GameRegistry.registerItem(corruptedAxe, "corruptedAxe");
        */
    }
}
