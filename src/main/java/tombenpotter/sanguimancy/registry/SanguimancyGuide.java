package tombenpotter.sanguimancy.registry;

import WayofTime.bloodmagic.compat.guideapi.GuideBloodMagic;
import WayofTime.bloodmagic.compat.guideapi.entry.EntryText;
import WayofTime.bloodmagic.compat.guideapi.page.PageAltarRecipe;
import WayofTime.bloodmagic.registry.ModBlocks;
import WayofTime.bloodmagic.registry.ModItems;
import amerifrance.guideapi.RegistrarGuideAPI;
import amerifrance.guideapi.api.GuideBook;
import amerifrance.guideapi.api.GuideRegistry;
import amerifrance.guideapi.api.IGuideBook;
import amerifrance.guideapi.api.IPage;
import amerifrance.guideapi.api.impl.Book;
import amerifrance.guideapi.api.impl.BookBinder;
import amerifrance.guideapi.api.impl.Category;
import amerifrance.guideapi.api.impl.Entry;
import amerifrance.guideapi.api.impl.abstraction.CategoryAbstract;
import amerifrance.guideapi.api.impl.abstraction.EntryAbstract;
import amerifrance.guideapi.api.util.PageHelper;
import amerifrance.guideapi.category.CategoryItemStack;
import amerifrance.guideapi.page.PageIRecipe;
import amerifrance.guideapi.page.PageImage;
import amerifrance.guideapi.page.PageTextImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.guide.PageCorruptionRecipe;
import tombenpotter.sanguimancy.recipes.RecipeCorruptedInfusion;
import tombenpotter.sanguimancy.util.ConfigHandler;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@GuideBook(priority = EventPriority.HIGHEST)
public class SanguimancyGuide implements IGuideBook {

    public static BookBinder sanguimancyGuide = new BookBinder(new ResourceLocation(Sanguimancy.modid, "guide_book"));
    public static List<CategoryAbstract> categories = new ArrayList<>();

    public static void registerGuide() {
        createLoreEntries();
        createItemEntries();
        createRitualEntries();
        createBlockEntries();
        GuideRegistry.registerBook(sanguimancyGuide);
        GameRegistry.addRecipe(new ShapedOreRecipe(GuideRegistry.getItemStackForBook(sanguimancyGuide), "X", "Y", "O", 'X', "dyeBlack", 'O', ModItems.weakBloodOrb, 'Y', Items.writable_book));
    }

