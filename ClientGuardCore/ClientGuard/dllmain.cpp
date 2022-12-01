// dllmain.cpp : 定义 DLL 应用程序的入口点。
#include "Logger.hpp"
#include "../Sharded/Version.h"

BOOL APIENTRY DllMain(HMODULE hModule, DWORD  ul_reason_for_call, LPVOID lpReserved) {
    switch (ul_reason_for_call) {
    case DLL_PROCESS_ATTACH:
        //Camo Content

        Log(LOG_INFO, "[ClientGuard] [进程] 启动完毕... 版本: %s\n", VERSION);
        break;
    case DLL_THREAD_ATTACH:
    case DLL_THREAD_DETACH:
    case DLL_PROCESS_DETACH:
        break;
    }
    return TRUE;
}
