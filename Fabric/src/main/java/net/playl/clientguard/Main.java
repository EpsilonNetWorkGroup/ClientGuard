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

import net.playl.clientguard.searcher.SystemEnv;
import net.playl.clientguard.shared.ClientGuard;
import net.playl.clientguard.shared.Lang;

import javax.swing.*;
import java.io.Console;

public class Main {
    public static void main(String[] args) {
        if (!SystemEnv.getName().contains("Windows")) {
            System.out.println(Lang.unSupportOs);
            throw new RuntimeException();
        }
        Console cons = System.console();
        if (cons != null) {
            JOptionPane.showMessageDialog(null, Lang.intro, Lang.windowTitle, JOptionPane.INFORMATION_MESSAGE);
            Object[] options ={ Lang.install, Lang.uninstall };
            int acceptStat = JOptionPane.showOptionDialog(null, Lang.choosePrompt, Lang.windowTitle,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options);

            if (acceptStat == -1) {
                System.exit(0);
            }

            ClientGuard.load();
            if (!ClientGuard.isAdmin()) {
                JOptionPane.showMessageDialog(null, Lang.needPerm, Lang.windowTitle, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            if (acceptStat == 0) {
                install();
            } else if (acceptStat == 1) {
                unInstall();
            }
        } else {
            System.out.println(Lang.unSupportOs);
            throw new RuntimeException();
        }
    }

    private static void install() {
        int acceptStat = JOptionPane.showConfirmDialog(null, Lang.privacy, Lang.installPrompt, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (acceptStat != 0) {
            System.exit(0);
        }

        String DriverDir = ClientGuard.copyDriver();
        if (ClientGuard.installService(DriverDir)) {
            JOptionPane.showMessageDialog(null, Lang.InstallSuccess, Lang.windowTitle, JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, Lang.setupError, Lang.windowTitle, JOptionPane.ERROR_MESSAGE);
        }
    }
    private static void unInstall() {
        if (ClientGuard.unInstallService()) {
            JOptionPane.showMessageDialog(null, Lang.uninstallSuccess, Lang.windowTitle, JOptionPane.PLAIN_MESSAGE);
            ClientGuard.deleteDriver();
        } else {
            JOptionPane.showMessageDialog(null, Lang.setupError, Lang.windowTitle, JOptionPane.ERROR_MESSAGE);
        }
    }
}
