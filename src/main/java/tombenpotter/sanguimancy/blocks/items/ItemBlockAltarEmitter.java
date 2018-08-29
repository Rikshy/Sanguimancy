package tombenpotter.sanguimancy.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockAltarEmitter extends ItemBlock {

    public ItemBlockAltarEmitter(Block block) {
        super(block);

        setRegistryName(block.getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (!GuiScreen.isShiftKeyDown())
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
        else {
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.place.corner.1"));
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.place.corner.2"));
        }
    }
}
