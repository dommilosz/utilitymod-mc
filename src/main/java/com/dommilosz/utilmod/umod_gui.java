package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.logging;
import com.dommilosz.utilmod.commands.umod.modules.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
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
            DObject.checkClick((int) mouseX, (int) mouseY);
        }
        for (Widget w:buttons){
            w.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        return super.mouseDragged(p_mouseDragged_1_, p_mouseDragged_3_, p_mouseDragged_5_, p_mouseDragged_6_, p_mouseDragged_8_);
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        return super.mouseScrolled(p_mouseScrolled_1_, p_mouseScrolled_3_, p_mouseScrolled_5_);
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
        renderPreset();
    }

    public void renderPreset() {
        scaling = true;
        switch (preset) {
            case 1:
                setLoggingPreset();
                break;
            case 2:
                setModulePreset();
                break;
            case 3:
                setTryjumpPreset();
                break;
            case 4:
                setAutoTryjumpPreset();
                break;
            case 5:
                setSignWriterPreset();
                break;
            case 6:
                setMacroPreset();
                break;
            case 0:
            default:
                setMainPreset();
        }
        scaling = false;
    }

    @Override
    public void tick() {
        super.tick();
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

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        for (rendering_util.drawableObject DObject : drawableObjects) {
            DObject.handleKeyPress(key, scanCode, modifiers);
        }
        for (Widget w:buttons){
            w.keyPressed(key,scanCode,modifiers);
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        for (rendering_util.drawableObject DObject : drawableObjects) {
            DObject.handleCharTyped(p_charTyped_1_,p_charTyped_2_);
        }
        for (Widget w:buttons){
            w.charTyped(p_charTyped_1_, p_charTyped_2_);
        }
        return super.charTyped(p_charTyped_1_,p_charTyped_2_);
    }

    double spacing4 = 1200d / 9d;
    double spacing3 = 175;
    public Color defaultButtonColor = new Color(13, 88, 163);
    public Color defaultButtonHoverColor = new Color(0, 128, 255);
    public Color enabledColor = new Color(19, 137, 11, 255);
    public Color enabledHoverColor = new Color(24, 255, 0, 235);
    public Color disabledColor = new Color(137, 11, 11, 255);
    public Color disabledHoverColor = new Color(255, 0, 0, 235);
    public Color backColor = new Color(201, 87, 0, 235);
    public Color backHoverColor = new Color(255, 81, 0, 235);
    public Color headerColor = new Color(0, 0, 0, 39);
    public Color textColor = new Color(255, 255, 255, 255);

    public void setMainPreset() {
        drawableObjects.clear();
        preset = 0;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = drawBackground();
        drawHeader("Main Menu",tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(50), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "Logging", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            setLoggingPreset();
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(175), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "Modules", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            setModulePreset();
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "EXIT", disabledColor, textColor);
        child.color_hover = disabledHoverColor;
        child.setCallbackOnClick(() -> showGUI(null));
        tmp.addChild(child);
    }

    public void setLoggingPreset() {
        drawableObjects.clear();
        preset = 1;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = drawBackground();
        drawHeader("Logging",tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(50), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "OFF", logging.logging_type.equals("off") ? disabledColor : defaultButtonColor, textColor);
        child.color_hover = logging.logging_type.equals("off") ? disabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            logging.setLoggingType("off");
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + spacing4), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "ALL", logging.logging_type.equals("all") ? enabledColor : defaultButtonColor, textColor);
        child.color_hover = logging.logging_type.equals("all") ? enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            logging.setLoggingType("all");
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + spacing4 * 2), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "IN", logging.logging_type.equals("in") ? enabledColor : defaultButtonColor, textColor);
        child.color_hover = logging.logging_type.equals("in") ? enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            logging.setLoggingType("in");
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + spacing4 * 3), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "OUT", logging.logging_type.equals("out") ? enabledColor : defaultButtonColor, textColor);
        child.color_hover = logging.logging_type.equals("out") ? enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            logging.setLoggingType("out");
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50), screenYAbsolute(200), screenXAbsolute(100), screenYAbsolute(50), "FILTER", logging.logging_filter ? enabledColor : disabledColor, textColor);
        child.color_hover = logging.logging_filter ? enabledHoverColor : disabledHoverColor;
        child.setCallbackOnClick(() -> {
            logging.setLoggingFilter(!logging.logging_filter);
            init();
        });
        tmp.addChild(child);

        child = drawBackButton(tmp);
        child.setCallbackOnClick(() -> {
            setMainPreset();
            init();
        });
    }

    public void setModulePreset() {
        drawableObjects.clear();
        preset = 2;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = drawBackground();
        drawHeader("Modules",tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing4), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "TRYJUMP", try_jump.issuedEnabled ? enabledColor : defaultButtonColor, textColor);
        child.color_hover = try_jump.issuedEnabled ? enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            setTryjumpPreset();
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing4), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "AUTOTRYJUMP", auto_try_jump.enabled ? enabledColor : defaultButtonColor, 0.8, textColor);
        child.color_hover = auto_try_jump.enabled ? enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            setAutoTryjumpPreset();
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing4), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "FREE_INTERACT", free_interact.enabled ? enabledColor : disabledColor, 0.8, textColor);
        child.color_hover = free_interact.enabled ? enabledHoverColor : disabledHoverColor;
        child.setCallbackOnClick(() -> {
            free_interact.commandActions.CAtoggle();
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 3 * spacing4), screenYAbsolute(125), screenXAbsolute(100), screenYAbsolute(50), "MACRO", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            setMacroPreset();
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing4), screenYAbsolute(200), screenXAbsolute(100), screenYAbsolute(50), "SIGNWRITER", signwriter.enabled ? enabledColor : defaultButtonColor, textColor);
        child.color_hover = signwriter.enabled ? enabledHoverColor : defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            setSignWriterPreset();
            init();
        });
        tmp.addChild(child);

        child = drawBackButton(tmp);
        child.setCallbackOnClick(() -> {
            setMainPreset();
            init();
        });
    }

    public void setTryjumpPreset() {
        if (!try_jump.commandActions.isCompatible()) return;
        drawableObjects.clear();
        preset = 3;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = drawBackground();
        drawHeader("TryJump",tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "START", enabledColor, textColor);
        child.color_hover = enabledHoverColor;
        child.setCallbackOnClick(() -> {
            try_jump.commandActions.CAstart();
            showGUI(null);
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "DO", try_jump.enabled ? enabledColor : disabledColor, textColor);
        child.color_hover = try_jump.enabled ? enabledHoverColor : disabledHoverColor;
        child.setCallbackOnClick(() -> {
            try_jump.commandActions.CAdo();
            showGUI(null);
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "RESET", disabledColor, textColor);
        child.color_hover = disabledHoverColor;
        child.setCallbackOnClick(() -> {
            try_jump.commandActions.CAreset();
            init();
        });
        tmp.addChild(child);

        child = drawBackButton(tmp);
        child.setCallbackOnClick(() -> {
            setModulePreset();
            init();
        });
    }

    public void setAutoTryjumpPreset() {
        if (!auto_try_jump.commandActions.isCompatible()) return;
        drawableObjects.clear();
        preset = 4;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = drawBackground();
        drawHeader("AutoTryJump",tmp);

        child = drawBackButton(tmp);
        child.setCallbackOnClick(() -> {
            setModulePreset();
            init();
        });
    }

    public void setSignWriterPreset() {
        drawableObjects.clear();
        preset = 5;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child2;

        tmp = drawBackground();
        drawHeader("SignWriter",tmp);


        interactiveTextField child11 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[0], disabledColor, textColor);
        child11.color_hover = disabledHoverColor;
        child11.setTyped_callback(()->{
            signwriter.lines[0] = child11.field.getText();
        });
        tmp.addChild(child11);

        interactiveTextField child12 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(200), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[1], disabledColor, textColor);
        child12.color_hover = disabledHoverColor;
        child12.setTyped_callback(()->{
            signwriter.lines[1] = child12.field.getText();
        });
        tmp.addChild(child12);

        interactiveTextField child13 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(275), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[2], disabledColor, textColor);
        child13.color_hover = disabledHoverColor;
        child13.setTyped_callback(()->{
            signwriter.lines[2] = child13.field.getText();
        });
        tmp.addChild(child13);

        interactiveTextField child14 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(350), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[3], disabledColor, textColor);
        child14.color_hover = disabledHoverColor;
        child14.setTyped_callback(()->{
            signwriter.lines[3] = child14.field.getText();
        });
        tmp.addChild(child14);

        drawableObject child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(425), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.enabled?"Disable":"Enable", signwriter.enabled?disabledColor:enabledColor, textColor);
        child.color_hover = signwriter.enabled?disabledHoverColor:enabledHoverColor;
        child.setCallbackOnClick(()->{
            if (signwriter.enabled) {
                signwriter.commandActions.CAoff();
            } else {
                signwriter.commandActions.CAon();
            }
            init();
        });

        tmp.addChild(child);
        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(425), screenXAbsolute(spacing4), screenYAbsolute(50), "Clear", disabledColor, textColor);
        child.color_hover = disabledHoverColor;
        child.setCallbackOnClick(()->{
            signwriter.commandActions.CAclearLines();
            init();
        });
        tmp.addChild(child);

        child = drawBackButton(tmp);
        child.setCallbackOnClick(() -> {
            setModulePreset();
            init();
        });
    }

    public void setMacroPreset() {
        if (!macro.commandActions.isCompatible()) return;
        drawableObjects.clear();
        preset = 6;
        rendering_util.drawableObject tmp;
        rendering_util.drawableObject child;
        rendering_util.drawableObject child2;

        tmp = drawBackground();
        drawHeader("Macro",tmp);

        child = drawBackButton(tmp);
        child.setCallbackOnClick(() -> {
            setModulePreset();
            init();
        });
    }

    public rendering_util.drawableObject drawBackground() {
        rendering_util.drawableObject tmp;
        tmp = new rendering_util.interactiveRect(screenXAbsolute(200), screenYAbsolute(200), screenXAbsolute(600), screenYAbsolute(600), new Color(56, 56, 56, 150));
        tmp.color_hover = new Color(56, 56, 56, 190);
        drawableObjects.add(tmp);
        return tmp;
    }
    public rendering_util.drawableObject drawHeader(String txt,drawableObject tmp) {
        rendering_util.drawableObject child;
        child = new rendering_util.interactiveButton(screenXAbsolute(0), screenYAbsolute(0), screenXAbsolute(600), screenYAbsolute(100), txt, headerColor, 2, textColor);
        tmp.addChild(child);
        return child;
    }
    public rendering_util.drawableObject drawBackButton(drawableObject tmp) {
        rendering_util.drawableObject child;
        child = new rendering_util.interactiveButton(screenXAbsolute(50), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "BACK", backColor, textColor);
        child.color_hover = backHoverColor;
        tmp.addChild(child);
        return child;
    }

    public void showGUI(@Nullable Screen gui){
        Minecraft.getInstance().displayGuiScreen(gui);
    }
}
