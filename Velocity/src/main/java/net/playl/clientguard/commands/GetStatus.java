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

package net.playl.clientguard.commands;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.playl.clientguard.ClientGuardVelocity;
import net.playl.clientguard.listener.ClientGuardChat;

public class GetStatus implements RawCommand {
    private final ClientGuardVelocity plugin;

    public GetStatus(ClientGuardVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(final RawCommand.Invocation invocation) {
        if (invocation.source() instanceof Player) {
            invocation.source().sendMessage(Component.text("您不能").color(NamedTextColor.RED));
            return;
        }

        plugin.logger.info(ClientGuardChat.Stat.toString());
    }

    @Override
    public boolean hasPermission(final RawCommand.Invocation invocation) {
        return !(invocation.source() instanceof Player);
    }
}
