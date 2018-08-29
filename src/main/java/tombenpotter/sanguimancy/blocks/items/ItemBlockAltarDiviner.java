package tombenpotter.sanguimancy.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockAltarDiviner extends ItemBlock {

    public ItemBlockAltarDiviner(Block block) {
        super(block);
        setRegistryName(block.getRegistryName());
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (!GuiScreen.isShiftKeyDown())
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.shift.info"));
        else {
            tooltip.add(I18n.format("info.Sanguimancy.tooltip.place.altar"));
        }
    }
}
