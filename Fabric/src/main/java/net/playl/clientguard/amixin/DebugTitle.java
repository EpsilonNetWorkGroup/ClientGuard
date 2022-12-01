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

package net.playl.clientguard.amixin;

import java.util.List;

import net.playl.clientguard.ClientGuardFabric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.DebugHud;

@Environment(EnvType.CLIENT)
@Mixin(DebugHud.class)

public class DebugTitle {
    @Inject(at = @At("TAIL"), method = "getLeftText")
    private void returnLeftText(CallbackInfoReturnable<List<String>> ci) {
        ci.getReturnValue().add(0, "ClientGuard Loaded! Version: "+ClientGuardFabric.VERSION);
        ci.getReturnValue().add(0, "ClientGuard Init: "+ClientGuardFabric.INIT +" DriverGuard: "+ClientGuardFabric.KERNEL);
    }
}
