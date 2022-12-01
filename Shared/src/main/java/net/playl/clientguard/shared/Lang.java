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

package net.playl.clientguard.shared;

public class Lang {
    // From BattlEye
    public static final String intro = """
            ClientGuard 是用於在線多人遊戲的反作弊軟件，可主動防止和檢測作弊並禁止作弊者在受 ClientGuard 保護的服務器上玩遊戲。

            為了實現其目標，ClientGuard 安裝了一個系統服務，該服務僅在遊戲運行時處於活動狀態。 ClientGuard 系統服務還會臨時安裝和加載內核模式驅動程序(DriverGuard)。

            雖然由於 ClientGuard 的性質，它需要訪問您系統的大部分內容，但它可以確保您的隱私不受侵犯。
            ClientGuard 對您的個人數據（文件、密碼、信用卡詳細信息等）和任何其他對您個人而言是私密的信息都不感興趣。
            ClientGuard 所做的一切都只是為了實現其防止和檢測作弊的目標。""";
    public static final String privacy = """
            為了達到預防和偵測作弊軟體的目標，確保玩家享有公平的遊戲環境，ClientGuard 可能會處理下列玩家資訊：

            - IP 位址
            - 遊戲識別碼（例如：遊戲內名稱、帳戶 ID 等）
            - 硬體設備資訊和識別碼（例如：序號）
            - 執行中作業系統的資訊
            - 遊戲相關和作業系統相關檔案與記憶體的資訊
            - 執行中處理程序、驅動程式和其他可執行程式碼的資訊
            - 此處所列其他資訊中包含的檔案名稱，可能包括作業系統的使用者名稱

            ClientGuard 遵循資料最小化政策，確保只在必要時儲存資料，例如：ClientGuard 發現可能代表使用作弊軟體的資訊時。所以 ClientGuard 通常不會儲存多數使用者的資訊。

            ClientGuard 可能會儲存遊戲使用其服務期間的所有資訊。

            你可以隨時聯繫 对应的ClientGuard服务器所有者 以行使你對資料處理的權利。想要獲取更多資訊，請參閱 ClientGuard 项目：https://github.com/EpsilonNetWorkGroup/ClientGuard。""";

    public static final String windowTitle = "ClientGuard 服务设定程序";
    public static final String choosePrompt = "您想安装或是卸载ClientGuard服务? 点击窗口X来退出";
    public static final String install = "安装ClientGuard服务";
    public static final String uninstall = "卸载ClientGuard服务";
    public static final String installPrompt = "你想要接受并安装 ClientGuard 服务吗?";
    public static final String uninstallSuccess = "ClientGuard 服务卸载成功";
    public static final String InstallSuccess = "ClientGuard 服务设定成功";
    public static final String needPerm = "ClientGuard 服务安装/卸载过程中需要管理员权限, 请使用管理员权限运行Cmd/安装程序!";
    public static final String setupError = "ClientGuard 服务安装/卸载过程中出现错误";

    public static final String unSupportOs = "抱歉, ClientGuard仅支持 Windows操作系统 X64位 同时 有控制台窗口支持的java。";
    public static final String kernelStartFail = "ClientGuard 内核服务无法启动, 请重新/安装设定服务, 请使用Cmd运行ClientGuard.jar文件(java -jar {modName}.jar)来进行服务安装!";
    public static final String whatProblem = "What you problem? 你有什么问题?";

    public static final String VerifyTimeOut = "未能及时完成反作弊验证, 请联系服务器所有者获取并安装反作弊软件!";
}