    private CategoryAbstract createLoreEntries() {
        Category cat = new CategoryItemStack("guide.Sanguimancy.category.lore", SanguimancyItemStacks.attunnedPlayerSacrificer);

        ArrayList<IPage> sacrificeMagicPages = new ArrayList<>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificeMagic")));
        cat.addEntry("entry_sacrmagic", new Entry(sacrificeMagicPages, "guide.Sanguimancy.entryName.sacrificeMagic"));

        ArrayList<IPage> soulCorruptionPages = new ArrayList<>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruption")));
        cat.addEntry("entry_corruption", new Entry(soulCorruptionPages, "guide.Sanguimancy.entryName.corruption"));

        ArrayList<IPage> protectionPages = new ArrayList<>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.protection.1")));
        protectionPages.add(new PageTextImage("guide.Sanguimancy.entry.protection.picture.1", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/CompleteCrystallizerMultiblock.png"), true));
        protectionPages.add(new PageTextImage("guide.Sanguimancy.entry.protection.picture.2", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/CrystallizerMultiblock.png"), true));
        protectionPages.add(new PageTextImage("guide.Sanguimancy.entry.protection.picture.3", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/WaterCrystallizerMultiblock.png"), true));
        protectionPages.add(new PageTextImage("guide.Sanguimancy.entry.protection.picture.4", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/LavaCrystallizerMultiblock.png"), true));
        protectionPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.protection.2")));
        cat.addEntry("entry_protection", new EntryText(protectionPages, "guide.Sanguimancy.entryName.protection"));

        ArrayList<IPage> corruptionApplicationsPages = new ArrayList<>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.applications")));
        cat.addEntry("entry_apllications", new EntryText(corruptionApplicationsPages, "guide.Sanguimancy.entryName.apllications"));

        return cat;
    }

    private CategoryAbstract createItemEntries() {
        Category cat = new CategoryItemStack("guide.Sanguimancy.category.items", SanguimancyItemStacks.corruptedSword);

        ArrayList<IPage> bloodAmuletPages = new ArrayList<>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.bloodAmulet"), SanguimancyItemStacks.bloodAmulet));
        bloodAmuletPages.add(new PageIRecipe(RecipesRegistry.bloodAmulet));
        cat.addEntry( "entry_bloodamulet", new EntryText(bloodAmuletPages, "item.Sanguimancy.bloodAmulet.name"));

        ArrayList<IPage> chunkClaimerPages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.chunkClaimer"), SanguimancyItemStacks.chunkClaimer));
        chunkClaimerPages.add(new PageIRecipe(RecipesRegistry.chunkClaimer));
        cat.addEntry( "entry_chunkclaimer", new EntryText(chunkClaimerPages, "item.Sanguimancy.chunkClaimer.name"));

        ArrayList<IPage> craftingItemsPages = new ArrayList<>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.craftingItems")));
        craftingItemsPages.add(new PageCorruptionRecipe(RecipesRegistry.corruptedDemonShard));
        craftingItemsPages.add(new PageAltarRecipe(RecipesRegistry.imbuedStick));
        craftingItemsPages.add(new PageIRecipe(RecipesRegistry.corruptedMineral));
        cat.addEntry( "entry_crafting", new EntryText(craftingItemsPages, "guide.Sanguimancy.entryName.craftingItems"));

        ArrayList<IPage> corruptionCatalystPages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptionCatalyst.1"), SanguimancyItemStacks.corruptionCatalist));
        corruptionCatalystPages.add(new PageAltarRecipe(RecipesRegistry.corruptionCatalyst));
        corruptionCatalystPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptionCatalyst.2")));
        for (RecipeCorruptedInfusion r : RecipeCorruptedInfusion.getAllRecipes()) {
            corruptionCatalystPages.add(new PageCorruptionRecipe(r));
        }
        cat.addEntry( "entry_corruption_cat", new EntryText(corruptionCatalystPages, "item.Sanguimancy.corruptionCatalist.name"));

        ArrayList<IPage> corruptionReaderPages = new ArrayList<IPage>(I18n.format("guide.Sanguimancy.entry.corruptionReader"), SanguimancyItemStacks.corruptionReader);
        corruptionReaderPages.add(new PageIRecipe(RecipesRegistry.corruptionReader));
        cat.addEntry( "entry_corruption_reader", new EntryText(corruptionReaderPages, "item.Sanguimancy.soulCorruption.reader.name"));

        ArrayList<IPage> oreLumpPages = new ArrayList<IPage>(I18n.format("guide.Sanguimancy.entry.oreLump"), SanguimancyItemStacks.oreLump);
        for (RecipeCorruptedInfusion r : RecipesRegistry.oreLumpRecipes) {
            oreLumpPages.add(new PageCorruptionRecipe(r));
        }
        //for (RecipeBloodCleanser r : RecipesRegistry.oreLumpCleansing) {
        //    oreLumpPages.add(new EntryBloodCleanserRecipe(r));
        // }
        cat.addEntry( "entry_tooltip", new EntryText(oreLumpPages, "info.Sanguimancy.tooltip.any"));

        ArrayList<IPage> playerSacrificingStonesPages = new ArrayList<>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificingStones.1"), SanguimancyItemStacks.unattunedPlayerSacrificer));
        playerSacrificingStonesPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.unattunedPlayerSacrificer));
        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificingStones.2"), SanguimancyItemStacks.attunnedPlayerSacrificer));
        playerSacrificingStonesPages.add(new PageAltarRecipe(RecipesRegistry.attunedPlayerSacrificer));
        playerSacrificingStonesPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sacrificingStones.3"), SanguimancyItemStacks.focusedPlayerSacrificer));
        cat.addEntry( "entry_sacrstones", new EntryText(playerSacrificingStonesPages, "Player Sacrificing Stones"));

        ArrayList<IPage> wandPages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.wand"), SanguimancyItemStacks.wand));
        wandPages.add(new PageIRecipe(RecipesRegistry.wand));
        cat.addEntry( "entry_wand", new EntryText(wandPages, "item.Sanguimancy.spellWand.name"));

        ArrayList<IPage> corruptedAxePages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptedAxe"), SanguimancyItemStacks.corruptedAxe));
        corruptedAxePages.add(new PageIRecipe(RecipesRegistry.corruptedAxe));
        cat.addEntry( "entry_curr_axe", new EntryText(corruptedAxePages, "item.Sanguimancy.corruptedAxe.name"));

        ArrayList<IPage> corruptedPickaxePages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptedPickaxe"), SanguimancyItemStacks.corruptedPickaxe));
        corruptedPickaxePages.add(new PageIRecipe(RecipesRegistry.corruptedPickaxe));
        cat.addEntry( "entry_curr_pickaxe",new EntryText(corruptedPickaxePages, "item.Sanguimancy.corruptedPickaxe.name"));

        ArrayList<IPage> corruptedShovelPages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptedShovel"), SanguimancyItemStacks.corruptedShovel));
        corruptedShovelPages.add(new PageIRecipe(RecipesRegistry.corruptedShovel));
        cat.addEntry( "entry_curr_shov",new EntryText(corruptedShovelPages, "item.Sanguimancy.corruptedShovel.name"));

        ArrayList<IPage> corruptedSwordPages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptedSword"), SanguimancyItemStacks.corruptedSword));
        corruptedSwordPages.add(new PageIRecipe(RecipesRegistry.corruptedSword));
        cat.addEntry( "entry_curr_sword",new EntryText(corruptedSwordPages, "item.Sanguimancy.corruptedSword.name"));

        ArrayList<IPage> soulTranporterPages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.soulTransporter"), SanguimancyItemStacks.soulTransporter));
        soulTranporterPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.soulTransporter));
        cat.addEntry( "entry_soultrans",new EntryText(soulTranporterPages, "item.Sanguimancy.soulTransporter.name"));

        ArrayList<IPage> telepositionSigilIPages = new ArrayList<IPage>(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.telepositionSigil"), SanguimancyItemStacks.telepositionSigil));
        telepositionSigilIPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.telepositionSigil));
        cat.addEntry( "entry_tele_sigil",new EntryText(telepositionSigilIPages, "item.Sanguimancy.telepositionSigil.name"));

        ArrayList<IPage> transpositionSigilPages = new ArrayList<IPage>(I18n.format("guide.Sanguimancy.entry.transpositionSigil"), SanguimancyItemStacks.transpositionSigil));
        transpositionSigilPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.transpositionSigil));
        cat.addEntry( "entry_trans_sigil",new EntryText(transpositionSigilPages, "item.Sanguimancy.transpositionSigil.name"));

        return cat;
    }

