package tombenpotter.sanguimancy.api.bloodutils.api.classes.guide;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import tombenpotter.sanguimancy.Sanguimancy;
import tombenpotter.sanguimancy.api.bloodutils.api.classes.guide.elements.ElementCategory;
import tombenpotter.sanguimancy.api.bloodutils.api.compact.Category;
import tombenpotter.sanguimancy.api.bloodutils.api.registries.EntryRegistry;

public class GuiCategories extends GuiScreen {
    private static final ResourceLocation gui = new ResourceLocation(Sanguimancy.texturePath + ":textures/gui/guide/front.png");
    int gwidth = 192;
    int gheight = 192;
    int x, y;
    ElementCategory[] categories = new ElementCategory[EntryRegistry.categories.size()];
    EntityPlayer player;

    public GuiCategories(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void initGui() {
        super.initGui();
        x = (this.width / 2) - (gwidth / 2);
        y = (this.height / 2) - (gheight / 2);
        this.buttonList.clear();

        int pX = x - 1;
        int pY = y + 12;

        int iWidth = 20;
        int iHeight = 20;
        for (int i = 0; i < EntryRegistry.categories.size(); i++) {
            Category category = EntryRegistry.categories.get(i);
            this.categories[i] = new ElementCategory(category, pX, pY + (i * iHeight) - 2, iWidth, iHeight, this.player);
        }
    }

    @Override
    public void drawScreen(int mX, int mY, float f1) {
        super.drawScreen(mX, mY, f1);
        int fHeight = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;

        GL11.glColor4f(1F, 1F, 1F, 1F);
        this.mc.renderEngine.bindTexture(gui);
        drawTexturedModalRect(x, y, 0, 0, gwidth, gheight);

        /** Title */
        String str = "Categories";
        this.drawCenteredString(fontRendererObj, str, this.x + gwidth / 2, y - 10, 0x336666);

        /**Credit ;D**/
        String cre = "Powered by wasliebob";
        this.drawCenteredString(fontRendererObj, cre, this.x + gwidth / 2, y - 20, 0x336666);

        for (int i = 0; i < EntryRegistry.categories.size(); i++) {
            ElementCategory category = this.categories[i];
            if (category.isMouseInElement(mX, mY)) {
                category.onMouseEnter(mX, mY);
            }
            category.drawElement();
        }
    }

    @Override
    public void mouseClicked(int mX, int mY, int type) {
        super.mouseClicked(mX, mY, type);

        for (int i = 0; i < EntryRegistry.categories.size(); i++) {
            ElementCategory category = this.categories[i];
            if (category.isMouseInElement(mX, mY)) {
                category.onMouseClick(mX, mY, type);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}