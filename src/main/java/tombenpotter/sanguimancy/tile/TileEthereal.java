package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.api.tile.TileBase;

public class TileEthereal extends TileBase {

    public int block = 0;
    public int metadata = 0;

    public TileEthereal() {
        custoomNBTTag = new NBTTagCompound();
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.block = tagCompound.getInteger("blockID");
        this.metadata = tagCompound.getInteger("metadata");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("blockID", this.block);
        tagCompound.setInteger("metadata", this.metadata);
    }
}
