/*
 * This file is part of ClientGuard, MIT License
 *
 * Copyright (c) 2022 D3it7i
 * Copyright (c) contributors
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.playl.clientguard.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.playl.clientguard.ClientGuardVelocity;
import net.playl.clientguard.shared.Lang;
import net.playl.clientguard.util.Crypto;
import net.playl.clientguard.shared.Sum;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientGuardChat {
    private final ClientGuardVelocity plugin;
    public static HashMap<Player, Integer> Stat = new HashMap<>();
    public static ArrayList<Player> Flag = new ArrayList<>();

    public ClientGuardChat(ClientGuardVelocity plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onServerConnected(ServerConnectedEvent e) {
        // 每当有未验证的玩家连接到任意后端服务器, 发送ClientGuard初始化
        //Camo Content
    }

    @Subscribe
    public void onPluginMessage(PluginMessageEvent e) {
        //Camo Content
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent e) {
        //已下线, 删除ClientGuard初始化状态
        Stat.remove(e.getPlayer());
    }

    private int Handle(byte[] data, Player p) {
        //Camo Content
    }
}