    public static void createRitualEntries() {
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        if (ConfigHandler.enableAltarBuilder) {
            ArrayList<IPage> altarBuilderPages = new ArrayList<IPage>();
            altarBuilderPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.altar.builder")));
            entries.add(new EntryUniText(altarBuilderPages, "ritual.Sanguimancy.altar.builder"));
        }

        if (ConfigHandler.enableDrillOfTheDead) {
            ArrayList<IPage> drillOfTheDeadPages = new ArrayList<IPage>();
            drillOfTheDeadPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.drillOfTheDead")));
            entries.add(new EntryUniText(drillOfTheDeadPages, "ritual.Sanguimancy.drill.dead"));
        }

        if (ConfigHandler.enableFelling) {
            ArrayList<IPage> timbermanPages = new ArrayList<IPage>();
            timbermanPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.timberman")));
            entries.add(new EntryUniText(timbermanPages, "ritual.Sanguimancy.feller"));
            ;
        }

        if (ConfigHandler.enableIllumination) {
            ArrayList<IPage> enlightenmentPages = new ArrayList<IPage>();
            enlightenmentPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.enlightenment")));
            entries.add(new EntryUniText(enlightenmentPages, "ritual.Sanguimancy.illumination"));
        }

        if (ConfigHandler.enableVulcanosFrigius) {
            ArrayList<IPage> vulcanosFrigiusPages = new ArrayList<IPage>();
            vulcanosFrigiusPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.vulcanosFrigius")));
            entries.add(new EntryUniText(vulcanosFrigiusPages, "ritual.Sanguimancy.vulcanos.frigius"));
        }

        if (ConfigHandler.enablePlacer) {
            ArrayList<IPage> fillerPages = new ArrayList<IPage>();
            fillerPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.filler")));
            entries.add(new EntryUniText(fillerPages, "ritual.Sanguimancy.placer"));
        }

        if (ConfigHandler.enablePortal) {
            ArrayList<IPage> portalPages = new ArrayList<IPage>();
            portalPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.portal.1")));
            portalPages.add(new PageUnlocImage("guide.Sanguimancy.entry.portal.picture", new ResourceLocation(Sanguimancy.texturePath + ":textures/screenshots/PortalExample.png"), true));
            portalPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.portal.2")));
            entries.add(new EntryUniText(portalPages, "ritual.Sanguimancy.portal"));
        }

        if (ConfigHandler.enablePump) {
            ArrayList<IPage> pumpPages = new ArrayList<IPage>();
            pumpPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.pump")));
            entries.add(new EntryUniText(pumpPages, "ritual.Sanguimancy.pump"));
        }

        if (ConfigHandler.enableTrash) {
            ArrayList<IPage> greatDeletionPages = new ArrayList<IPage>();
            greatDeletionPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.greatDeletion")));
            entries.add(new EntryUniText(greatDeletionPages, "ritual.Sanguimancy.trash"));
        }

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.rituals", new ItemStack(ModBlocks.blockMasterStone)));
    }

