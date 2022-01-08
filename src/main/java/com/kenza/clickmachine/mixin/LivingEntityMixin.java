package com.kenza.clickmachine.mixin;

import com.kenza.clickmachine.ext.LivingEntityAttribute;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityAttribute {

    @Shadow
    protected int lastAttackedTicks;

    @Shadow protected abstract void sendEquipmentChanges();

    @Override
    public int getLastAttackedTicksValue() {
        return lastAttackedTicks;
    }

    @Override
    public void setLastAttackedTicksValue(int lastAttackedTicksValue) {
        lastAttackedTicks = lastAttackedTicksValue;
    }

    @Override
    public void kenza_sendEquipmentChanges() {
        sendEquipmentChanges();
    }
}