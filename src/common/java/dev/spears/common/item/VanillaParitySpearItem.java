package dev.spears.common.item;

import dev.spears.common.SpearMaterial;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * Spear implementation intentionally aligned with vanilla trident-style timing/throwing flow
 * to approximate 1.21.11 spear behavior in 1.21.8 backports.
 */
public class VanillaParitySpearItem extends Item {
    public static final int THROW_MIN_CHARGE_TICKS = 10;
    public static final int MAX_USE_TICKS = 72000;

    private final SpearMaterial material;

    public VanillaParitySpearItem(SpearMaterial material, Properties properties) {
        super(properties
            .durability(material.durability())
            .attributes(ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, material.attackDamage(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, material.attackSpeed(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build())
            .enchantable(material.enchantability()));
        this.material = material;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return MAX_USE_TICKS;
    }

    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.SPEAR;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(stack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) {
            return;
        }

        int useTicks = this.getUseDuration(stack, entity) - timeLeft;
        if (useTicks < THROW_MIN_CHARGE_TICKS) {
            return;
        }

        if (!level.isClientSide) {
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));

            ThrownTrident thrown = new ThrownTrident(level, player, stack.copyWithCount(1));
            thrown.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, material.throwVelocity(), material.throwInaccuracy());

            if (player.getAbilities().instabuild) {
                thrown.pickup = net.minecraft.world.entity.projectile.AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            level.addFreshEntity(thrown);
            level.playSound(null, thrown, SoundEvents.TRIDENT_THROW, net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.0F);

            if (!player.getAbilities().instabuild) {
                player.getInventory().removeItem(stack);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, LivingEntity.getSlotForHand(attacker.getUsedItemHand()));
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, net.minecraft.world.level.block.state.BlockState state,
                             net.minecraft.core.BlockPos pos, LivingEntity miner) {
        if (state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(2, miner, LivingEntity.getSlotForHand(miner.getUsedItemHand()));
        }
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        int impaling = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.IMPALING, stack);
        if (impaling > 0) {
            tooltip.add(Component.translatable("item.spears.impaling_bonus", impaling)
                .withStyle(ChatFormatting.DARK_AQUA));
        }
    }

    public SpearMaterial material() {
        return material;
    }
}
