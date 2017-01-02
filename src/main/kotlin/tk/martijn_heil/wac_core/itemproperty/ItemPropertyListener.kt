/*
 * MIT License
 *
 * Copyright (c) 2016 Martijn Heil
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package tk.martijn_heil.wac_core.itemproperty;

import isSoulBound;
import isUnbreakable;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import tk.martijn_heil.wac_core.WacCore;


class ItemPropertyListener : Listener {
    @EventHandler(priority = EventPriority.HIGHEST) // If player tries to put a soulbound item in an item frame..
    fun onPlayerInteractEntity(e: PlayerInteractEntityEvent) {
        if (e.rightClicked.type == EntityType.ITEM_FRAME && isSoulBound(
                e.player.inventory.itemInMainHand)) {
            e.isCancelled = true;
            e.player.sendMessage(WacCore.messages.getString("error.event.cancelled.entity.itemFrame.putItemIn"));
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST) // If player tries to drop a soulbound item..
    fun onPlayerDropItem(e: PlayerDropItemEvent) {
        // If dropped item is soulbound, cancel the event.
        if (isSoulBound(e.itemDrop.itemStack)) {
            e.isCancelled = true;
            e.player.updateInventory();
            e.player.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("error.event.cancelled.item.drop"));
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST) // If player tries to put a soulbound item in another inventory..
    fun onInventoryClick(e: InventoryClickEvent) {
        if(e.whoClicked !is Player) return;

        if (e.currentItem != null && e.action == InventoryAction.MOVE_TO_OTHER_INVENTORY &&
                isSoulBound(e.currentItem) &&
                e.inventory.type != InventoryType.PLAYER &&
                e.inventory.type != InventoryType.CRAFTING &&
                e.inventory.type != InventoryType.CREATIVE) {
            e.isCancelled = true;
            e.whoClicked.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("error.event.cancelled.inventory.putItemIn"));
        }


        // Click the item, and then click it into the slot in the chest
        val clicked = e.clickedInventory
        if (clicked == null || clicked.type != InventoryType.PLAYER &&
                clicked.type != InventoryType.CREATIVE &&
                clicked.type != InventoryType.CRAFTING) {
            // The cursor item is going into the top inventory
            val onCursor = e.cursor;

            if (onCursor != null && isSoulBound(onCursor)) {
                e.isCancelled = true;

                // If player tries to drop item by clicking outside of his inventory while dragging the item..
                // the PlayerDropItemEvent would cancel this aswell, but this keeps the item being dragged,
                // The PlayerDropItemEvent just puts the item back into the inventory, so this is a bit nicer..
                if (clicked == null) {
                    e.whoClicked.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("error.event.cancelled.item.drop"));
                } else {
                    e.whoClicked.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("error.event.cancelled.inventory.putItemIn"));
                }

            }
        }


        if (e.currentItem != null && e.inventory != null) {
            var cancel = false
            for (i in e.inventory.contents) {
                if (i != null && isSoulBound(i)) {
                    cancel = true;
                    break;
                }
            }


            // If clicked inventory is a WORKBENCH or CRAFTING inventory
            if (e.inventory.type == InventoryType.WORKBENCH || e.inventory.type == InventoryType.CRAFTING) {
                if (e.slotType == InventoryType.SlotType.RESULT && cancel) {
                    e.isCancelled = true;
                    e.whoClicked.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("event.error.cancelled.item.craft.soulbound"));
                }
            }
        }


        if (e.currentItem != null && isSoulBound(e.currentItem) &&
                (e.click == ClickType.DROP || e.click == ClickType.CONTROL_DROP)) {
            e.isCancelled = true
            (e.whoClicked as Player).updateInventory()
            e.whoClicked.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("error.event.cancelled.item.drop"));
        }
        //        else if(ItemStacks.isSoulBound(e.getCursor()) && e.getSlotType() == InventoryType.SlotType.OUTSIDE &&
        //                e.getClickedInventory() == null)
        //        {
        //            e.setCancelled(true);
        //            ((Player) e.getWhoClicked()).updateInventory();
        //            e.getWhoClicked().setItemOnCursor(e.getCursor());
        //
        //            np.sendError(TranslationUtils.getStaticMsg(ResourceBundle.getBundle("lang.errorMsgs",
        //                    np.getMinecraftLocale().toLocale()), "eventError.cancelledItemDrop"));
        //        }


        // If player tries to drop item by clicking outside of his inventory while dragging the item..
        // the PlayerDropItemEvent would cancel this aswell, but this keeps the item being dragged,
        // The PlayerDropItemEvent just puts the item back into the inventory, so this is a bit nicer..
        //        if(ItemStacks.isSoulBound(e.getCursor()) && (e.getSlotType() == InventoryType.SlotType.OUTSIDE))
        //        {
        //            e.setCancelled(true);
        //            ((Player) e.getWhoClicked()).updateInventory();
        //            e.getWhoClicked().setItemOnCursor(e.getCursor());
        //
        //            np.sendError(TranslationUtils.getStaticMsg(ResourceBundle.getBundle("lang.errorMsgs",
        //                    np.getMinecraftLocale().toLocale()), "eventError.cancelledItemDrop"));
        //        }

        //        if(ItemStacks.isSoulBound(e.getCursor()) && e.getSlotType() == InventoryType.SlotType.OUTSIDE &&
        //                e.getClickedInventory() == null)
        //        {
        //
        //        }
    }


    @EventHandler(priority = EventPriority.HIGHEST) // Click the item, and drag it inside the chest
    fun onInventoryDrag(e: InventoryDragEvent) {
        if(e.whoClicked !is Player) return;
        val dragged = e.oldCursor // This is the item that is being dragged

        //        if (ItemStacks.isSoulBound(dragged))
        //        {
        //            int inventorySize = e.getInventory().getSize(); // The size of the inventory, for reference
        //
        //            // Now we go through all of the slots and check if the slot is inside our inventory (using the inventory size as reference)
        //            for (int i : e.getRawSlots())
        //            {
        //                if (i < inventorySize)
        //                {
        //                    e.setCancelled(true);
        //
        //                    np.sendError(TranslationUtils.getStaticMsg(ResourceBundle.getBundle("lang.errorMsgs"
        //                            , np.getMinecraftLocale().toLocale()), "eventError.cancelledPutItemInInventory"));
        //                    break;
        //                }
        //            }
        //        }
        val type = e.inventory.type

        if (isSoulBound(dragged) &&
                type != InventoryType.CREATIVE &&
                type != InventoryType.PLAYER &&
                type != InventoryType.CRAFTING) {
            e.isCancelled = true
            (e.whoClicked as Player).sendMessage(WacCore.messages.getString("error.event.cancelled.inventory.putItemIn"));
        }
    }


//    @EventHandler
//    public void onInventoryClick(InventoryClickEvent e)
//    {
//
//    }


    @EventHandler // Soulbound items can not be put in any inventory.
    fun onInventoryMoveItem(e: InventoryMoveItemEvent) {
        val holder = e.initiator.holder

        if (holder is Player) {
            if (!e.destination.equals(holder.inventory)) {
                e.isCancelled = true;
                holder.updateInventory();
                holder.sendMessage(WacCore.messages.getString("error.event.cancelled.inventory.putItemIn"));
            }
        }
    }


    @EventHandler // soulbound items can not be used in crafting recipes.
    fun onPrepareItemCraft(e: CraftItemEvent) {
        if(e.whoClicked !is Player) return;

        var containsSoulboundItem = false;
        for (i in e.inventory.contents) {
            if (i != null && isSoulBound(i)) {
                containsSoulboundItem = true;
                break; // don't waste time continuing to iterate till the end.
            }
        }

        if (containsSoulboundItem) {
            e.isCancelled = true;
            (e.whoClicked as Player).updateInventory();
            e.whoClicked.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("event.error.cancelled.item.craft.soulbound"));
        }
    }


    // If the player dies, all soulbound drops should be removed from his death drops.
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDeath(e: PlayerDeathEvent) {
        // Prevent soulbound items from being dropped on death..
        val list = e.drops
        val i = list.iterator()

        while (i.hasNext()) {
            if (isSoulBound(i.next())) i.remove();
        }
    }


    // Soulbound items should not be put on any armor stand.
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerArmorStandManipulate(e: PlayerArmorStandManipulateEvent) {
        if (e.playerItem != null && isSoulBound(e.playerItem)) {
            e.isCancelled = true
            e.player.sendMessage(ChatColor.RED.toString() + WacCore.messages.getString("error.event.cancelled.entity.armorStand.putItemOn"));
        }
    }

    @EventHandler
    fun onPlayerItemDamage(e: PlayerItemDamageEvent) {
        if (isUnbreakable(e.item)) {
            e.isCancelled = true;
            e.player.updateInventory();
        }
    }
}
