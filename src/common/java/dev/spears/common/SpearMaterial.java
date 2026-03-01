package dev.spears.common;

public record SpearMaterial(
    int durability,
    double attackDamage,
    double attackSpeed,
    int enchantability,
    float throwVelocity,
    float throwInaccuracy
) {
    public static final SpearMaterial WOOD = new SpearMaterial(59, 5.0D, -2.90D, 15, 2.4F, 1.0F);
    public static final SpearMaterial STONE = new SpearMaterial(131, 6.0D, -2.90D, 5, 2.5F, 1.0F);
    public static final SpearMaterial COPPER = new SpearMaterial(191, 6.0D, -2.90D, 12, 2.6F, 1.0F);
    public static final SpearMaterial GOLD = new SpearMaterial(32, 5.0D, -2.90D, 22, 2.5F, 1.0F);
    public static final SpearMaterial DIAMOND = new SpearMaterial(1561, 7.0D, -2.90D, 10, 2.7F, 1.0F);
    public static final SpearMaterial NETHERITE = new SpearMaterial(2031, 8.0D, -2.90D, 15, 2.7F, 1.0F);
}
