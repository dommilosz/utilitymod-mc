package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.logging;
import com.dommilosz.utilmod.commands.umod.modules.free_interact;
import com.dommilosz.utilmod.commands.umod.modules.signwriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.dommilosz.utilmod.rendering_util.*;

public class umod_gui extends Screen {
    public List<rendering_util.drawableObject> drawableObjects = new ArrayList<>();
    public int preset = 0;
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
        scaling = true;
        switch (preset){
            case 1:
                setLoggingPreset();break;
            case 2:setModulePreset();break;
            case 0:
            default:setMainPreset();
        }
        scaling =false;
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
    public Color defaultButtonColor = new Color(13, 88, 163);
    public Color defaultButtonHoverColor = new Color(0, 128, 255);
    public Color enabledColor = new Color(19, 137, 11, 255);
    public Color enabledHoverColor =  new Color(24, 255, 0, 235);
    public Color disabledColor = new Color(137, 11, 11, 255);
    public Color disabledHoverColor =  new Color(255, 0, 0, 235);
    public Color backColor =  new Color(201, 87, 0, 235);
    public Color backHoverColor =  new Color(255, 81, 0, 235);
    public Color headerColor =  new Color(0, 0, 0, 39);
    public Color textColor =  new Color(255, 255, 255, 255);
    public void setMainPreset(){
        drawableObjects.clear();
        preset = 0;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = new rendering_util.interactiveRect(screenXAbsolute(200),screenYAbsolute(200),screenXAbsolute(600),screenYAbsolute(600), new Color(56, 56, 56, 150));
        tmp.color_hover = new Color(56, 56, 56, 190);
        drawableObjects.add(tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(0),screenYAbsolute(0),screenXAbsolute(600),screenYAbsolute(100),"Main Menu", headerColor,2,textColor);
        child.setCallbackOnClick(this::setLoggingPreset);
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"Logging", defaultButtonColor,textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(()->{setLoggingPreset();init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(175),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"Modules", defaultButtonColor,textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(()->{setModulePreset();init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50),screenYAbsolute(500),screenXAbsolute(100),screenYAbsolute(50),"EXIT", disabledColor,textColor);
        child.color_hover = disabledHoverColor;
        child.setCallbackOnClick(() -> Minecraft.getInstance().displayGuiScreen(null));
        tmp.addChild(child);
    }
    public void setLoggingPreset(){
        drawableObjects.clear();
        preset = 1;
        double spacing = 1200d/9d;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = new rendering_util.interactiveRect(screenXAbsolute(200),screenYAbsolute(200),screenXAbsolute(600),screenYAbsolute(600), new Color(56, 56, 56, 150));
        tmp.color_hover = new Color(56, 56, 56, 190);
        drawableObjects.add(tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(0),screenYAbsolute(0),screenXAbsolute(600),screenYAbsolute(100),"LOGGING", headerColor,2,textColor);
        child.setCallbackOnClick(this::setLoggingPreset);
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"OFF",logging.logging_type.equals("off")?disabledColor : defaultButtonColor,textColor);
        child.color_hover = logging.logging_type.equals("off")?disabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(()->{logging.setLoggingType("off");init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+spacing),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"ALL", logging.logging_type.equals("all")?enabledColor : defaultButtonColor,textColor);
        child.color_hover = logging.logging_type.equals("all")?enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(()->{logging.setLoggingType("all");init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+spacing*2),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"IN", logging.logging_type.equals("in")?enabledColor : defaultButtonColor,textColor);
        child.color_hover = logging.logging_type.equals("in")?enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(()->{logging.setLoggingType("in");init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+spacing*3),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"OUT", logging.logging_type.equals("out")?enabledColor : defaultButtonColor,textColor);
        child.color_hover = logging.logging_type.equals("out")?enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(()->{logging.setLoggingType("out");init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50),screenYAbsolute(200),screenXAbsolute(100),screenYAbsolute(50),"FILTER", logging.logging_filter? enabledColor:disabledColor,textColor);
        child.color_hover = logging.logging_filter? enabledHoverColor:disabledHoverColor;
        child.setCallbackOnClick(()->{logging.setLoggingFilter(!logging.logging_filter);init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50),screenYAbsolute(500),screenXAbsolute(100),screenYAbsolute(50),"BACK", backColor,textColor);
        child.color_hover = backHoverColor;
        child.setCallbackOnClick(this::setMainPreset);
        tmp.addChild(child);
    }
    public void setModulePreset(){
        drawableObjects.clear();
        preset = 2;
        double spacing = 1200d/9d;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = new rendering_util.interactiveRect(screenXAbsolute(200),screenYAbsolute(200),screenXAbsolute(600),screenYAbsolute(600), new Color(56, 56, 56, 150));
        tmp.color_hover = new Color(56, 56, 56, 190);
        drawableObjects.add(tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(0),screenYAbsolute(0),screenXAbsolute(600),screenYAbsolute(100),"MODULES", headerColor,2,textColor);
        child.setCallbackOnClick(this::setLoggingPreset);
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+0*spacing),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"TRYJUMP",defaultButtonColor,textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(()->{/*todo show tryjump gui*/;init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+1*spacing),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"AUTOTRYJUMP",defaultButtonColor,0.8,textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(()->{/*todo show autotryjump gui*/;init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+2*spacing),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"FREE_INTERACT", free_interact.enabled?enabledColor:disabledColor,0.8,textColor);
        child.color_hover = free_interact.enabled?enabledHoverColor:disabledHoverColor;
        child.setCallbackOnClick(()->{ free_interact.commandActions.CAtoggle();init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+3*spacing),screenYAbsolute(125),screenXAbsolute(100),screenYAbsolute(50),"MACRO",defaultButtonColor,textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(()->{/*todo show macro gui*/;init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50+0*spacing),screenYAbsolute(200),screenXAbsolute(100),screenYAbsolute(50),"SIGNWRITER",defaultButtonColor,textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(()->{/*todo show signwriter gui*/;init();});
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50),screenYAbsolute(500),screenXAbsolute(100),screenYAbsolute(50),"BACK", backColor,textColor);
        child.color_hover = backHoverColor;
        child.setCallbackOnClick(this::setMainPreset);
        tmp.addChild(child);
    }
}
