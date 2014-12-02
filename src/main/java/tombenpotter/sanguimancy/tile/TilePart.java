package tombenpotter.sanguimancy.tile;

import net.minecraft.nbt.NBTTagCompound;
import tombenpotter.sanguimancy.util.enums.EnumSNType;

public class TilePart extends TileBaseSNPart {

    private NBTTagCompound custoomNBTTag;

    public TilePart() {
        custoomNBTTag = new NBTTagCompound();
    }

    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        custoomNBTTag = tagCompound.getCompoundTag("customNBTTag");
    }

    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setTag("customNBTTag", custoomNBTTag);
    }

    @Override
    public NBTTagCompound getCustomNBTTag() {
        return custoomNBTTag;
    }

    @Override
    public void setCustomNBTTag(NBTTagCompound tag) {
        custoomNBTTag = tag;
    }

    @Override
    public EnumSNType getType() {
        return EnumSNType.ITEM;
    }
}
