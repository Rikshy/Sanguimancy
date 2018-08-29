package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tiles.TileAltarDiviner;
import tombenpotter.sanguimancy.util.RandomUtils;

import javax.annotation.Nonnull;

public class BlockAltarDiviner extends BlockContainer {

    public BlockAltarDiviner(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.creativeTab);
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new TileAltarDiviner();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tmp = world.getTileEntity(pos);
        if (tmp instanceof TileAltarDiviner) {
            TileAltarDiviner tile = (TileAltarDiviner)tmp;

            if (player.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && !tile.getInventory(null).getStackInSlot(0).isEmpty()) {
                ItemStack stack = tile.getInventory(null).getStackInSlot(0);
                tile.getInventory(null).extractItem(0, stack.getCount(), false);
                player.inventory.addItemStackToInventory(stack);

                world.notifyBlockUpdate(pos, state, state, 3);
            } else if (!player.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && tile.getInventory(null).getStackInSlot(0).isEmpty()) {
                ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
                tile.getInventory(null).setStackInSlot(0, stack.copy());

                if (!player.capabilities.isCreativeMode) {
                    player.getHeldItem(EnumHand.MAIN_HAND).setCount(0);
                }
                world.notifyBlockUpdate(pos, state, state, 3);
            }
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        RandomUtils.dropItems(world, pos);
        super.breakBlock(world, pos, state);
    }
}
