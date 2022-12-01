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

package net.playl.clientguard;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.playl.clientguard.commands.GetMods;
import net.playl.clientguard.commands.GetStatus;
import net.playl.clientguard.listener.ClientGuardChat;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(id = "clientguard", name = "ClientGuard", version = "{version}", url = "{github_url}", description = "{description}", authors = {"D3it7i"})
public class ClientGuardVelocity{
    public final ProxyServer server;
    public final Logger logger;
    public Configuration configuration;
    private final Path dataDirectory;
    public final static String VERSION = "{version}";
    public final static ChannelIdentifier channel = MinecraftChannelIdentifier.create("clientguard", "chat");
    @Inject
    public ClientGuardVelocity(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 注册插件通道信息接收器
        server.getChannelRegistrar().register(channel);
        // 注册事件
        server.getEventManager().register(this, new ClientGuardChat(this));
        // 注册超级命令
        server.getCommandManager().register("getStatus", new GetStatus(this));
        server.getCommandManager().register("getMods", new GetMods(this));

    }

    public void initConfig() throws IOException {
        if (!dataDirectory.toFile().exists()) {
            if (!dataDirectory.toFile().mkdir()) {
                logger.error("Create PluginFolder Fail!");
            }
        }

        File configFile = dataDirectory.resolve("config.yml").toFile();

        if (!configFile.exists()) {
            InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml");
            if (in != null) {
                Files.copy(in, configFile.toPath());
                logger.error("Copy New Config File, Please Config ClientGuard!");
                server.shutdown();
                return;
            }
            logger.error("Copy New Config File Fail!");
        }

        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);

    }
}
