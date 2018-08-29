package tombenpotter.sanguimancy.tiles;

import WayofTime.bloodmagic.block.BlockAltar;
import WayofTime.bloodmagic.tile.TileAltar;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import tombenpotter.sanguimancy.api.tiles.TileBase;

public class TileAltarEmitter extends TileBase implements ITickable {

    public int bloodAsked;
    public boolean overAsked;
    public boolean oldOverAsked;

    public TileAltarEmitter() {
        customNBTTag = new NBTTagCompound();
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            for (EnumFacing dir : EnumFacing.HORIZONTALS) {
                BlockPos newPos = pos.add(dir.getDirectionVec());
                if (!world.isAirBlock(newPos) && world.getBlockState(newPos).getBlock() instanceof BlockAltar) {
                    TileEntity tmp = world.getTileEntity(newPos);
                    if (tmp instanceof TileAltar) {
                        int blood = ((TileAltar)tmp).getCurrentBlood();

                        if (overAsked != oldOverAsked)
                            oldOverAsked = overAsked;

                        if (bloodAsked > 0 && blood >= bloodAsked) {
                            oldOverAsked = overAsked;
                            overAsked = true;
                        } else {
                            oldOverAsked = overAsked;
                            overAsked = false;
                        }

                        if (overAsked != oldOverAsked)
                            world.notifyNeighborsOfStateChange(pos, world.getBlockState(pos).getBlock(), true);
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        bloodAsked = tagCompound.getInteger("bloodAsked");
        overAsked = tagCompound.getBoolean("overAsked");
        oldOverAsked = tagCompound.getBoolean("oldOverAsked");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("bloodAsked", bloodAsked);
        tagCompound.setBoolean("overAsked", overAsked);
        tagCompound.setBoolean("oldOverAsked", oldOverAsked);

        return tagCompound;
    }
}
