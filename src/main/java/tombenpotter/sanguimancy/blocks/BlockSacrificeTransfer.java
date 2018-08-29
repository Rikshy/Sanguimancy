package tombenpotter.sanguimancy.blocks;

import WayofTime.bloodmagic.core.data.SoulNetwork;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.inversion.CorruptionHandler;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import WayofTime.bloodmagic.util.helper.PlayerHelper;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.items.ItemPlayerSacrificer;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.client.particle.EntityColoredFlameFX;
import tombenpotter.sanguimancy.tiles.TileSacrificeTransfer;
import tombenpotter.sanguimancy.util.RandomUtils;
import tombenpotter.sanguimancy.util.SanguimancyItemStacks;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockSacrificeTransfer extends BlockContainer {

    public BlockSacrificeTransfer(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.creativeTab);
    }


    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int meta) {
        return new TileSacrificeTransfer();
    }

    @Override
    public void breakBlock(World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        RandomUtils.dropItems(world, pos);

        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tmp = world.getTileEntity(pos);
        if (!world.isRemote && tmp instanceof TileSacrificeTransfer) {
            TileSacrificeTransfer tile = (TileSacrificeTransfer) tmp;
            ItemStack heldItem = player.getHeldItem(hand);

            if (tile.getInventory(null).getStackInSlot(0).isEmpty() && !heldItem.isEmpty()) {
                ItemStack input = heldItem.copy();
                input.setCount(1);
                heldItem.shrink(1);
                tile.getInventory(null).insertItem(0, input, false);
            } else if (!tile.getInventory(null).getStackInSlot(0).isEmpty() && heldItem.isEmpty()) {
                EntityItem invItem = new EntityItem(tile.getWorld(), player.posX, player.posY + 0.25, player.posZ, tile.getInventory(null).getStackInSlot(0));
                world.spawnEntity(invItem);
                tile.getInventory(null).extractItem(0, tile.getInventory(null).getStackInSlot(0).getCount(), false);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        TileEntity tmp = world.getTileEntity(pos);
        if (!world.isRemote && tmp instanceof TileSacrificeTransfer) {
            TileSacrificeTransfer tile = (TileSacrificeTransfer) tmp;
            if (entity instanceof EntityPlayer && !tile.getInventory(null).getStackInSlot(0).isEmpty() && (tile.getInventory(null).getStackInSlot(0).isItemEqual(SanguimancyItemStacks.focusedPlayerSacrificer) || tile.getInventory(null).getStackInSlot(0).isItemEqual(SanguimancyItemStacks.wayToDie))) {
                ItemStack stack = tile.getInventory(null).getStackInSlot(0);
                EntityPlayer player = (EntityPlayer) entity;
                RandomUtils.checkAndSetCompound(stack);

                String uuid = "nothing";

                if (stack.getTagCompound().getString("thiefUUID").equals(PlayerHelper.getUUIDFromPlayer(player).toString())) {
                    uuid = stack.getTagCompound().getString("thiefUUID");
                    //SoulCorruptionHelper.addCorruption(player, 2); TODO?
                } else if (stack.getTagCompound().getString("ownerUUID").equals(PlayerHelper.getUUIDFromPlayer(player).toString())) {
                    uuid = stack.getTagCompound().getString("ownerUUID");
                } else {
                    player.sendMessage(new TextComponentString(I18n.format("info.Sanguimancy.tooltip.sacrifice.transfer")));
                    world.spawnEntity(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), false));
                    player.setFire(100);
                }

                if (!uuid.equals("nothing")) {
                    SoulNetwork network = NetworkHelper.getSoulNetwork(uuid);
                    network.add(new SoulTicket(stack.getTagCompound().getInteger("bloodStolen")), Integer.MAX_VALUE);
                    player.setFire(120);
                    tile.getInventory(null).extractItem(0, 1, false);
                }
            }
        }
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
        TileEntity tmp = world.getTileEntity(pos);
        if (tmp instanceof TileSacrificeTransfer) {
            TileSacrificeTransfer tile = (TileSacrificeTransfer) tmp;
            if (!tile.getInventory(null).getStackInSlot(0).isEmpty() && tile.getInventory(null).getStackInSlot(0).getItem() instanceof ItemPlayerSacrificer) {
                for (float i = 0; i <= 7; i += 0.1) {
                    Particle fire = new EntityColoredFlameFX(world, pos.getX() - 0.5, pos.getY() + i, pos.getZ() + 0.5, 0, 0.1, 0, 255, 72, 0);
                    Minecraft.getMinecraft().effectRenderer.addEffect(fire);
                    Particle water = new EntityColoredFlameFX(world, pos.getX() + 0.5, pos.getY() + i, pos.getZ() - 0.5, 0, 0.1, 0, 0, 136, 255);
                    Minecraft.getMinecraft().effectRenderer.addEffect(water);
                    Particle air = new EntityColoredFlameFX(world, pos.getX() + 1.5, pos.getY() + i, pos.getZ() + 0.5, 0, 0.1, 0, 153, 204, 204);
                    Minecraft.getMinecraft().effectRenderer.addEffect(air);
                    Particle earth = new EntityColoredFlameFX(world, pos.getX() + 0.5, pos.getY() + i, pos.getZ() + 1.5, 0, 0.1, 0, 40, 166, 32);
                    Minecraft.getMinecraft().effectRenderer.addEffect(earth);
                    Particle darkness = new EntityColoredFlameFX(world, pos.getX() + 0.5, pos.getY() + i, pos.getZ() + 0.5, 0, -0.2, 0, 0, 0, 0);
                    Minecraft.getMinecraft().effectRenderer.addEffect(darkness);
                }
            }
        }
    }
}
