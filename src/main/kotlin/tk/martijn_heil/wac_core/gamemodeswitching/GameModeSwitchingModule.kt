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

package tk.martijn_heil.wac_core.gamemodeswitching

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.*
import java.util.logging.Logger


object GameModeSwitchingModule {
    private val switchingPlayers = HashSet<UUID>()
    lateinit private var plugin: Plugin
    lateinit private var logger: Logger

    fun init(plugin: Plugin, logger: Logger) {
        this.plugin = plugin
        this.logger = logger

        logger.info("Initializing GameModeSwitchingModule..")

//        plugin.server.pluginManager.registerEvents(GameModeSwitchingListener, plugin)
//        plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, {
//            Bukkit.getOnlinePlayers().forEach {
//                if(isGameModeSwitching(it)) it.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 40, 1, false, false), true)
//            }
//        }, 0, 20)
    }


    fun isGameModeSwitching(p: Player) = switchingPlayers.contains(p.uniqueId)


//    private object GameModeSwitchingListener : Listener {
//        @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
//        fun onPlayerGameModeChange(e: PlayerGameModeChangeEvent) {
//            val p = e.player
//
//            if(!e.player.hasPermission(WacCore.Permission.BYPASS__GAMEMODE_SWITCH_PENALTY.toString()) && !isGameModeSwitching(p)) {
//                if(e.newGameMode == GameMode.CREATIVE) e.isCancelled = true
//                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, {
//                    switchingPlayers.add(p.uniqueId)
//                    Freeze.setFrozen(p, true, true)
//                    e.player.sendMessage(ChatColor.RED.toString() + "Je bent nu aan het wisselen naar " + e.newGameMode + " mode, dit duurt 30 seconden waarin je kwetsbaar bent.")
//
//                    when {
//                        (e.newGameMode == GameMode.SURVIVAL || e.newGameMode == GameMode.ADVENTURE) -> {
//                            Bukkit.getScheduler().scheduleSyncDelayedTask(WacCore.plugin, {
//                                switchingPlayers.remove(p.uniqueId)
//                                Freeze.setFrozen(p, false, true)
//                            }, 600)
//                        }
//
//                        (e.newGameMode == GameMode.CREATIVE || e.newGameMode == GameMode.SPECTATOR) -> {
//                            Bukkit.getScheduler().scheduleSyncDelayedTask(WacCore.plugin, {
//                                Freeze.setFrozen(p, false, true)
//                                e.player.gameMode = e.newGameMode
//                                switchingPlayers.remove(p.uniqueId)
//                            }, 600)
//                        }
//                    }
//                }, 0)
//            }
//        }
//    }
}