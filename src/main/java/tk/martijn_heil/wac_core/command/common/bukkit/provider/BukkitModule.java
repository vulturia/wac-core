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

package tk.martijn_heil.wac_core.command.common.bukkit.provider;


import com.sk89q.intake.parametric.AbstractModule;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import tk.martijn_heil.wac_core.command.common.Target;

public class BukkitModule extends AbstractModule
{
    private final Server server;


    public BukkitModule(Server server)
    {
        this.server = server;
    }

    @Override
    protected void configure()
    {
        bind(Server.class).toInstance(server);
        bind(Player.class).toProvider(new PlayerProvider(server, false));
        bind(OfflinePlayer.class).toProvider(new OfflinePlayerProvider(server));

        bind(Player.class).annotatedWith(Target.class).toProvider(new PlayerProvider(server, true));
    }
}