    public static void createBlockEntries() {
        List<EntryAbstract> entries = new ArrayList<EntryAbstract>();

        ArrayList<IPage> altarEmitterPages = new ArrayList<IPage>();
        altarEmitterPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.altarEmitter"), SanguimancyItemStacks.altarEmitter));
        altarEmitterPages.add(new PageIRecipe(RecipesRegistry.altarEmitter));
        entries.add(new EntryUniText(altarEmitterPages, "tile.Sanguimancy.altarEmitter.name"));

        ArrayList<IPage> altarDivinerPages = new ArrayList<IPage>();
        altarDivinerPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.altarDiviner"), SanguimancyItemStacks.altarDiviner));
        altarDivinerPages.add(new PageAltarRecipe(RecipesRegistry.altarDiviner));
        entries.add(new EntryUniText(altarDivinerPages, "tile.Sanguimancy.altarDiviner.name"));

        ArrayList<IPage> altarManipulatorPages = new ArrayList<IPage>();
        altarManipulatorPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.altarManipulator"), SanguimancyItemStacks.altarManipulator));
        altarManipulatorPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.altarManipulator));
        altarManipulatorPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.sanguineShifter"), SanguimancyItemStacks.sanguineShifter));
        altarManipulatorPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.sanguineShifter));
        entries.add(new EntryUniText(altarManipulatorPages, "tile.Sanguimancy.altarManipulator.name"));

        ArrayList<IPage> bloodInterfacePages = new ArrayList<IPage>();
        bloodInterfacePages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.bloodInterface"), SanguimancyItemStacks.bloodInterface));
        bloodInterfacePages.add(new PageIRecipe(RecipesRegistry.bloodInterface));
        entries.add(new EntryUniText(bloodInterfacePages, "tile.Sanguimancy.interface.name"));

        ArrayList<IPage> decorativeBlocksPages = new ArrayList<IPage>();
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.bloodstoneSlab));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.largeBloodstoneSlab));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.bloodstoneStairs));
        decorativeBlocksPages.add(new PageIRecipe(RecipesRegistry.largeBloodstoneStairs));
        entries.add(new EntryUniText(decorativeBlocksPages, "guide.Sanguimancy.entryName.decorativeBlocks"));

        ArrayList<IPage> bloodTankPages = new ArrayList<IPage>();
        bloodTankPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.bloodTank"), SanguimancyItemStacks.bloodTank));
        for (IRecipe recipe : RecipesRegistry.bloodTank) {
            bloodTankPages.add(BloodMagicGuide.getOrbPageForRecipe(recipe));
        }
        entries.add(new EntryUniText(bloodTankPages, "tile.Sanguimancy.bloodTank.name"));

        ArrayList<IPage> corruptionCrystallizerPages = new ArrayList<IPage>();
        corruptionCrystallizerPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptionCrystallizer"), SanguimancyItemStacks.corruptionCrystallizer));
        corruptionCrystallizerPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.corruptionCrystallizer));
        entries.add(new EntryUniText(corruptionCrystallizerPages, "tile.Sanguimancy.corruptionCrystallizer.name"));

        ArrayList<IPage> manifestationsPages = new ArrayList<IPage>();
        manifestationsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.itemManifestation"), SanguimancyItemStacks.boundItem));
        manifestationsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.ritualManifestation"), SanguimancyItemStacks.ritualRepresentation));
        manifestationsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.etherealManifestation"), SanguimancyItemStacks.etherealManifestation));
        manifestationsPages.add(new PageAltarRecipe(RecipesRegistry.etherealManifestation));
        manifestationsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.soulBranch"), SanguimancyItemStacks.simpleBranch));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.simpleBranch));
        manifestationsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.simpleKnot"), SanguimancyItemStacks.simpleKnot));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.simpleKnot));
        manifestationsPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.toggledKnot"), SanguimancyItemStacks.toggleKnot));
        manifestationsPages.add(new PageIRecipe(RecipesRegistry.toggledKnot));
        entries.add(new EntryUniText(manifestationsPages, "guide.Sanguimancy.entryName.manifestations"));

        ArrayList<IPage> bloodCleanserPages = new ArrayList<IPage>();
        bloodCleanserPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.bloodCleanser"), SanguimancyItemStacks.lumpCleaner));
        bloodCleanserPages.add(BloodMagicGuide.getOrbPageForRecipe(RecipesRegistry.lumpCleaner));
        //for (RecipeBloodCleanser r : RecipeBloodCleanser.getAllRecipes()) {
        //    bloodCleanserPages.add(new EntryBloodCleanserRecipe(r));
        //}
        entries.add(new EntryUniText(bloodCleanserPages, "tile.Sanguimancy.bloodCleaner.name"));

        ArrayList<IPage> soulTransferrerPages = new ArrayList<IPage>();
        soulTransferrerPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.soulTransferrer"), SanguimancyItemStacks.sacrificeTransferrer));
        soulTransferrerPages.add(new PageIRecipe(RecipesRegistry.sacrificeTransferrer));
        entries.add(new EntryUniText(soulTransferrerPages, "tile.Sanguimancy.sacrificeTransfer.name"));

        ArrayList<IPage> etherealBlocksPages = new ArrayList<IPage>();
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.etherealBlock"), SanguimancyItemStacks.etherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.corruptedEtherealBlock"), SanguimancyItemStacks.etherealCorruptedBlock));
        etherealBlocksPages.add(new PageCorruptionRecipe(RecipesRegistry.corruptedEtherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.toggledEtherealBlock"), SanguimancyItemStacks.etherealToggledBlock));
        etherealBlocksPages.add(new PageIRecipe(RecipesRegistry.toggledEtherealBlock));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.boundEtherealBlocks")));
        etherealBlocksPages.addAll(PageHelper.pagesForLongText(I18n.format("guide.Sanguimancy.entry.personalEtherealBlock"), SanguimancyItemStacks.personalEtherealBlock));
        etherealBlocksPages.add(new PageIRecipe(RecipesRegistry.personalEtherealBlock));
        entries.add(new EntryUniText(etherealBlocksPages, "guide.Sanguimancy.entryName.etherealBlocks"));

        categories.add(new CategoryItemStack(entries, "guide.Sanguimancy.category.blocks", SanguimancyItemStacks.lumpCleaner));
    }

    @Nullable
    @Override
    public Book buildBook() {
        sanguimancyGuide
                .addCategory(createLoreEntries())
                .addCategory(createItemEntries())
                .setAuthor(Sanguimancy.name)
                .setGuideTitle("guide.Sanguimancy.book.title")
                .setColor(new Color(190, 10, 0))
                .setItemName("guide.Sanguimancy.book.name")
                .setOutlineTexture(new ResourceLocation(Sanguimancy.texturePath + ":DelvingIntoTheDarkness"));
                //.setUnlocWelcomeMessage("guide.Sanguimancy.welcomeMessage");
        return sanguimancyGuide.build();
    }
}
