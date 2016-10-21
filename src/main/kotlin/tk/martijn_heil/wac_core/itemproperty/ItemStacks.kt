/*
 *     wac-core
 *     Copyright (C) 2016 Martijn Heil
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *     ${PROJECT_NAME}
 *     Copyright (C) 2016 Martijn Heil
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.bukkit.inventory.ItemStack;

/**
 * Check if an [ItemStack] is soulbound.

 * @param item The [ItemStack] to check.
 * *
 * @return Returns true if the [ItemStack] is soulbound.
 * *
 */
fun isSoulBound(item: ItemStack): Boolean {
    return item.itemMeta != null && item.itemMeta.lore != null &&
            item.itemMeta.lore.contains("§6§oSoulbound");
}


/**
 * Check if an [ItemStack] is unbreakable.

 * @param item The [ItemStack] to check.
 * *
 * @return true if this [ItemStack] is unbreakable.
 * *
 */
fun isUnbreakable(item: ItemStack): Boolean {
    return item.itemMeta.lore != null && item.itemMeta.lore != null &&
            item.itemMeta.lore.contains("§6§oUnbreakable");
}


/**
 * Check if an [ItemStack] is use allowed.

 * @param item The [ItemStack] to check.
 * *
 * @return true if the [ItemStack] is use allowed.
 * *
 */
fun isUseAllowed(item: ItemStack): Boolean {
    return item.itemMeta.lore != null && item.itemMeta.lore != null &&
            item.itemMeta.lore.contains("§6§oUse-Allowed");
}


/**
 * Check if an [ItemStack] is combat allowed.

 * @param item The [ItemStack] to check.
 * *
 * @return true if the [ItemStack] is combat allowed.
 * *
 */
fun isCombatAllowed(item: ItemStack): Boolean {
    return item.itemMeta.lore != null && item.itemMeta.lore != null &&
            item.itemMeta.lore.contains("§6§oCombat-Allowed");
}


/**
 * Check if an [ItemStack] is equip allowed.

 * @param item The [ItemStack] to check.
 * *
 * @return true if the [ItemStack] is equip allowed.
 * *
 */
fun isEquipAllowed(item: ItemStack): Boolean {
    return item.itemMeta.lore != null && item.itemMeta.lore != null &&
            item.itemMeta.lore.contains("§6§oEquip-Allowed");
}


/**
 * Check if an [ItemStack] is consume allowed.

 * @param item The [ItemStack] to check.
 * *
 * @return true if the [ItemStack] is consume allowed.
 * *
 */
fun isConsumeAllowed(item: ItemStack): Boolean {
    return item.itemMeta.lore != null && item.itemMeta.lore != null &&
            item.itemMeta.lore.contains("§6§oConsume-Allowed");
}


/**
 * Check if an item is part of a given kit. The item is known to be part of a certain kit if the lore contains
 * &#39;§b§oKitNameHere&#39;

 * @param item    The [ItemStack] to check.
 * *
 * @param kitName The name of the kit to check if this item is part of it.
 * *
 * @return true if the item is part of this kit.
 * *
 * @throws NullPointerException if kitName is null.
 */
fun isPartOfKit(item: ItemStack?, kitName: String): Boolean {
    return item != null && item.itemMeta.lore != null && item.itemMeta.lore != null &&
            item.itemMeta.lore.contains("§b§o" + kitName);
    // NOTE: §b instead of §6
}
