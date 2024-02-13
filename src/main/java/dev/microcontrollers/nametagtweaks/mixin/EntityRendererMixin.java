package dev.microcontrollers.nametagtweaks.mixin;

import dev.microcontrollers.nametagtweaks.config.NametagTweaksConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {
    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    public void hideNametags(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (NametagTweaksConfig.INSTANCE.getConfig().removeNametags ||
                (NametagTweaksConfig.INSTANCE.getConfig().hideEntityNametagsInHiddenHud && !MinecraftClient.isHudEnabled() && !entity.isPlayer()) ||
                (NametagTweaksConfig.INSTANCE.getConfig().hideArmorStandNametagsInHiddenHud && !MinecraftClient.isHudEnabled() && entity instanceof ArmorStandEntity))
            ci.cancel();
    }

    @Inject(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", shift = At.Shift.BEFORE, ordinal = 0))
    public void nametagScale(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        float scale = NametagTweaksConfig.INSTANCE.getConfig().nametagScale;
        matrices.scale(scale, scale, scale);
    }

    @ModifyArg(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"), index = 2)
    public float changeNametagHeight(float y) {
        return y - NametagTweaksConfig.INSTANCE.getConfig().nametagOffset;
    }

    @ModifyArg(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I"), index = 4)
    public boolean addNametagShadow(boolean shadow) {
        return NametagTweaksConfig.INSTANCE.getConfig().nametagTextShadow;
    }

    @ModifyArg(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", ordinal = 0), index = 8)
    public int changeNametagBackground(int color) {
        return NametagTweaksConfig.INSTANCE.getConfig().nametagColor.getRGB();
    }
}
