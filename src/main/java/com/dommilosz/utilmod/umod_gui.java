package com.dommilosz.utilmod;

import com.dommilosz.utilmod.commands.umod.logging;
import com.dommilosz.utilmod.commands.umod.modules.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dommilosz.utilmod.rendering_util.*;

public class umod_gui extends Screen {
    public static umod_gui umod_gui_obj;
    public List<rendering_util.drawableObject> drawableObjects = new ArrayList<>();
    public int preset = 0;

    public umod_gui() {
        super(getTitleS());
        umod_gui_obj = this;
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
        try {
            for (rendering_util.drawableObject DObject : drawableObjects) {
                DObject.checkClick((int) mouseX, (int) mouseY);
            }
            for (Widget w : buttons) {
                w.mouseClicked(mouseX, mouseY, button);
            }
        } catch (Exception ex) {
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
            case 7:
                FSPPrompt.render();
                break;
            case -1:
                drawableObjects.clear();
                break;
            case 0:
            default:
                setMainPreset();
        }
        if (infoPrompt != null) renderInfoPrompt(infoPrompt);
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
        try {
            for (rendering_util.drawableObject DObject : drawableObjects) {
                DObject.handleKeyPress(key, scanCode, modifiers);
            }
            for (Widget w : buttons) {
                w.keyPressed(key, scanCode, modifiers);
            }
        } catch (Exception ex) {
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
        try {
            for (rendering_util.drawableObject DObject : drawableObjects) {
                DObject.handleCharTyped(p_charTyped_1_, p_charTyped_2_);
            }
            for (Widget w : buttons) {
                w.charTyped(p_charTyped_1_, p_charTyped_2_);
            }
        } catch (Exception ex) {
        }
        return super.charTyped(p_charTyped_1_, p_charTyped_2_);
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
        drawHeader("Main Menu", tmp);

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
        drawHeader("Logging", tmp);

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
        drawHeader("Modules", tmp);

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

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing4), screenYAbsolute(200), screenXAbsolute(100), screenYAbsolute(50), "FULLBRIGHT", fullbright.enabled ? enabledColor : disabledColor, 0.8, textColor);
        child.color_hover = fullbright.enabled ? enabledHoverColor : disabledHoverColor;
        child.setCallbackOnClick(() -> {
            fullbright.commandActions.CAtoggle();
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
        drawHeader("TryJump", tmp);

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
        drawHeader("AutoTryJump", tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "START", enabledColor, textColor);
        child.color_hover = enabledHoverColor;
        child.setCallbackOnClick(() -> {
            auto_try_jump.commandActions.CAstart();
            showGUI(null);
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "BACK", try_jump.enabled ? enabledColor : disabledColor, textColor);
        child.color_hover = try_jump.enabled ? enabledHoverColor : disabledHoverColor;
        child.setCallbackOnClick(() -> {
            auto_try_jump.commandActions.CAback();
            showGUI(null);
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "RESET", disabledColor, textColor);
        child.color_hover = disabledHoverColor;
        child.setCallbackOnClick(() -> {
            auto_try_jump.commandActions.CAreset();
            init();
        });
        tmp.addChild(child);

        interactiveIntTextField child15 = new rendering_util.interactiveIntTextField(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(350), screenXAbsolute(spacing4), screenYAbsolute(50), String.valueOf(auto_try_jump.delay), disabledColor, textColor);
        child15.color_hover = disabledHoverColor;
        child15.max = auto_try_jump.maxVal;
        child15.min = auto_try_jump.minVal;
        child15.setTyped_callback(() -> {
            String delays = child15.field.getText();
            auto_try_jump.commandActions.CAsetDelay(delays);
        });
        tmp.addChild(child15);

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
        rendering_util.drawableObject child;

        tmp = drawBackground();
        drawHeader("SignWriter", tmp);


        interactiveTextField child11 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[0], disabledColor, textColor);
        child11.color_hover = disabledHoverColor;
        child11.setTyped_callback(() -> {
            signwriter.lines[0] = child11.field.getText();
        });
        tmp.addChild(child11);

        interactiveTextField child12 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(200), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[1], disabledColor, textColor);
        child12.color_hover = disabledHoverColor;
        child12.setTyped_callback(() -> {
            signwriter.lines[1] = child12.field.getText();
        });
        tmp.addChild(child12);

        interactiveTextField child13 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(275), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[2], disabledColor, textColor);
        child13.color_hover = disabledHoverColor;
        child13.setTyped_callback(() -> {
            signwriter.lines[2] = child13.field.getText();
        });
        tmp.addChild(child13);

        interactiveTextField child14 = new rendering_util.interactiveTextField(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(350), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.lines[3], disabledColor, textColor);
        child14.color_hover = disabledHoverColor;
        child14.setTyped_callback(() -> {
            signwriter.lines[3] = child14.field.getText();
        });
        tmp.addChild(child14);

        interactiveIntTextField child15 = new rendering_util.interactiveIntTextField(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(350), screenXAbsolute(spacing4), screenYAbsolute(50), String.valueOf(signwriter.delay), disabledColor, textColor);
        child15.color_hover = disabledHoverColor;
        child15.max = signwriter.maxVal;
        child15.min = signwriter.minVal;
        child15.setTyped_callback(() -> {
            String delays = child15.field.getText();
            signwriter.commandActions.CAsetDelay(delays);
        });
        tmp.addChild(child15);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing3), screenYAbsolute(350), screenXAbsolute(spacing4), screenYAbsolute(50), "            DELAY: ", new Color(0, 0, 0, 0), textColor);
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(425), screenXAbsolute(spacing4), screenYAbsolute(50), signwriter.enabled ? "Disable" : "Enable", signwriter.enabled ? disabledColor : enabledColor, textColor);
        child.color_hover = signwriter.enabled ? disabledHoverColor : enabledHoverColor;
        child.setCallbackOnClick(() -> {
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
        child.setCallbackOnClick(() -> {
            signwriter.commandActions.CAclearLines();
            init();
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "LOAD", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            FileLoadPrompt fp = new FileLoadPrompt(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/signwriter/", "json", 0, () -> {
                signwriter.commandActions.CAload(FSPPath);
                init();
            });
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "SAVE", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            new FileSavePrompt(() -> {
                signwriter.commandActions.CAsave(FSPPath);
                init();
            });
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
        drawHeader("Macro", tmp);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing3), screenYAbsolute(200), screenXAbsolute(spacing4), screenYAbsolute(50), "LOAD", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            FileLoadPrompt fp = new FileLoadPrompt(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/macros/", "mcr", 0, () -> {
                macro.commandActions.CAload(FSPPath);
                init();
            });

        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "SAVE", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            new FileSavePrompt(() -> {
                macro.commandActions.CAsave(FSPPath);
                init();
            });
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), macro.recording ? "ENDRECORD" : "RECORD", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            if (macro.recording) {
                macro.commandActions.CAendrecord();
            } else {
                macro.commandActions.CArecord();
                showGUI(null);
            }


        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(125), screenXAbsolute(spacing4), screenYAbsolute(50), "PLAY", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            macro.commandActions.CAplay();
            showGUI(null);
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing3), screenYAbsolute(200), screenXAbsolute(spacing4), screenYAbsolute(50), "LOAD&PLAY", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            FileLoadPrompt fp = new FileLoadPrompt(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/macros/", "mcr", 0, () -> {
                macro.commandActions.CAloadplay(FSPPath);
                showGUI(null);
            });
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(200), screenXAbsolute(spacing4), screenYAbsolute(50), "LOAD&PLAY (ROT)", defaultButtonColor, textColor);
        child.color_hover = defaultButtonHoverColor;
        child.setCallbackOnClick(() -> {
            MaskedFileLoadPrompt fp = new MaskedFileLoadPrompt(Minecraft.getInstance().gameDir.getAbsolutePath() + "/UMOD/macros/", "mcr", 0, () -> {
                macro.commandActions.CArotloadplay(FSPPath);
                showGUI(null);
            }, new String[]{"_n.mcr", "_s.mcr", "_w.mcr", "_e.mcr"});
        });
        tmp.addChild(child);

        child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing3), screenYAbsolute(275), screenXAbsolute(spacing4), screenYAbsolute(50), "INSTRUCTIONS: " + (macro.MacroManager.loadedPackets != null ? macro.MacroManager.loadedPackets.size() : "0"), new Color(0, 0, 0, 0), textColor);
        tmp.addChild(child);

        child = drawBackButton(tmp);
        child.setCallbackOnClick(() -> {
            setModulePreset();
            init();
        });
    }

    public String FSPPath = "";
    public FilePrompt FSPPrompt;
    public String infoPrompt = null;

    public void renderInfoPrompt(String msg) {
        for (drawableObject obj : drawableObjects) {
            obj.setDisabledChild(true);
        }
        infoPrompt = msg;
        drawableObject tmp;
        drawableObject tmp2;
        tmp = new interactiveRect(screenXAbsolute(300), screenYAbsolute(350), screenXAbsolute(400), screenYAbsolute(300), new Color(49, 49, 49, 255));
        tmp.color_hover = new Color(45, 45, 45, 255);
        drawableObjects.add(tmp);

        tmp2 = new interactiveButton(screenXAbsolute(100), screenYAbsolute(20), screenXAbsolute(200), screenYAbsolute(50), msg, new Color(0, 0, 0, 0), textColor);
        tmp.addChild(tmp2);

        tmp2 = new interactiveButton(screenXAbsolute(100), screenYAbsolute(200), screenXAbsolute(200), screenYAbsolute(50), "OK", defaultButtonColor, textColor);
        tmp2.color_hover = defaultButtonHoverColor;
        tmp2.setCallbackOnClick(() -> {
            infoPrompt = null;
            init();
        });
        tmp.addChild(tmp2);
    }

    public class FilePrompt {
        int lastPreset;
        String folderPath;
        String extension;
        int page;
        Runnable callback;

        public void render() {
            if (this instanceof FileLoadPrompt) {
                ((FileLoadPrompt) this).setFileSelectPreset();
            }
            if (this instanceof FileSavePrompt) {
                ((FileSavePrompt) this).setFileSavePreset();
            }
        }
    }

    public class FileLoadPrompt extends FilePrompt {
        public FileLoadPrompt(String folderPath, String extension, int page, Runnable callback) {
            this.lastPreset = preset;
            this.folderPath = folderPath;
            this.extension = extension.contains(".") ? extension : "." + extension;
            this.callback = callback;
            FSPPrompt = this;
            setFileSelectPreset();
        }

        public void setFileSelectPreset() {
            if (!macro.commandActions.isCompatible()) return;
            drawableObjects.clear();
            preset = 7;
            rendering_util.drawableObject tmp;
            rendering_util.drawableObject child;
            rendering_util.drawableObject child2;

            tmp = drawBackground();
            drawHeader("LOAD FILE [PAGE :" + page + "]", tmp);

            FSPPath = "";
            try {
                File dir = new File(folderPath);
                if (!dir.isDirectory()) throw new Exception();
                if (!dir.exists()) throw new Exception();

                List<File> files = Arrays.asList(dir.listFiles());
                List<File> vfiles = new ArrayList<>();
                List<File> pagefiles = new ArrayList<>();
                for (File f : files) {
                    if (f.getName().endsWith(extension)) {
                        vfiles.add(f);
                    }
                }
                if (vfiles.size() < page * 20) {
                    page--;
                    init();
                }
                if (page < 0) {
                    page++;
                    init();
                }
                for (int i = 0; i < vfiles.size(); i++) {
                    File f = vfiles.get(i);
                    if (i >= page * 20 && i < (page + 1) * 20) {
                        pagefiles.add(f);
                    }
                }
                for (int i = 0; i < pagefiles.size(); i++) {
                    File f = pagefiles.get(i);
                    int off = i % 4;
                    int voff = i / 4;
                    child = new rendering_util.interactiveButton(screenXAbsolute(50 + off * spacing4), screenYAbsolute(125 + voff * 75), screenXAbsolute(100), screenYAbsolute(50), f.getName(), defaultButtonColor, 0.8, textColor);
                    child.color_hover = defaultButtonHoverColor;
                    child.setCallbackOnClick(() -> {
                        FSPPath = f.getName().replace(extension, "");
                        preset = lastPreset;
                        callback.run();
                        init();
                    });
                    tmp.addChild(child);
                }
                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "BACK", backColor, 1, textColor);
                child.color_hover = backHoverColor;
                child.setCallbackOnClick(() -> {
                    preset = lastPreset;
                    init();
                });
                tmp.addChild(child);
                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "PREV", defaultButtonColor, 1, textColor);
                child.color_hover = defaultButtonHoverColor;
                child.setCallbackOnClick(() -> {
                    page = page - 1;
                    init();
                });
                tmp.addChild(child);
                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "NEXT", defaultButtonColor, 1, textColor);
                child.color_hover = defaultButtonHoverColor;
                child.setCallbackOnClick(() -> {
                    page = page + 1;
                    init();
                });
                tmp.addChild(child);
            } catch (Exception ex) {
                preset = lastPreset;
                init();
            }
            return;
        }
    }

    public class MaskedFileLoadPrompt extends FilePrompt {
        public String[] extmask;

        public MaskedFileLoadPrompt(String folderPath, String extension, int page, Runnable callback, String[] extmask) {
            this.lastPreset = preset;
            this.folderPath = folderPath;
            this.extension = extension.contains(".") ? extension : "." + extension;
            this.callback = callback;
            this.extmask = extmask;
            FSPPrompt = this;
            setFileSelectPreset();
        }

        public void setFileSelectPreset() {
            if (!macro.commandActions.isCompatible()) return;
            drawableObjects.clear();
            preset = 7;
            rendering_util.drawableObject tmp;
            rendering_util.drawableObject child;
            rendering_util.drawableObject child2;

            tmp = drawBackground();
            drawHeader("LOAD FILE [PAGE :" + page + "]", tmp);

            FSPPath = "";
            try {
                File dir = new File(folderPath);
                if (!dir.isDirectory()) throw new Exception();
                if (!dir.exists()) throw new Exception();

                List<File> files = Arrays.asList(dir.listFiles());
                List<File> vfiles = new ArrayList<>();
                List<File> pagefiles = new ArrayList<>();
                for (File f : files) {
                    if (f.getName().endsWith(extension)) {
                        vfiles.add(f);
                    }
                }
                if (vfiles.size() < page * 20) {
                    page--;
                    init();
                }
                if (page < 0) {
                    page++;
                    init();
                }
                for (int i = 0; i < vfiles.size(); i++) {
                    File f = vfiles.get(i);
                    if (i >= page * 20 && i < (page + 1) * 20) {
                        pagefiles.add(f);
                    }
                }
                int ishift = 0;
                List<String> names = new ArrayList<>();
                for (int i = 0; i < pagefiles.size(); i++) {
                    File f = pagefiles.get(i);
                    int off = (i - ishift) % 4;
                    int voff = (i - ishift) / 4;
                    String name = f.getName();
                    for (String mask : extmask) {
                        name = name.replace(mask, "");
                    }
                    String namel = name;

                    if (!names.contains(name) && !name.endsWith(".mcr")) {
                        names.add(name);
                        child = new rendering_util.interactiveButton(screenXAbsolute(50 + off * spacing4), screenYAbsolute(125 + voff * 75), screenXAbsolute(100), screenYAbsolute(50), name, defaultButtonColor, 0.8, textColor);
                        child.color_hover = defaultButtonHoverColor;
                        child.setCallbackOnClick(() -> {
                            FSPPath = namel.replace(extension, "");
                            preset = lastPreset;
                            callback.run();
                            init();
                        });
                        tmp.addChild(child);
                    } else {
                        ishift++;
                    }
                }
                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 0 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "BACK", backColor, 1, textColor);
                child.color_hover = backHoverColor;
                child.setCallbackOnClick(() -> {
                    preset = lastPreset;
                    init();
                });
                tmp.addChild(child);
                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 1 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "PREV", defaultButtonColor, 1, textColor);
                child.color_hover = defaultButtonHoverColor;
                child.setCallbackOnClick(() -> {
                    page = page - 1;
                    init();
                });
                tmp.addChild(child);
                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "NEXT", defaultButtonColor, 1, textColor);
                child.color_hover = defaultButtonHoverColor;
                child.setCallbackOnClick(() -> {
                    page = page + 1;
                    init();
                });
                tmp.addChild(child);
            } catch (Exception ex) {
                preset = lastPreset;
                init();
            }
            return;
        }
    }

    public class FileSavePrompt extends FilePrompt {
        public FileSavePrompt(Runnable callback) {
            this.callback = callback;
            lastPreset = preset;
            FSPPrompt = this;
            setFileSavePreset();
        }

        public void setFileSavePreset() {
            if (!macro.commandActions.isCompatible()) return;
            drawableObjects.clear();
            preset = 7;
            rendering_util.drawableObject tmp;
            rendering_util.drawableObject child;
            rendering_util.drawableObject child2;

            tmp = drawBackground();
            drawHeader("SAVE FILE", tmp);
            FSPPath = "";

            try {
                child = new rendering_util.interactiveButton(screenXAbsolute(0.5f * spacing3), screenYAbsolute(275), screenXAbsolute(spacing3), screenYAbsolute(50), "File Name:", new Color(0, 0, 0, 0), textColor);
                tmp.addChild(child);

                interactiveTextField child11 = new rendering_util.interactiveTextField(screenXAbsolute(1.5f * spacing3), screenYAbsolute(275), screenXAbsolute(spacing4), screenYAbsolute(50), "", disabledColor, textColor);
                child11.color_hover = disabledHoverColor;
                tmp.addChild(child11);

                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 3 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "SAVE", defaultButtonColor, 1, textColor);
                child.color_hover = defaultButtonHoverColor;
                child.setCallbackOnClick(() -> {
                    FSPPath = child11.field.getText();
                    preset = lastPreset;
                    callback.run();
                    init();
                });
                tmp.addChild(child);

                child = new rendering_util.interactiveButton(screenXAbsolute(50 + 2 * spacing4), screenYAbsolute(500), screenXAbsolute(100), screenYAbsolute(50), "BACK", backColor, 1, textColor);
                child.color_hover = backHoverColor;
                child.setCallbackOnClick(() -> {
                    preset = lastPreset;
                    init();
                });
                tmp.addChild(child);
            } catch (Exception ex) {
                preset = lastPreset;
                init();
            }
            return;
        }
    }

    public rendering_util.drawableObject drawBackground() {
        rendering_util.drawableObject tmp;
        tmp = new rendering_util.interactiveRect(screenXAbsolute(200), screenYAbsolute(200), screenXAbsolute(600), screenYAbsolute(600), new Color(56, 56, 56, 150));
        tmp.color_hover = new Color(56, 56, 56, 190);
        drawableObjects.add(tmp);
        return tmp;
    }

    public rendering_util.drawableObject drawHeader(String txt, drawableObject tmp) {
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

    public void showGUI(@Nullable Screen gui) {
        Minecraft.getInstance().displayGuiScreen(gui);
    }
}
