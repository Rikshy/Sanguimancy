package tombenpotter.sanguimancy.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.tiles.TileAltarEmitter;

import javax.annotation.Nonnull;

public class BlockAltarEmitter extends BlockContainer {

    public BlockAltarEmitter(Material material) {
        super(material);
        setHardness(5.0F);
        setCreativeTab(Sanguimancy.creativeTab);
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World world, int var2) {
        return new TileAltarEmitter();
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess access, BlockPos pos, EnumFacing side) {
        TileEntity tmp = access.getTileEntity(pos);
        if (tmp instanceof TileAltarEmitter) {
            if (((TileAltarEmitter) tmp).overAsked)
                return 15;
        }
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tmp = world.getTileEntity(pos);
        if (tmp instanceof TileAltarEmitter && !world.isRemote) {
            TileAltarEmitter tile = (TileAltarEmitter) tmp;
            if (!player.isSneaking()) {
                tile.bloodAsked += 100;
                player.sendMessage(new TextComponentString(I18n.format("chat.Sanguimancy.blood.required") + ": " + String.valueOf(tile.bloodAsked)));
            } else if (tile.bloodAsked >= 100) {
                tile.bloodAsked -= 100;
                player.sendMessage(new TextComponentString(I18n.format("chat.Sanguimancy.blood.required") + ": " + String.valueOf(tile.bloodAsked)));
                world.notifyNeighborsOfStateChange(pos, this, true);
            }
            world.notifyBlockUpdate(pos, state, state, 3);
            return true;
        }
        return false;
    }

}
