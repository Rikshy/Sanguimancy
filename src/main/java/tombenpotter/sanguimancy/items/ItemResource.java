package tombenpotter.sanguimancy.items;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import tombenpotter.sanguimancy.Sanguimancy;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class ItemResource extends Item {

    ArrayList<String> namesList = new ArrayList<>();

    public ItemResource() {
        setCreativeTab(Sanguimancy.creativeTab);
        setUnlocalizedName(Sanguimancy.modid + ".resource");
        setHasSubtypes(true);

        namesList.add(0, "corruptedDemonShard");
        namesList.add(1, "corruptedMineral");
        namesList.add(2, "imbuedStick");
        namesList.add(3, "etherealManifestation");
        namesList.add(4, "manipulatorUpgrade");
    }


    @Nonnull
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = namesList.get(stack.getItemDamage());
        return getUnlocalizedName() + "." + name;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        for (int i = 0; i < namesList.size(); i++) {
            items.add(new ItemStack(this, 1, i));
        }
    }
}
