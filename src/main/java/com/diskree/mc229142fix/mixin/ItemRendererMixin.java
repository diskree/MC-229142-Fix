package com.diskree.mc229142fix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @WrapOperation(
        method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/model/BakedModelManager;getModel(Lnet/minecraft/client/util/ModelIdentifier;)Lnet/minecraft/client/render/model/BakedModel;"
        )
    )
    private BakedModel applyModelOverridesForSpyglassAndTrident(
        BakedModelManager modelManager,
        ModelIdentifier id,
        @NotNull Operation<BakedModel> original,
        @Local(argsOnly = true) ItemStack stack,
        @Local(argsOnly = true) BakedModel model
    ) {
        BakedModel spyglassOrTridentModel = original.call(modelManager, id);
        BakedModel customSpyglassOrTridentModel = spyglassOrTridentModel.getOverrides().apply(
            spyglassOrTridentModel,
            stack,
            null,
            null,
            0
        );
        if (customSpyglassOrTridentModel == null) {
            return models.getModelManager().getMissingModel();
        }
        return customSpyglassOrTridentModel;
    }
}
