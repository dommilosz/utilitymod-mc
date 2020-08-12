package com.dommilosz.utilmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.dommilosz.utilmod.rendering_util.screenXAbsolute;
import static com.dommilosz.utilmod.rendering_util.screenYAbsolute;

public class umod_gui extends Screen {
    public List<rendering_util.drawableObject> drawableObjects = new ArrayList<>();

    public umod_gui() {
        super(getTitleS());
    }

    @Override
    public ITextComponent getTitle() {
        return new StringTextComponent("UMOD GUI");
    }

    public static ITextComponent getTitleS() {
        return new StringTextComponent("UMOD GUI");
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        for (rendering_util.drawableObject DObject : drawableObjects) {
            DObject.render(mouseX, mouseY);
        }
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (rendering_util.drawableObject DObject : drawableObjects) {
            DObject.checkClick((int)mouseX, (int)mouseY);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    protected <T extends Widget> T addButton(T p_addButton_1_) {
        return super.addButton(p_addButton_1_);
    }

    @Override
    public boolean handleComponentClicked(ITextComponent p_handleComponentClicked_1_) {
        return super.handleComponentClicked(p_handleComponentClicked_1_);
    }

    @Override
    protected void init() {
        drawableObjects.clear();
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = new rendering_util.interactiveRect(screenXAbsolute(200),screenYAbsolute(200),screenXAbsolute(600),screenYAbsolute(600), new Color(56, 56, 56, 150));
        tmp.color_hover = new Color(56, 56, 56, 190);
        drawableObjects.add(tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(50),screenYAbsolute(50),screenXAbsolute(100),screenYAbsolute(50),"Klik?", new Color(13, 110, 215, 200),new Color(0,0,0,255));
        child.color_hover = new Color(0, 50, 245, 235);
        tmp.addChild(child);

        child2 = new rendering_util.interactiveText(0,0,screenXAbsolute(100),screenYAbsolute(50),"Klik?", new Color(0, 0, 0, 200));
        child.addChild(child2);
    }

    @Override
    public void tick() {
        //renderBackground();
    }

    @Override
    public void renderBackground() {

    }

    @Override
    public boolean isPauseScreen() {
        return super.isPauseScreen();
    }

    @Override
    public Minecraft getMinecraft() {
        return super.getMinecraft();
    }
}
