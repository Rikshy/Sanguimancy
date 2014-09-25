package tombenpotter.sanguimancy.rituals;

import WayofTime.alchemicalWizardry.api.alchemy.energy.ReagentRegistry;
import WayofTime.alchemicalWizardry.api.rituals.IMasterRitualStone;
import WayofTime.alchemicalWizardry.api.rituals.RitualComponent;
import WayofTime.alchemicalWizardry.api.rituals.RitualEffect;
import WayofTime.alchemicalWizardry.api.soulNetwork.LifeEssenceNetwork;
import WayofTime.alchemicalWizardry.api.soulNetwork.SoulNetworkHandler;
import WayofTime.alchemicalWizardry.common.spell.complex.effect.SpellHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import tombenpotter.sanguimancy.util.TreeFinder;

import java.util.ArrayList;
import java.util.List;

public class RitualEffectTreeFelling extends RitualEffect {

    public static final int crystallosDrain = 10;
    public int X;
    public int Y;
    public int Z;

    @Override
    public boolean startRitual(IMasterRitualStone ritualStone, EntityPlayer player) {
        World world = ritualStone.getWorld();
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 3; j <= y; j++) {
                for (int k = z - 1; k <= z + 1; k++) {
                    if (world.getBlock(i, j, k).isWood(world, i, j, k)) {
                        X = i;
                        Y = j;
                        Z = k;
                        System.out.println("I found a log");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void performEffect(IMasterRitualStone ritualStone) {
        String owner = ritualStone.getOwner();
        World worldSave = MinecraftServer.getServer().worldServers[0];
        LifeEssenceNetwork data = (LifeEssenceNetwork) worldSave.loadItemData(LifeEssenceNetwork.class, owner);
        if (data == null) {
            data = new LifeEssenceNetwork(owner);
            worldSave.setItemData(owner, data);
        }
        int currentEssence = data.currentEssence;
        World world = ritualStone.getWorld();
        if (world.getWorldTime() % 10 != 5) {
            return;
        }
        int x = ritualStone.getXCoord();
        int y = ritualStone.getYCoord();
        int z = ritualStone.getZCoord();
        TileEntity tile = world.getTileEntity(x, y + 1, z);
        IInventory tileEntity;
        if (tile instanceof IInventory) {
            tileEntity = (IInventory) tile;
        } else {
            return;
        }
        if (tileEntity.getSizeInventory() <= 0) {
            return;
        }
        boolean hasRoom = false;
        for (int i = 0; i < tileEntity.getSizeInventory(); i++) {
            if (tileEntity.getStackInSlot(i) == null) {
                hasRoom = true;
                break;
            }
        }
        if (!hasRoom) {
            return;
        }
        boolean hasCrystallos = this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, crystallosDrain, false);
        boolean isSilkTouch = hasCrystallos;
        if (currentEssence < this.getCostPerRefresh()) {
            EntityPlayer entityOwner = SpellHelper.getPlayerForUsername(owner);
            if (entityOwner == null) {
                return;
            }
            SoulNetworkHandler.causeNauseaToPlayer(owner);
        } else {
            for (int i = 0; i < 6; i++) {
                SpellHelper.sendIndexedParticleToAllAround(world, x, y, z, 20, world.provider.dimensionId, 3, x, y, z);
            }
            TreeFinder treeFinder = new TreeFinder();
            int i = 0;
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                if (treeFinder.isAdjacentBlockLog(world, X, Y, Y)[i] || treeFinder.isAdjacentBlockLeaves(world, X, Y, Z)[i]) {
                    Block block = world.getBlock(X + dir.offsetX, Y + dir.offsetY, Z + dir.offsetZ);
                    int meta = world.getBlockMetadata(X + dir.offsetX, Y + dir.offsetY, Z + dir.offsetZ);

                    if (isSilkTouch && block.canSilkHarvest(world, null, X + dir.offsetX, Y + dir.offsetY, Z + dir.offsetZ, meta)) {
                        ItemStack item = new ItemStack(block, 1, meta);
                        ItemStack copyStack = item.copyItemStack(item);
                        SpellHelper.insertStackIntoInventory(copyStack, tileEntity);
                        if (copyStack.stackSize > 0) {
                            world.spawnEntityInWorld(new EntityItem(world, x + 0.4, y + 2, z + 0.5, copyStack));
                        }
                        if (hasCrystallos) {
                            this.canDrainReagent(ritualStone, ReagentRegistry.crystallosReagent, crystallosDrain, true);
                        }
                    }
                    world.setBlockToAir(X + dir.offsetX, Y + dir.offsetY, Z + dir.offsetZ);
                    data.currentEssence = currentEssence - this.getCostPerRefresh();
                    data.markDirty();
                    return;
                }
                X = X + dir.offsetX;
                Y = Y + dir.offsetY;
                Z = Z + dir.offsetZ;
                i = i + 1;
            }
        }
    }

    @Override
    public int getCostPerRefresh() {
        return 30;
    }

    @Override
    public List<RitualComponent> getRitualComponentList() {
        ArrayList<RitualComponent> fellingRitual = new ArrayList();
        fellingRitual.add(new RitualComponent(1, 0, 0, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(-1, 0, 0, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(0, 0, 1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(0, 0, -1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(1, 1, 1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(-1, 1, 1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(-1, 1, -1, RitualComponent.EARTH));
        fellingRitual.add(new RitualComponent(1, 1, -1, RitualComponent.EARTH));
        return fellingRitual;
    }
}
