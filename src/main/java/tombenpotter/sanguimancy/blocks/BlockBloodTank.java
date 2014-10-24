package tombenpotter.sanguimancy.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tile.TileBloodTank;
import tombenpotter.sanguimancy.util.RandomUtils;

import java.util.ArrayList;

public class BlockBloodTank extends BlockContainer {

    public BlockBloodTank(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.tabSanguimancy);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBloodTank();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {
        this.blockIcon = ir.registerIcon(Sanguimancy.texturePath + ":BloodTank");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileBloodTank fluidHandler = (TileBloodTank) world.getTileEntity(x, y, z);
        if (RandomUtils.fillHandlerWithContainer(world, fluidHandler, player)) {
            return true;
        }
        if (RandomUtils.fillContainerFromHandler(world, fluidHandler, player, fluidHandler.tank.getFluid())) {
            return true;
        }
        if (FluidContainerRegistry.isContainer(player.getCurrentEquippedItem())) {
            return true;
        }
        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        this.dropBlockAsItem(world, x, y, z, meta, 0);
        super.onBlockHarvested(world, x, y, z, meta, player);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> list = new ArrayList();
        if (world.getTileEntity(x, y, z) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(x, y, z);
            ItemStack drop = new ItemStack(this);
            NBTTagCompound tag = new NBTTagCompound();
            tile.tank.writeToNBT(tag);
            tag.setInteger("capacity", tile.capacity);
            drop.stackTagCompound = tag;
            list.add(drop);
        }
        return list;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack) {
        if (world.getTileEntity(x, y, z) instanceof TileBloodTank) {
            TileBloodTank tile = (TileBloodTank) world.getTileEntity(x, y, z);
            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null) {
                tile.tank.readFromNBT(tag);
                tile.capacity = tag.getInteger("capacity");
            }
        }
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}