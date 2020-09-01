package com.dommilosz.utilmod;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class rendering_util {
    public static void drawRect(int x, int y, int width, int height, Color color) {
        drawRectRaw(x, y, width, height, color);
    }

    public static void drawRect(Rectangle rect, Color color) {
        drawRectRaw(rect.x, rect.y, rect.width, rect.height, color);
    }

    public static void drawRect(Rectangle rect, Color color, float z) {
        drawRectRaw(rect.x, rect.y, rect.width, rect.height, color, z);
    }

    public static void drawRectRaw(int x, int y, int width, int height, Color color, float z) {
        Matrix4f matrix = TransformationMatrix.identity().getMatrix();

        float red = color.getRed() / 255f;
        float alpha = color.getAlpha() / 255f;
        float green = color.getGreen() / 255f;
        float blue = color.getBlue() / 255f;
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(matrix, (float) x, (float) y, z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(matrix, (float) x, (float) y + height, z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(matrix, (float) x + width, (float) y + height, z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(matrix, (float) x + width, (float) y, z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.finishDrawing();
        WorldVertexBufferUploader.draw(bufferbuilder);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    public static void drawRectRaw(int x, int y, int width, int height, Color color) {
        drawRectRaw(x, y, width, height, color, 0.00F);
    }

    public static void drawText(String text, int x, int y, Color Color, double scale) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        GL11.glPushMatrix();
        GL11.glScalef((float) scale, (float) scale, (float) scale);
        fontRenderer.drawStringWithShadow(text, x, y, ColortoHex(Color));
        //drawRect(x/scale,y/scale,50,50,new Color(255, 0, 0,255));
        GL11.glPopMatrix();
    }

    public static void drawTextCenterMiddle(String text, Rectangle rect, Color Color, double scale) {
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;
        double fontWidth = fontRenderer.getStringWidth(text) * scale;
        double fontHeight = fontRenderer.FONT_HEIGHT * scale;
        int rectx = rect.x;
        int recty = rect.y;
        int rectw = rect.width;
        int recth = rect.height;

        int x = (int) Math.floor((rectx + (rectw - fontWidth) / 2) / scale);
        int y = (int) Math.floor((recty + ((recth - fontHeight) / 2)) / scale);
        //drawRect(rect,new Color(255,255,255,100));
        drawText(text, x, y, Color, scale);
    }

    public static int ColortoHex(Color color) {
        String alpha = pad(Integer.toHexString(color.getAlpha()));
        String red = pad(Integer.toHexString(color.getRed()));
        String green = pad(Integer.toHexString(color.getGreen()));
        String blue = pad(Integer.toHexString(color.getBlue()));
        String hex = alpha + red + green + blue;
        return (int) Long.parseLong(hex, 16);
    }

    private static String pad(String s) {
        return (s.length() == 1) ? "0" + s : s;
    }

    public static class drawableObject {
        public boolean visible = true;
        public List<drawableObject> children = new ArrayList<drawableObject>();
        public Color color;
        public int x;
        public int y;
        public int width;
        public int height;
        Rectangle rect;
        Runnable clicked_callback;
        Runnable hovered_callback;
        public Color color_hover;
        public Minecraft mc = Minecraft.getInstance();
        public boolean disabled = false;

        public void drawThis(int mouseX, int mouseY, Color Color) {

        }

        public void drawThis(int mouseX, int mouseY, Color Color, float z) {

        }

        public void render(int mouseX, int mouseY) {
            if (isHovered(mouseX, mouseY)) {
                if (color_hover != null)
                    drawThis(mouseX, mouseY, color_hover);
            } else {
                drawThis(mouseX, mouseY, color);
            }
            for (drawableObject child : children) {
                child.render(mouseX, mouseY);
            }
        }

        public boolean checkClick(int mouseX, int mouseY) {
            try {
                for (drawableObject child : children) {
                    child.checkClick(mouseX, mouseY);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (isHovered(mouseX, mouseY)) {
                runCallback(clicked_callback);
                return true;
            }
            return false;
        }

        public boolean isHovered(int mouseX, int mouseY) {
            if (disabled) return false;
            if (mouseX >= rect.getMinX() && mouseX <= rect.getMaxX()
                    && (mouseY >= rect.getMinY() && mouseY <= rect.getMaxY())) {
                runCallback(hovered_callback);
                return true;
            }
            return false;
        }

        public void setCallbackOnClick(Runnable clicked_callback) {
            this.clicked_callback = clicked_callback;
        }

        public void setCallbackOnHover(Runnable hovered_callback) {
            this.hovered_callback = hovered_callback;
        }

        public void addChild(drawableObject child) {
            child.x = x + child.x;
            child.y = y + child.y;
            child.rect = new Rectangle(child.x, child.y, child.width, child.height);
            this.children.add(child);
        }

        public void handleKeyPress(int key, int scanCode, int modifiers) {
            for (drawableObject child : children) {
                child.handleKeyPress(key, scanCode, modifiers);
            }
        }

        public void handleCharTyped(int key, int scanCode) {
            for (drawableObject child : children) {
                child.handleCharTyped(key, scanCode);
            }
        }

        public void setDisabled(boolean state) {
            this.disabled = state;
        }

        public void setDisabledChild(boolean state) {
            this.disabled = state;
            for (drawableObject o : children) {
                o.setDisabledChild(state);
            }
        }
    }

    public static class interactiveRect extends drawableObject {
        public interactiveRect(int x, int y, int width, int height, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            color_hover = color;
            rect = new Rectangle(x, y, width, height);
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color) {
            drawThis(mouseX, mouseY, Color, -255);
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color, float z) {
            if (!visible) return;
            drawRect(this.rect, Color, z);
            for (drawableObject child : children) {
                child.drawThis(mouseX, mouseY, child.color, z + 1);
            }
        }
    }

    public static class interactiveText extends drawableObject {
        public String text;
        public double scale;

        public interactiveText(int x, int y, int width, int height, String text, Color color, int scale) {
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.text = text;
            this.rect = new Rectangle(x, y, width, height);
            this.color = color;
            color_hover = color;

        }

        public interactiveText(int x, int y, int width, int height, String text, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.text = text;
            this.rect = new Rectangle(x, y, width, height);
            this.color = color;
            color_hover = color;
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color) {
            drawThis(mouseX, mouseY, Color, -255);
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color, float z) {
            if (!visible) return;
            drawTextCenterMiddle(text, rect, color, scale);
            for (drawableObject child : children) {
                child.drawThis(mouseX, mouseY, child.color, z + 1);
            }
        }
    }

    public static class interactiveButton extends drawableObject {
        public String text;
        public Color txtColor;
        public double scale = 1;

        public interactiveButton(int x, int y, int width, int height, String txt, Color color, double scale, Color txtColor) {
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            color_hover = color;
            rect = new Rectangle(x, y, width, height);
            this.scale = scale;
            this.text = txt;
            this.txtColor = txtColor;
        }

        public interactiveButton(int x, int y, int width, int height, String txt, Color color, Color txtColor) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            color_hover = color;
            rect = new Rectangle(x, y, width, height);
            this.text = txt;
            this.txtColor = txtColor;
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color) {
            drawThis(mouseX, mouseY, Color, -255);
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color, float z) {
            if (!visible) return;
            drawRect(this.rect, Color, z);
            drawTextCenterMiddle(text, rect, txtColor, scale);

            for (drawableObject child : children) {
                child.drawThis(mouseX, mouseY, child.color, z + 1);
            }
        }
    }

    public static class interactiveTextField extends drawableObject {
        public String text() {
            return field.getText();
        }

        public Color txtColor;
        public double scale = 1;
        public boolean active = false;
        Runnable typed_callback;
        public TextFieldWidget field;
        public String allowedCharacters;
        public String oldTxt = "";

        public interactiveTextField(int x, int y, int width, int height, String txt, Color color, double scale, Color txtColor) {
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            color_hover = color;
            rect = new Rectangle(x, y, width, height);
            this.scale = scale;
            this.txtColor = txtColor;
            field = (new TextFieldWidget(Minecraft.getInstance().fontRenderer, x, y, width, height, txt));
            field.setText(txt);

        }

        public interactiveTextField(int x, int y, int width, int height, String txt, Color color, Color txtColor) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            color_hover = color;
            rect = new Rectangle(x, y, width, height);
            this.txtColor = txtColor;
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
            field = (new TextFieldWidget(Minecraft.getInstance().fontRenderer, x, y, width, height, txt));
            field.setText(txt);
        }

        public void setTyped_callback(Runnable callback) {
            typed_callback = callback;
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color) {
            drawThis(mouseX, mouseY, Color, -255);
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color, float z) {
            if (!visible) return;
            if (field != null) {
                field.x = x;
                field.y = y;
                field.setWidth(width);
                field.setHeight(height);
                field.render(mouseX, mouseY, z);
            }

            for (drawableObject child : children) {
                child.drawThis(mouseX, mouseY, child.color, z + 1);
            }
        }

        @Override
        public boolean checkClick(int mouseX, int mouseY) {
            if (field != null && !disabled)
                field.mouseClicked(mouseX, mouseY, 0);
            for (drawableObject child : children) {
                child.checkClick(mouseX, mouseY);
            }
            active = false;
            if (isHovered(mouseX, mouseY)) {
                active = true;
                runCallback(clicked_callback);
                return true;
            }
            return false;
        }

        @Override
        public void handleKeyPress(int key, int scanCode, int modifiers) {
            oldTxt = text();
            field.keyPressed(key, scanCode, modifiers);
            super.handleKeyPress(key, scanCode, modifiers);
            if (!oldTxt.equals(text())) {
                runCallback(typed_callback);
            }
        }

        @Override
        public void handleCharTyped(int key, int scanCode) {
            oldTxt = text();
            if (allowedCharacters == null || allowedCharacters.contains("" + (char) key)) {
                if (field != null)
                    field.charTyped((char) key, scanCode);
            }
            super.handleCharTyped(key, scanCode);
            if (!oldTxt.equals(text())) {
                runCallback(typed_callback);
            }
        }
    }

    public static class interactiveIntTextField extends drawableObject {
        public String text() {
            return field.getText();
        }

        public Color txtColor;
        public double scale = 1;
        public boolean active = false;
        Runnable typed_callback;
        public TextFieldWidget field;
        public String allowedCharacters;
        public String oldTxt = "";
        public int min = -1;
        public int max = Integer.MAX_VALUE;

        public int value() {
            try {
                return Integer.parseInt(field.getText());
            } catch (Exception ex) {
                return 0;
            }
        }

        public interactiveIntTextField(int x, int y, int width, int height, String txt, Color color, double scale, Color txtColor) {
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.allowedCharacters = "0123456789";
            this.color = color;
            color_hover = color;
            rect = new Rectangle(x, y, width, height);
            this.scale = scale;
            this.txtColor = txtColor;
            field = (new TextFieldWidget(Minecraft.getInstance().fontRenderer, x, y, width, height, txt));
            field.setText(txt);

        }

        public interactiveIntTextField(int x, int y, int width, int height, String txt, Color color, Color txtColor) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
            color_hover = color;
            this.allowedCharacters = "0123456789";
            rect = new Rectangle(x, y, width, height);
            this.txtColor = txtColor;
            if (!scaling) {
                this.scale = scale;
            } else {
                this.scale = scaleAbsolute(scale);
            }
            field = (new TextFieldWidget(Minecraft.getInstance().fontRenderer, x, y, width, height, txt));
            field.setText(txt);
        }

        public void setTyped_callback(Runnable callback) {
            typed_callback = callback;
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color) {
            drawThis(mouseX, mouseY, Color, -255);
        }

        @Override
        public void drawThis(int mouseX, int mouseY, Color Color, float z) {
            if (!visible) return;
            if (field != null) {
                field.x = x;
                field.y = y;
                field.setWidth(width);
                field.setHeight(height);
                field.render(mouseX, mouseY, z);
            }

            for (drawableObject child : children) {
                child.drawThis(mouseX, mouseY, child.color, z + 1);
            }
        }

        @Override
        public boolean checkClick(int mouseX, int mouseY) {
            if (field != null && !disabled)
                field.mouseClicked(mouseX, mouseY, 0);
            for (drawableObject child : children) {
                child.checkClick(mouseX, mouseY);
            }
            active = false;
            if (isHovered(mouseX, mouseY)) {
                active = true;
                runCallback(clicked_callback);
                return true;
            }
            return false;
        }

        @Override
        public void handleKeyPress(int key, int scanCode, int modifiers) {
            oldTxt = text();
            field.keyPressed(key, scanCode, modifiers);
            if (value() < min || value() > max) {
                if (value() < min) {
                    field.setText(String.valueOf(min));
                } else {
                    field.setText(String.valueOf(max));
                }
            }

            super.handleKeyPress(key, scanCode, modifiers);
            if (!oldTxt.equals(text())) {
                runCallback(typed_callback);
            }
        }

        @Override
        public void handleCharTyped(int key, int scanCode) {
            oldTxt = text();
            if (allowedCharacters == null || allowedCharacters.contains("" + (char) key)) {
                if (field != null)
                    field.charTyped((char) key, scanCode);
                if (value() < min || value() > max) {
                    if (value() < min) {
                        field.setText(String.valueOf(min));
                    } else {
                        field.setText(String.valueOf(max));
                    }
                }
            }
            super.handleCharTyped(key, scanCode);
            if (!oldTxt.equals(text())) {
                runCallback(typed_callback);
            }
        }
    }

    public static int screenXAbsolute(int part) {
        Screen scr = Minecraft.getInstance().currentScreen;
        return (int) Math.floor((scr.width / 1000f) * part);
    }

    public static int screenYAbsolute(int part) {
        Screen scr = Minecraft.getInstance().currentScreen;
        return (int) Math.floor((scr.height / 1000f) * part);
    }

    public static int screenXAbsolute(double part) {
        Screen scr = Minecraft.getInstance().currentScreen;
        return (int) Math.floor((scr.width / 1000f) * part);
    }

    public static int screenYAbsolute(double part) {
        Screen scr = Minecraft.getInstance().currentScreen;
        return (int) Math.floor((scr.height / 1000f) * part);
    }

    public static double scaleAbsolute(double scale) {
        Screen scr = Minecraft.getInstance().currentScreen;
        double scaled = ((double) (scr.height / 320f) * (double) scale);
        return scaled;
    }

    public static boolean scaling = false;
    public static void runCallback(Runnable callback){
        try{
            if(callback!=null){
                callback.run();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            packetIO.SendUMODMessageToClient(ex.toString());
        }
    }
}
