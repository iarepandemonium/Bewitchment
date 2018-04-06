package com.bewitchment.common.cauldron;

import com.bewitchment.api.cauldron.IBrewEffect;
import com.bewitchment.api.cauldron.IBrewModifier;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public abstract class SimpleModifier implements IBrewModifier {
	
	private final ResourceLocation name;
	private final Ingredient ingredient;
	
	public SimpleModifier(String name, Ingredient ingredient) {
		this.name = new ResourceLocation(LibMod.MOD_ID, name);
		this.ingredient = ingredient;
	}
	
	@Override
	public IBrewModifier setRegistryName(ResourceLocation name) {
		throw new UnsupportedOperationException("Don't mess with bewitchment default implementation of modifiers!");
	}
	
	@Override
	public ResourceLocation getRegistryName() {
		return name;
	}
	
	@Override
	public Class<IBrewModifier> getRegistryType() {
		return IBrewModifier.class;
	}
	
	@Override
	public ModifierResult acceptIngredient(IBrewEffect brew, ItemStack stack, IBrewModifierList currentModifiers) {
		int currentLevel = currentModifiers.getLevel(this).orElse(0);
		if (ingredient.apply(stack)) {
			if (currentLevel < 5) {
				return new ModifierResult(currentLevel + 1, ResultType.SUCCESS);
			}
			return new ModifierResult(ResultType.FAIL);
		}
		return new ModifierResult(ResultType.PASS);
	}
	
	@Override
	public Ingredient getJEIStackRepresentative() {
		return ingredient;
	}
}
