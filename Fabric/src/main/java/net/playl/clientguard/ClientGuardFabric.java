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

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.playl.clientguard.searcher.SystemEnv;
import net.playl.clientguard.shared.ClientGuard;
import net.playl.clientguard.shared.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class ClientGuardFabric implements ClientModInitializer {
	// 此记录器用于将文本写入控制台和日志文件。
	// 使用您的 mod id 作为记录器的名称被认为是最佳实践。
	// 这样一来，很清楚哪个 mod 写了信息、警告和错误。
	public static final String MODID = "clientguard";
	public static final String VERSION = "{version}";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	//Camo Content
	public static final MinecraftClient Mc = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		// 只要 Minecraft 处于 mod-load-ready 状态，此代码就会运行。
		// 但是，有些东西（比如资源）可能仍然未初始化。
		// 谨慎行事。
		if (!SystemEnv.getName().contains("Windows")) {
			LOGGER.error(Lang.unSupportOs);
			throw new RuntimeException();
		}

		//Camo Content

		LOGGER.info("Started! 始めました! 已开始! начал! शुरू किया गया!");

		// 注册插件通道信息接收器
		ClientPlayNetworking.registerGlobalReceiver(channel, new ChatHandle());
	}
}


